(ns clordle.game
  (:require [clojure.string :as clj-str]
            [clojure.java.io :as io]))

(defn get-words 
  []
  (-> "words.txt" io/resource slurp clj-str/split-lines))

(defn daily-word
  "Return today's 'word of the day'."
  []
  (-> (java.time.Instant/now)    ;; I want to map the current date to a word in our list of words
      inst-ms
      (quot 1000)
      (quot 60)
      (quot 60)
      (- 6)                      ;; offset to local time -- GMT->MST -6:00
      (quot 24)                  ;; number of days since Jan 1, 1970
      (mod (count (get-words)))  ;; use modulo to map to an element in our list of words
      (->> (nth (get-words)))))

(defn check-character
  [chosen-word character idx]
  (cond
    (= character (nth chosen-word idx)) {(str character) :in-position}
    (contains? (set chosen-word) character) {(str character) :in-word}
    :else {(str character) :incorrect}))

(defn check-solution
  [guess]
  (if-not (re-matches #"^[a-zA-Z]{5}$" guess)
    (throw (IllegalArgumentException. "Invalid guess"))
    (->> guess 
         clj-str/lower-case
         (map-indexed (fn [idx character] (check-character (daily-word) character idx))))))

;; !------------------------------------------------------------------------------------------------

(comment
  (check-solution "stair")
  (check-solution "stare")
  (check-solution "heyyo")
  (check-solution "hello"))

(comment
  (check-solution "stare"))

(comment
  (vec (get-words))
  (daily-word))