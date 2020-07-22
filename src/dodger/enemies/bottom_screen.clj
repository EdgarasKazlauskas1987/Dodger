(ns dodger.enemies.bottom-screen
  (:require [quil.core :as quil]
            [dodger.utils :as utils]
            [dodger.player :as player]
            [dodger.controls :as controls]))

(defstruct bottom-enemy :x :y :speed)

(def default-y-coordinate 650)

;; Creating initial objects of enemies
(def enemy1
  (let [size (utils/generate-size)]
    (atom
      (struct-map bottom-enemy
        :x (utils/generate-x-coordinate) :y default-y-coordinate :width size :height size :speed (utils/generate-speed)))))

(def enemy2
  (let [size (utils/generate-size)]
    (atom
      (struct-map bottom-enemy
        :x (utils/generate-x-coordinate) :y default-y-coordinate :width size :height size :speed (utils/generate-speed)))))

(def enemy3
  (let [size (utils/generate-size)]
    (atom
      (struct-map bottom-enemy
        :x (utils/generate-x-coordinate) :y default-y-coordinate :width size :height size :speed (utils/generate-speed)))))

(def enemy4
  (let [size (utils/generate-size)]
    (atom
      (struct-map bottom-enemy
        :x (utils/generate-x-coordinate) :y default-y-coordinate :width size :height size :speed (utils/generate-speed)))))

(def bottom-enemies (seq [enemy1
                          enemy2
                          enemy3
                          enemy4]))

(defn set-to-start-position
  "Setting enemy to starting position" [enemy]
  (let [size (utils/generate-size)]
    (swap! enemy assoc :y 650)
    (swap! enemy assoc :x (utils/generate-x-coordinate))
    (swap! enemy assoc :width size)
    (swap! enemy assoc :height size)
    (swap! enemy assoc :speed (utils/generate-speed))))

(defn set-all-enemies-to-start-position
  "Setting all bottom enemies to starting position" []
  (doseq [enemy bottom-enemies]
    (set-to-start-position enemy)))

(defn bottom-enemies-update
  "Updating positions of all top screen enemies in the list" []
  (doseq [enemy bottom-enemies]
    (if (controls/outside? enemy)
      (set-to-start-position enemy)
      (if (controls/collision? @player/player-coordinates enemy)
        (do (player/dec-player-lives) (set-to-start-position enemy))
        (swap! enemy update-in [:y] - (get @enemy :speed))))))

(defn draw-bottom-enemy
  "Drawing bottom enemy object" [x y width]
  (let [image-size (case width
               35 :enemy-bottom-35
               40 :enemy-bottom-40
               50 :enemy-bottom-50
               60 :enemy-bottom-60)
        enemy-bottom (quil/state image-size)]
    (when (quil/loaded? enemy-bottom)
      (quil/image enemy-bottom x y))))

(defn bottom-enemies-draw
  "Drawing all bottom screen enemies in the list" []
  (doseq [enemy bottom-enemies]
    (draw-bottom-enemy (get @enemy :x) (get @enemy :y) (get @enemy :width))))