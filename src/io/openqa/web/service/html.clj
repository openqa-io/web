(ns io.openqa.web.service.handlers
  (:require [hiccup/core :as hiccup]))

(defn layout [{:keys {user body}}]
  (core/html [:div.container
              [:nav.navbar {:role "navigator" :aria-label "main navigation"}
               [:div.navbar-brand
                [:a.navbar-item
                 [:img {:src "/static/images/OpenQA-horizontal-530x130.png"}]]
                ]
               [:div#navbarmenu.navbar-menu
                [:div.navbar-start]]]])
  )
