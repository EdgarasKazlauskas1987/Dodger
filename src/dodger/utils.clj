(ns dodger.utils
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def high-score-file "resources/high_score.txt")
(def settings-file "resources/settings.edn")

(defn format-type [size type]
  (keyword (str "enemy-" type "-"size)))

(defn img-size-converter [width type]
  (case width
    35 (format-type 35 type)
    40 (format-type 40 type)
    50 (format-type 50 type)
    60 (format-type 60 type)))

(defn calc-sec-elapsed [time]
  (/ time 80.0))

(defn generate-speed
  "Generating random enemy speed" []
  (rand-nth [1 2 3]))

(defn generate-x-coordinate
  "Generating random x coordinate between 0 and 900 with deducted objects width"
  [width]
  (rand-int (- 900 width)))

(defn generate-y-coordinate
  "Generating random y coordinate between 0 and 650 with deducted objects width"
  [height]
  (rand-int (- 650 height)))

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