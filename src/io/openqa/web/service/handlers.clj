(ns io.openqa.web.service.handlers
  (:import (io.vertx.core Vertx Handler)
           (io.vertx.core.net NetSocket)
           (io.vertx.core.json JsonObject)
           (io.vertx.core.http HttpMethod)
           (io.vertx.ext.web Router))
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
      (let [response (. context response)
            _ (println "root...xs")
            body-string (pages/layout)
            _ (println body-string)]
        (.. response (end (hiccup/html body-string)))))))

(defn github-login
  "Create github login with vertx"
  [web-client]
  (reify Handler
    (handle [this context]
      (let [config @config/config
            github (:github config)
            {:keys [appid secret]} github
            request (. context request)
            response (. context response)
            code (. request getParam "code")
            _ (println code github)]
        (.. web-client
            (postAbs "https://github.com/login/oauth/access_token")
            (putHeader "Accept" "application/json")
            (sendJsonObject (.. (new JsonObject)
                                (put "client_id" (:appid github))
                                (put "client_secret" (:github-secret github))
                                (put "code" code))
                            (reify Handler
                              (handle [this ar]
                                (if (. ar succeeded)
                                  (let [client-response (. ar result)
                                        body-string (. client-response bodyAsString)
                                        body-json (cheshire/parse-string body-string)
                                        access_token (:access_token body-json)]
                                    (.. web-client
                                        (getAbs "https://api.github.com/user")
                                        (putHeader "Authorization" (str "token " access_token))
                                        (putHeader "Accept" "application/json")
                                        (send (reify Handler
                                                (handle [this ar]
                                                  (if (. ar succeeded)
                                                    (let [client-response (. ar result)
                                                          body-string (. client-response bodyAsString)
                                                          ;; body-json (cheshire/parse-string body-string)
                                                          ]
                                                      (println body-string)
                                                      (.. response (end body-string)))
                                                    (do
                                                      (println (class (. ar result)) (. ar result))
                                                      (println (.. ar cause printStackTrace))
                                                      (.. response (setStatusCode 500) (end "failed")))))))))
                                  (do
                                    (println (class (. ar result)) (. ar result))
                                    (println (.. ar cause printStackTrace))
                                    (.. response (setStatusCode 500) (end "failed"))))))))))))
