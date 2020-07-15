(ns dodger.utils)

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
  "Generating random size" []
  (rand-nth [35 40 50 60]))