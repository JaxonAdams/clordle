(ns clordle.handler
  (:require [clordle.game :as game]
            [cheshire.core :as json]
            [compojure.core :as server]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(server/defroutes app-routes
  (server/GET "/" [] "Hello World")
  (server/GET "/api/check/:guess" [guess] (-> guess game/check-solution json/generate-string))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
