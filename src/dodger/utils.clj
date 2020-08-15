(ns dodger.utils
  (:require [clojure.java.io :as io]))

(def high-score-file "resources/high_score.txt")

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

(defn file-exist?
  "Checking if high score file exists" []
  (.exists (io/as-file high-score-file)))

(defn create-file
  "Creating high score file with starting value" []
  (spit high-score-file "0"))

(defn prepare-file
  "Preparing high score file" []
  (when (false? (file-exist?))
    (create-file)))

(defn read-from-file
  "Reading content from high score file" []
  (prepare-file)
  (slurp high-score-file))

(defn write-to-file
  "Writing new high score to file" [score]
  (spit high-score-file score))

(defn new-record?
  "Checking if its a new record" [current-result current-record]
  (if (> current-result current-record)
    true
    false))

