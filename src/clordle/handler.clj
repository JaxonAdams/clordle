(ns clordle.handler
  (:require [clordle.game :as game]
            [clordle.views :as views]
            [cheshire.core :as json]
            [compojure.core :as server]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [hiccup.middleware :as middleware]))

(server/defroutes app-routes
  (server/GET "/" [] (views/index-page))
  (server/GET "/api/check/:guess" [guess] (-> guess game/check-solution json/generate-string))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (middleware/wrap-base-url)))
