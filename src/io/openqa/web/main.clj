(ns io.openqa.web.main
  (:gen-class)
  (:import (java.io File))
  (:require [io.openqa.web.bootstrap.config :as config]
            [io.openqa.web.bootstrap.server :as server]
            [clojure.tools.cli :refer [parse-opts]]))


(def cli-options
  [["-c" "--config CONFIG-FILE" "Configration File"
    :default "config/config.yaml"]
   ["-h" "--help"]])

(defn- print-help []
  "Print commandline help"
  (println "Usage: openqa COMMAND [OPTIONS]")
  (println "Comands: start | test | help")
  (println "Example: openqa start -c /opt/opqa/config.yaml")
  (println "")
  (println "Command: start")
  (println "Option: -c config/file/path")
  (println "\tConfigration file path \n\tOptional \n\tDefault: opqroot/config/config.yaml")
  (println "")
  (println "Command: help")
  (println "\tPrint helps"))

(defn -main
  "Bootstap web server"
  [& args]
  (let [parsed-options (parse-opts args cli-options)
        {{:keys [config help]} :options arguments :arguments} parsed-options
        config-file (. (new File config) getCanonicalFile)
        _ (println "cli-options" parsed-options config help arguments)]
    (cond
      (not= (count arguments) 1) (print-help)
      (= (first arguments) "start") (do (config/load-config config-file)
                                        (println @config/config)
                                        (server/start))
      :else (print-help)))  )
