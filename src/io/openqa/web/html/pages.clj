(ns io.openqa.web.html.pages
  (:require [cheshire.core :as cheshire]
            [hiccup.core :as hiccup]
            [hiccup.page]
            [clojure-bulma.layout]
            [clojure-bulma.components :as components]))


(defn layout
  "Generate layout"
  [{:keys [login-info content]}]
  (let [{:keys [uid github-uid github-avatar github-email]} login-info]
    [:body [:div.navbar {:role "navigation"
                         :aria-label "main navigation"}
            [:div.container
             [:div.navbar-brand
              [:a.navbar-item {:href "/"}
               [:img {:src "/static/images/OpenQA-horizontal-530x130.png" :width "112" :height "28"}]]
              [:a.navbar-burger.burger {:role "button"
                                        :ariel-label "menu"
                                        :aria-expanded "false"
                                        :data-target "openqa-nav"
                                        }
               (for [i (range 1 3)]
                 [:span {:aria-hidden "true"}])]]
             [:div#openqa-nav.navbar-menu
              [:div.navbar-start
               [:a.navbar-item "主页"]
               [:a.navbar-item "文档"]]
              [:div.nav-end
               [:div.navbar-item
                [:div.buttons
                 (if github-uid
                   [:a.button.is-white
                    [:strong github-uid]]
                   [:a.button.is-primary
                    [:strong "Sign Up"]])
                 (if github-uid
                   [:a.button.is-light "Log out"]
                   [:a.button.is-light "Log in"])]]]]]]
     content]))
