(ns io.openqa.web.service.handlers
  (:import (io.vertx.core Vertx Handler)
           (io.vertx.core.net NetSocket)
           (io.vertx.core.json JsonObject)
           (io.vertx.core.http HttpMethod)
           (io.vertx.ext.web Router)
           (io.vertx.ext.web.client WebClient))
  (:require [io.openqa.web.bootstrap.config :as config]
            [cheshire.core :as cheshire]
            [io.openqa.web.html.pages :as pages]
            [hiccup.core :as hiccup]
            [hiccup.page]
            [clojure-bulma.layout :as layout]
            [io.openqa.web.service.db :as db]))


(def home-page
  (reify Handler
    (handle [this context]
      (let [request (. context request)
            response (. context response)
            body-string (. request getBodyAsString)
            code (. request getParam "code")
            state (. request getParam "state")]
        (.. context response (end (str "code=" code " state=" state)))))))

(def github-login
  (reify Handler
    (handle [this context]
      (let [config @config/config
            github (:github config)
            {:keys [appid secret]} github
            request (. context request)
            response (. context response)
            code (. request getParam "code")
            _ (println code)
            state (. request getParam "state")
            _ (println state)
            vertx (. context owner)
            _ (println "vertx" vertx)
            client (WebClient/create vertx)]
        (.. client
            (post "https://github.com/login/oauth/access_token")
            (putHeader "Accept" "application/json")
            (sendJsonObject (.. (new JsonObject)
                                (put "client_id" (:appid github))
                                (put "client_secret" (:github-secret github))
                                (put "code" code))
                            (reify Handler
                              (handle [this ar]
                                (if (. ar succeeded)
                                  (let [response (. ar result)
                                        body-string (. response bodyAsString)
                                        body-json (cheshire/parse-string body-string)]
                                    (. response (end body-string)))
                                  (.. response (setStatusCode 500) (end "failed")))))))))))
