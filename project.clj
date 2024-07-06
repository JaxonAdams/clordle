(defproject clordle "0.1.0-SNAPSHOT"
  :description "A Clojure web server hosting a Wordle clone."
  :url "https://clordle-tw8u.onrender.com/"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [cheshire "5.13.0"]
                 [hiccup "2.0.0-RC3"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler clordle.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
