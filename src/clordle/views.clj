(ns clordle.views
  (:require [hiccup.page :as h]
            [hiccup.util :as h-util]))

(defn index-page []
  (h/html5
   [:head
    [:title "Clordle!"]
    [:link {:rel "icon" :type "image/png" :href (h-util/to-uri "/favicon.png")}]
    (h/include-css "/css/styles.css")
    (h/include-js "/js/script.js")]
   [:body
    [:header.header
     [:h1.title "CLORDLE"]]
    [:main.main-container
     (map 
      (fn [n] [:div.input-row
               {:id (str "input-row-" n)}
               (map 
                (fn [m] [:input 
                         {:type "text" :maxlength "1" :id (str "input-" m "-" n)}]) 
                (range 5))]) 
      (range 6))]]))
