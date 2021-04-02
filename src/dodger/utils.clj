(ns dodger.utils
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def high-score-file "resources/high_score.txt")
(def settings-file "resources/settings.edn")

(defn generate-speed
  "Generating random enemy speed" []
  (rand-nth [1 2 3]))

(defn generate-x-coordinate
  "Generating random x coordinate between 0 and 880" []
  (rand-int 880))

(defn generate-y-coordinate
  "Generating random y coordinate between 0 and 630" []
  (rand-int 630))

(defn generate-size
  "Generating random enemy size" []
  (rand-nth [35 40 50 60]))

(defn addLife?
  "Decides if player gets a life after taking a star" []
  (if (zero? (rand-int 10))
    true
    false))

(defn prepare-high-score []
  (when (false?
          (.exists (io/as-file high-score-file)))
    (spit high-score-file "0")))

(defn read-high-score
  "Reading content from high score file" []
  (prepare-high-score)
  (slurp high-score-file))

(defn write-high-score
  "Writing new high score to file" [score]
  (spit high-score-file score))

(defn new-record?
  "Checking if its a new record" [current-result current-record]
  (if (> current-result current-record)
    true
    false))

(defn read-settings
  "Reading content from settings file" []
  (edn/read-string (slurp settings-file)))