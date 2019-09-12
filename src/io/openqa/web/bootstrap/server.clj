(ns io.openqa.web.bootstrap.server
  (:import (io.vertx.core Vertx Handler)
           (io.vertx.core.net NetSocket)
           (io.vertx.core.http HttpMethod)
           (io.vertx.ext.web Router)
           (java.io File)
           (io.vertx.ext.web.client WebClient)
           (io.vertx.ext.web.handler BodyHandler StaticHandler))
  (:require [io.openqa.web.bootstrap.config :as config]
            [io.openqa.web.service.db :as db]
            [io.openqa.web.service.handlers :as handlers]))

(def httpRequestHandler
  (reify Handler
    (handle [this request]
      (.. request response (end "hello world")))))

(defn init-sub-router [vertx]
  ;; init sub router
  (let [router (Router/router vertx)]
    (.. router route (handler (BodyHandler/create "webroot")))
    router))

(defn start []
  "Initiate db connection and start server..."
  (let [vertx (Vertx/vertx)
        config @config/config
        web (:web config)
        db (:db config)
        static (str (or (:static web) "/static") "/*")
        listens (:listens web)
        ;; (db/init-db-service Default Shards vertx)
        server (. vertx createHttpServer )
        main-router (Router/router vertx)
        static-handler (StaticHandler/create "webroot")
        web-client (WebClient/create vertx)
        ;; sub-router (init-sub-router vertx)
        ]

    ;; init db pool
    (db/initiate-pool db)

    (println static (. (new File "") getCanonicalPath))
    ;; Test route
    ;; (.. sub-router (get static) (handler static-handler) )

    ;; static file handler
    (try
      (.. main-router (route static) (handler static-handler))
      (.. main-router (get "/test") (handler httpRequestHandler))
      (.. main-router (get "/") (handler handlers/home-page))
      (.. main-router (get "/api/github/login") (handler (handlers/github-login web-client)))
      (catch Exception e (println (. e printStackTrace))))


    (doto server
      (.requestHandler main-router))

    (doseq [{:keys [host port]} listens]
      (. server listen (or port 8080) (or host "localhost"))
      (println "REST server started @ host " (or host "localhost") " port " (or port 8080)))))
