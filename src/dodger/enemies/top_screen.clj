(ns dodger.enemies.top-screen
  (:require [quil.core :as quil]
            [dodger.utils :as utils]))

(defstruct top-enemy :x :y :speed)

(def enemy1 (atom (struct-map top-enemy :x 20 :y 0 :width 20 :height 20 :speed 1)))
(def enemy2 (atom (struct-map top-enemy :x 50 :y 0 :width 20 :height 20 :speed 2)))
(def enemy3 (atom (struct-map top-enemy :x 80 :y 0 :width 20 :height 20 :speed 3)))
(def enemy4 (atom (struct-map top-enemy :x 110 :y 0 :width 20 :height 20 :speed 4)))
(def enemy5 (atom (struct-map top-enemy :x 140 :y 0 :width 20 :height 20 :speed 5)))

(def top-screen-enemies (seq [enemy1
                              enemy2
                              enemy3
                              enemy4
                              enemy5]))

(defn outside?
  "Checking if a top screen enemy is outside of screen limits"
  [enemy]
  (if (> (get @enemy :y) 650)
    true
    false))

(defn top-enemies-update
  "Updating positions of all top screen enemies in the list"
  []
  (doseq [enemy top-screen-enemies]
    (if (outside? enemy)
      (do
        (swap! enemy assoc :y 0)
        (swap! enemy assoc :x (utils/generate-x-coordinate))
        (let [size (utils/generate-size)]
          (swap! enemy assoc :width size)
          (swap! enemy assoc :height size))
        (swap! enemy assoc :speed (utils/generate-speed)))
      (swap! enemy update-in [:y] + (get @enemy :speed)))))

(defn draw-top-enemy
  "Drawing an enemy in red color"
  [x y length width]
  (quil/fill (quil/color 255 255 0))
  (quil/rect x y length width))

(defn top-enemies-draw
  "Drawing all top screen enemies in the list"
  []
  (doseq [enemy top-screen-enemies]
    (draw-top-enemy (get @enemy :x) (get @enemy :y) (get @enemy :width) (get @enemy :height))))

