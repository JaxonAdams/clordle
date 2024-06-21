(ns clordle.game
  (:require [clojure.string :as clj-str]
            [clojure.java.io :as io]))

(def words (-> "words.txt" io/resource slurp clj-str/split-lines))

(defn daily-word
  "Return today's 'word of the day'."
  []
  (-> (java.time.Instant/now)  ;; I want to map the current date to a word in clordle.game/words
      inst-ms
      (quot 1000)
      (quot 60)
      (quot 60)
      (quot 24)                ;; number of days since Jan 1, 1970
      (mod (count words))      ;; use modulo to map to an element in clordle.game/words
      (->> (nth words))))

(defn check-character
  [chosen-word character idx]
  (cond
    (= character (nth chosen-word idx)) {(str character) :in-position}
    (contains? (set chosen-word) character) {(str character) :in-word}
    :else {(str character) :incorrect}))

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

(comment
  (vec words)
  (daily-word))
