(ns clordle.game-test
  (:require [clojure.test :refer [deftest is testing]]
            [clordle.game :as game]))

(deftest test-game-words
  (testing "words list is not empty"
    (is (> (count (game/get-words)) 1)))
  (testing "daily-word returns a word"
    (is (string? (game/daily-word))))
  (testing "daily-word is pulled from words list"
    (is (contains? (set (game/get-words)) (game/daily-word))))
  (testing "daily-word is consistent"
    (is (= (game/daily-word) (game/daily-word)))))

(deftest test-game-check-character
  (testing "character is in position"
    (is (= {"a" :in-position} (game/check-character "stair" \a 2))))
  (testing "character is in word"
    (is (= {"a" :in-word} (game/check-character "stair" \a 1))))
  (testing "character is incorrect")
    (is (= {"z" :incorrect} (game/check-character "stair" \z 0))))

(deftest test-game-check-solution
    (testing "response returned for all five characters"
      (is (= (count (game/check-solution "happy")) 5))
      (is (= (count (game/check-solution "aaaaa")) 5)))
    (testing "response is correct"
      (with-redefs [game/daily-word (constantly "stair")]
        (is (= (game/check-solution "stair") 
               [{"s" :in-position} {"t" :in-position} {"a" :in-position} {"i" :in-position} {"r" :in-position}]))
        (is (= (game/check-solution "stare") 
               [{"s" :in-position} {"t" :in-position} {"a" :in-position} {"r" :in-word} {"e" :incorrect}]))
        (is (= (game/check-solution "hello") 
               [{"h" :incorrect} {"e" :incorrect} {"l" :incorrect} {"l" :incorrect} {"o" :incorrect}])))))

(deftest test-game-solution-edge-cases
  (testing "empty string is not accepted"
    (is (thrown? IllegalArgumentException (game/check-solution ""))))
  (testing "non-alphabetic characters are not accepted"
    (is (thrown? IllegalArgumentException (game/check-solution "12345"))))
  (testing "words longer than 5 characters are not accepted"
    (is (thrown? IllegalArgumentException (game/check-solution "abcdef"))))
  (testing "words shorter than 5 characters are not accepted"
    (is (thrown? IllegalArgumentException (game/check-solution "abcd"))))
  (testing "words with uppercase letters are converted to lowercase"
    (is (= (game/check-solution "STAIR") (game/check-solution "stair")))))
