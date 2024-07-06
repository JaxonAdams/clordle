# Clordle!
---
![clordle_img](https://github.com/JaxonAdams/clordle/assets/96997462/5b1ffb5f-da7d-4a91-8ff9-828d936e50d9)

Clordle is a simple Wordle clone powered by a Clojure backend and a vanilla JavaScript frontend. The rules of the game are simple: you have six attempts to guess today's five-letter word. If one of the letters in your guess exists in the same position in today's word, it will be marked as green. If a letter in your guess exists in today's word but is in the wrong position, it will be marked as yellow. If the letter is not in today's word, it remains white.

Clordle is currently hosted here: [Clordle](https://clordle-tw8u.onrender.com/)
*Please note that it may take up to a minute for the instance to spin up when you visit this site!*

If you would like to run this app locally, please see the instructions below.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server
