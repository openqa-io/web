(ns web.core-test
  (:require [clojure.test :refer :all]
            [clojure-bulma.layout :refer :all]
            [hiccup.core :refer :all]
            [hiccup.page :as page]
            ))

(deftest a-test
  (testing "FIXME, I fail."
    (println (page/html5 [:head (page/include-css "/static/css/openqa.css")] (hero {:children [:h1 "hello world"]})))
    (is (= 0 1))))
