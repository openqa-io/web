(ns io.openqa.web.bootstrap.config
  (:refer-clojure :exclude [load])
  (:require [environ.core :refer [env]])
  (:require [yaml.core :as yaml]))

;; global config
(def config (ref {}))

;; load config

(defn load-config
  ([] (load-config "./config/config.yaml"))
  ([file-name]
   (let [loaded-config (yaml/from-file file-name)
         github (:github loaded-config)
         github-with-secret (assoc github :github-secret (:github-secret env))
         merged-config (assoc loaded-config :github github-with-secret)]
     (dosync (ref-set config merged-config)))))
