(ns dodger.utils)

(defn generate-speed
  "Generating random enemy speed"
  []
  (rand-nth [3 4 5 6 7]))

(defn generate-x-coordinate
  "Generating random x coordinate"
  []
  (rand-int 870))

(defn generate-size
  "Generating random size"
  []
  (rand-nth [20 30 40 50]))