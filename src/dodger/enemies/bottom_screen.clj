(ns dodger.enemies.bottom-screen
  (:require [quil.core :as quil]
            [dodger.utils :as utils]
            [dodger.player :as player]))

(defstruct bottom-enemy :x :y :speed)

(def enemy1 (atom (struct-map bottom-enemy :x 20 :y 650 :width 20 :height 20 :speed 3)))
(def enemy2 (atom (struct-map bottom-enemy :x 50 :y 650 :width 20 :height 20 :speed 4)))
(def enemy3 (atom (struct-map bottom-enemy :x 80 :y 650 :width 20 :height 20 :speed 5)))
(def enemy4 (atom (struct-map bottom-enemy :x 110 :y 650 :width 20 :height 20 :speed 6)))

(def bottom-enemies (seq [enemy1
                           enemy2
                           enemy3
                           enemy4]))

(defn outside?
  "Checking if a bottom screen enemy is outside of screen limits"
  [enemy]
  (if (< (get @enemy :y) 0)
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
    (swap! enemy assoc :y 650)
    (swap! enemy assoc :x (utils/generate-x-coordinate))
    (swap! enemy assoc :width size)
    (swap! enemy assoc :height size)
    (swap! enemy assoc :speed (utils/generate-speed))))

(defn bottom-enemies-update
  "Updating positions of all top screen enemies in the list"
  []
  (doseq [enemy bottom-enemies]
    (if (outside? enemy)
      (set-to-start-position enemy)
      (do
        (if (collision? enemy)
          (do
            (player/dec-player-lives)
            (set-to-start-position enemy))
          (swap! enemy update-in [:y] - (get @enemy :speed))))
      )))

(defn draw-bottom-enemy
  "Drawing an enemy in green color"
  [x y length width]
  (quil/fill (quil/color 0 255 0))
  (quil/rect x y length width))

(defn bottom-enemies-draw
  "Drawing all bottom screen enemies in the list"
  []
  (doseq [enemy bottom-enemies]
    (draw-bottom-enemy (get @enemy :x) (get @enemy :y) (get @enemy :width) (get @enemy :height))))