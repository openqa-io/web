(ns io.openqa.web.service.db
  (:require [clj-postgresql.core :as pg]
            [hikari-cp.core :refer :all]
            [clojure.java.jdbc :as jdbc]))

;; domain to connection mapping.
;; Could be updated in different threads
(def pool (atom {}))

(defn initiate-pool
  "Initiate a connection pool with postgresql"
  [{:keys [host port user password db poolSize]}]
  (println host port user password db poolSize)
  (def datasource-options {:auto-commit        true
                           :read-only          false
                           :validation-timeout 5000
                           :idle-timeout       600000
                           :max-lifetime       1800000
                           :minimum-idle       10
                           :adapter            "postgresql"
                           :maximum-pool-size (or poolSize 10)
                           :username user
                           :password password
                           :database-name db
                           :server-name  (or host "localhost")
                           :port-number  (or port  5432)
                           :register-mbeans    false})
  ;; (delay (make-datasource datasource-options)))
  (let [db (delay (make-datasource datasource-options))]
    (reset! pool db)))
