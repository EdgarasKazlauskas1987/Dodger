(ns dodger.enemies.top-screen
  (:require [quil.core :as quil]
            [dodger.utils :as utils]
            [dodger.player :as player]
            [dodger.controls :as controls]))

(defstruct top-enemy :x :y :speed)

(def default-y-coordinate 0)

;; Creating initial objects of enemies
(def enemy1
  (let [size (utils/generate-size)]
    (atom
      (struct-map top-enemy
        :x (utils/generate-x-coordinate) :y default-y-coordinate :width size :height size :speed (utils/generate-speed)))))

(def enemy2
  (let [size (utils/generate-size)]
    (atom
      (struct-map top-enemy
        :x (utils/generate-x-coordinate) :y default-y-coordinate :width size :height size :speed (utils/generate-speed)))))

(def enemy3
  (let [size (utils/generate-size)]
    (atom
      (struct-map top-enemy
        :x (utils/generate-x-coordinate) :y default-y-coordinate :width size :height size :speed (utils/generate-speed)))))

(def enemy4
  (let [size (utils/generate-size)]
    (atom
      (struct-map top-enemy
        :x (utils/generate-x-coordinate) :y default-y-coordinate :width size :height size :speed (utils/generate-speed)))))

(def top-enemies (seq [enemy1
                        enemy2
                        enemy3
                        enemy4]))

(defn set-to-start-position
  "Setting enemy to starting position" [enemy]
  (let [size (utils/generate-size)]
    (swap! enemy assoc :y 0)
    (swap! enemy assoc :x (utils/generate-x-coordinate))
    (swap! enemy assoc :width size)
    (swap! enemy assoc :height size)
    (swap! enemy assoc :speed (utils/generate-speed))))

(defn set-all-enemies-to-start-position
  "Setting all top enemies to starting position" []
  (doseq [enemy top-enemies]
    (set-to-start-position enemy)))

(defn top-enemies-update
  "Updating positions of all top screen enemies in the list" []
  (doseq [enemy top-enemies]
    (if (controls/outside? enemy)
      (set-to-start-position enemy)
      (if (controls/collision? @player/player-coordinates enemy)
        (do (player/dec-player-lives) (set-to-start-position enemy))
        (swap! enemy update-in [:y] + (get @enemy :speed))))))

(defn draw-top-enemy
  "Drawing top enemy object" [x y width]
  (let [image-size (case width
                     35 :enemy-top-35
                     40 :enemy-top-40
                     50 :enemy-top-50
                     60 :enemy-top-60)
        enemy-top (quil/state image-size)]
    (when (quil/loaded? enemy-top)
      (quil/image enemy-top x y))))

(defn top-enemies-draw
  "Drawing all top screen enemies in the list" []
  (doseq [enemy top-enemies]
    (draw-top-enemy (get @enemy :x) (get @enemy :y) (get @enemy :width))))