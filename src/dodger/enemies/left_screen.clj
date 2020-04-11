(ns dodger.enemies.left-screen
  (:require [quil.core :as quil]
            [dodger.player :as player]
            [dodger.utils :as utils]))

(defstruct left-enemy :x :y :speed)

(def enemy1 (atom (struct-map left-enemy :x 0 :y 590 :width 20 :height 20 :speed 3)))
(def enemy2 (atom (struct-map left-enemy :x 0 :y 400 :width 20 :height 20 :speed 4)))
(def enemy3 (atom (struct-map left-enemy :x 0 :y 300 :width 20 :height 20 :speed 5)))
(def enemy4 (atom (struct-map left-enemy :x 0 :y 200 :width 20 :height 20 :speed 6)))

(def enemy5 (atom (struct-map left-enemy :x 0 :y 150 :width 20 :height 20 :speed 6)))
(def enemy6 (atom (struct-map left-enemy :x 0 :y 50 :width 20 :height 20 :speed 6)))

(def left-enemies (seq [enemy1
                        enemy2
                        enemy3
                        enemy4]))

(defn outside?
  "Checking if a left screen enemy is outside of screen limits"
  [enemy]
  (if (> (get @enemy :x) 900)
    true
    false))

(defn collision?
  "Checking if player collided with an enemy"
  [enemy]
  (if (and (or (and (>= (get @player/player-coordinates :x) (get @enemy :x))
                    (<= (get @player/player-coordinates :x) (+ (get @enemy :x) (get @enemy :width))))
               (and (>= (+ (get @player/player-coordinates :x) 45) (get @enemy :x))
                    (<= (+ (get @player/player-coordinates :x) 45) (+ (get @enemy :x) (get @enemy :width)))))
           (or (and (>= (get @player/player-coordinates :y) (get @enemy :y))
                    (<= (get @player/player-coordinates :y) (+ (get @enemy :y) (get @enemy :width))))
               (and (>= (+ (get @player/player-coordinates :y) 45) (get @enemy :y))
                    (<= (+ (get @player/player-coordinates :y) 45) (+ (get @enemy :y) (get @enemy :width))))))
    true
    false))

(defn set-to-start-position
  "Setting enemy to starting position"
  [enemy]
  (let [size (utils/generate-size)]
    (swap! enemy assoc :y (utils/generate-y-coordinate))
    (swap! enemy assoc :x 0)
    (swap! enemy assoc :width size)
    (swap! enemy assoc :height size)
    (swap! enemy assoc :speed (utils/generate-speed))))

(defn left-enemies-update
  "Updating positions of all left screen enemies in the list"
  []
  (doseq [enemy left-enemies]
    (if (outside? enemy)
      (set-to-start-position enemy)
      (do
        (if (collision? enemy)
          (do
            (player/dec-player-lives)
            (set-to-start-position enemy))
          (swap! enemy update-in [:x] + (get @enemy :speed))))
      )))

(defn draw-left-enemy
  "Drawing an enemy in x color"
  [x y length width]
  (quil/fill (quil/color 0 255 255))
  (quil/rect x y length width))

(defn left-enemies-draw
  "Drawing all left screen enemies in the list"
  []
  (doseq [enemy left-enemies]
    (draw-left-enemy (get @enemy :x) (get @enemy :y) (get @enemy :width) (get @enemy :height))))