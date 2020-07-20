(ns dodger.enemies.right-screen
  (:require [quil.core :as quil]
            [dodger.player :as player]
            [dodger.utils :as utils]))

(defstruct right-enemy :x :y :speed)

(def default-x-coordinate 900)

;; Creating initial objects of enemies
(def enemy1
  (let [size (utils/generate-size)]
    (atom
      (struct-map right-enemy
        :x default-x-coordinate :y (utils/generate-y-coordinate) :width size :height size :speed (utils/generate-speed)))))

(def enemy2
  (let [size (utils/generate-size)]
    (atom
      (struct-map right-enemy
        :x default-x-coordinate :y (utils/generate-y-coordinate) :width size :height size :speed (utils/generate-speed)))))

(def enemy3
  (let [size (utils/generate-size)]
    (atom
      (struct-map right-enemy
        :x default-x-coordinate :y (utils/generate-y-coordinate) :width size :height size :speed (utils/generate-speed)))))

(def enemy4
  (let [size (utils/generate-size)]
    (atom
      (struct-map right-enemy
        :x default-x-coordinate :y (utils/generate-y-coordinate) :width size :height size :speed (utils/generate-speed)))))

(def right-enemies (seq [enemy1
                        enemy2
                        enemy3
                        enemy4]))

(defn outside?
  "Checking if a right screen enemy is outside of screen limits" [enemy]
  (if (neg? (get @enemy :x))
    true
    false))

(defn collision?
  "Checking if player collided with an enemy" [player-coordinates enemy]
  (if (and (or (and (>= (get player-coordinates :x) (get @enemy :x))
                    (<= (get player-coordinates :x) (+ (get @enemy :x) (get @enemy :width))))
               (and (>= (+ (get player-coordinates :x) 45) (get @enemy :x))
                    (<= (+ (get player-coordinates :x) 45) (+ (get @enemy :x) (get @enemy :width)))))
           (or (and (>= (get player-coordinates :y) (get @enemy :y))
                    (<= (get player-coordinates :y) (+ (get @enemy :y) (get @enemy :width))))
               (and (>= (+ (get player-coordinates :y) 45) (get @enemy :y))
                    (<= (+ (get player-coordinates :y) 45) (+ (get @enemy :y) (get @enemy :width))))))
    true
    false))

(defn set-to-start-position
  "Setting enemy to starting position" [enemy]
  (let [size (utils/generate-size)]
    (swap! enemy assoc :y (utils/generate-y-coordinate))
    (swap! enemy assoc :x 900)
    (swap! enemy assoc :width size)
    (swap! enemy assoc :height size)
    (swap! enemy assoc :speed (utils/generate-speed))))

(defn set-all-enemies-to-start-position
  "Setting all right enemies to starting position" []
  (doseq [enemy right-enemies]
    (set-to-start-position enemy)))

(defn right-enemies-update
  "Updating positions of all right screen enemies in the list" []
  (doseq [enemy right-enemies]
    (if (outside? enemy)
      (set-to-start-position enemy)
      (if (collision? @player/player-coordinates enemy)
        (do (player/dec-player-lives) (set-to-start-position enemy))
        (swap! enemy update-in [:x] - (get @enemy :speed))))))

(defn draw-right-enemy
  "Drawing right enemy object" [x y width]
  (let [image-size (case width
                     35 :enemy-right-35
                     40 :enemy-right-40
                     50 :enemy-right-50
                     60 :enemy-right-60)
        enemy-right (quil/state image-size)]
    (when (quil/loaded? enemy-right)
      (quil/image enemy-right x y))))

(defn right-enemies-draw
  "Drawing all right screen enemies in the list" []
  (doseq [enemy right-enemies]
    (draw-right-enemy (get @enemy :x) (get @enemy :y) (get @enemy :width))))