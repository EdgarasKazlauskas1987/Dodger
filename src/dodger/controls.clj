(ns dodger.controls)

(defn collision?
  "Checking if player collided with another object" [player-bounds object-bounds]
  (let [player (java.awt.Rectangle. (get player-bounds :x) (get player-bounds :y)
                                    30 45)
        object (java.awt.Rectangle. (get @object-bounds :x) (get @object-bounds :y)
                                    (get @object-bounds :width) (get @object-bounds :height))]
    (.intersects player object)))

(defn outside?
  "Checking if an enemy is outside of the screen limits" [enemy]
  (if (or (neg? (get @enemy :y))                            ;bottom enemy leaves screen
          (> (get @enemy :y) 650)                           ;top enemy leaves screen
          (> (get @enemy :x) 900)                           ;left enemy leaves screen
          (neg? (get @enemy :x)))                           ;right enemy leaves screen
    true
    false))