(ns clordle.core
  (:gen-class)
  (:require [clordle.game :as game]))

;; ...Engage!
(defn -main
  "I don't do a whole lot ... yet."
  [& _]
  (println "Hello, World!")
  (println "Daily word ==>" (clordle.game/daily-word)))
