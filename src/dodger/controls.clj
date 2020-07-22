(ns dodger.controls)

(defn collision?
  "Checking if player collided with another object" [player-bounds object-bounds]
  (let [player (java.awt.Rectangle. (get player-bounds :x) (get player-bounds :y)
                                    30 45)
        object (java.awt.Rectangle. (get @object-bounds :x) (get @object-bounds :y)
                                    (get @object-bounds :width) (get @object-bounds :height))]
    (. player intersects object)))