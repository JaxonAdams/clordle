(ns clordle.game
  (:require clojure.string))

;; random function will be replaced later
(defn daily-word
  []
  (rand-nth ["hello" "stare" "place" "cares" "happy"]))

(defn num-in-position
  [chosen-word guess]
  nil)

(defn num-in-word
  [chosen-word guess]
  nil)

(defn submit-guess
  [chosen-word guess]
  nil)

(comment
  (let [word-chars (clojure.string/split "heels" #"")
        guess-chars (clojure.string/split "jello" #"")]
    (loop [correct-to-proc word-chars
           guesses-to-proc guess-chars
           in-position 0
           in-word 0]
      (if (seq guesses-to-proc)
        (if (= (first guesses-to-proc) (first correct-to-proc))
          (recur (rest correct-to-proc) (rest guesses-to-proc) (inc in-position) in-word)
          (recur (rest correct-to-proc) (rest guesses-to-proc) in-position in-word))
        {:correct-to-proc correct-to-proc
         :guesses-to-proc guesses-to-proc
         :in-position in-position
         :in-word in-word}))))
