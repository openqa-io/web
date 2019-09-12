(defproject web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-postgresql "0.7.0"]
                 [hikari-cp "2.8.0"]
                 [io.vertx/vertx-core "3.8.1"]
                 [io.forward/yaml "1.0.9"]
                 [honeysql "0.9.4"]
                 [io.vertx/vertx-rx-java2 "3.8.1"]
                 [io.vertx/vertx-web-client "3.8.1"]
                 [io.vertx/vertx-web "3.8.1"]
                 [io.vertx/vertx-pg-client "3.8.1"]
                 [org.clojure/tools.cli "0.4.2"]
                 [hiccup "1.0.5"]
                 [cheshire "5.9.0"]
                 [environ "1.1.0"]
                 ;; cross linked - clojure-bulma
                 [clojure-bulma "0.1.0-SNAPSHOT"]]
  :main ^:skip-aot io.openqa.web.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
