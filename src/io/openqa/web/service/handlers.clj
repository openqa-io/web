(ns io.openqa.web.service.handlers
  (:import (io.vertx.core Vertx Handler)
           (io.vertx.core.net NetSocket)
           (io.vertx.core.http HttpMethod)
           (io.vertx.ext.web Router))
  (:require [io.openqa.web.bootstrap.config :as config]
            [cheshire.core :as cheshire]
            [hiccup.core :as hiccup]
            [hiccup.page :as page]
            [clojure-bulma.layout :as layout]
            [io.openqa.web.service.db :as db]))


(def home-page
  (reify Handler
    (handle [this context]
      (let [response (. context response)]
        (.. context response (end (page/html5
                                   [:head (page/include-css "/static/css/openqa.css")]
                                   (layout/hero {:children (layout/hero-body {:children [:h1.title "Hello hiccup clojure"]})}))))))))
