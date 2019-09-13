(ns web.core-test
  (:require [clojure.test :refer :all]
            [clojure-bulma.layout :refer :all]
            [hiccup.core :refer :all]
            [hiccup.page :as page]
            [io.openqa.web.html.pages :as pages]
            ))

(deftest a-test
  (testing "FIXME, I fail."
    (println (pages/layout))
    (is (= 0 1))))
