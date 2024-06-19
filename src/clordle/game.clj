(ns clordle.game
  (:require clojure.string))

;; random function will be replaced later
(defn daily-word
  []
  (rand-nth ["hello" "stare" "place" "cares" "happy"]))

(defn check-character
  [chosen-word character idx]
  (cond
    (= character (nth chosen-word idx)) :green
    (contains? (set chosen-word) character) :yellow
    :else :red))

(defn index-characters
  [word]
  (map-indexed (fn [i c] [i c]) word))

(defn check-solution
  [chosen-word guess]
  (->> guess
       index-characters
       (map (fn [[idx character]] (check-character chosen-word character idx)))))

(comment
  (check-solution "hello" "stair")
  (check-solution "hello" "stare")
  (check-solution "hello" "heyyo")
  (check-solution "hello" "hello"))
