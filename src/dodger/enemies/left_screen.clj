(ns dodger.enemies.left-screen
  (:require [quil.core :as quil]
            [dodger.utils :as utils]
            [dodger.player :as player]
            [dodger.controls :as controls]))

(defstruct left-enemy :x :y :speed)

(def default-x-coordinate 0)

;; Creating initial objects of enemies
(def enemy1
  (let [size (utils/generate-size)]
    (atom
      (struct-map left-enemy
        :x default-x-coordinate :y (utils/generate-y-coordinate) :width size :height size :speed (utils/generate-speed)))))

(def enemy2
  (let [size (utils/generate-size)]
    (atom
      (struct-map left-enemy
        :x default-x-coordinate :y (utils/generate-y-coordinate) :width size :height size :speed (utils/generate-speed)))))

(def enemy3
  (let [size (utils/generate-size)]
    (atom
      (struct-map left-enemy
        :x default-x-coordinate :y (utils/generate-y-coordinate) :width size :height size :speed (utils/generate-speed)))))

(def enemy4
  (let [size (utils/generate-size)]
    (atom
      (struct-map left-enemy
        :x default-x-coordinate :y (utils/generate-y-coordinate) :width size :height size :speed (utils/generate-speed)))))

(def enemy5
  (let [size (utils/generate-size)]
    (atom
      (struct-map left-enemy
        :x default-x-coordinate :y (utils/generate-y-coordinate) :width size :height size :speed (utils/generate-speed)))))

(def left-enemies (seq [enemy1
                        enemy2
                        enemy3
                        enemy4
                        enemy5]))

(defn set-to-start-position
  "Setting enemy to starting position" [enemy]
  (let [size (utils/generate-size)]
    (swap! enemy assoc :y (utils/generate-y-coordinate))
    (swap! enemy assoc :x 0)
    (swap! enemy assoc :width size)
    (swap! enemy assoc :height size)
    (swap! enemy assoc :speed (utils/generate-speed))))

(defn set-all-enemies-to-start-position
  "Setting all left enemies to starting position" []
  (doseq [enemy left-enemies]
    (set-to-start-position enemy)))

(defn left-enemies-update
  "Updating positions of all left screen enemies in the list" []
  (doseq [enemy left-enemies]
    (if (controls/outside? enemy)
      (set-to-start-position enemy)
      (if (controls/collision? @player/player-coordinates enemy)
        (do (player/dec-player-lives) (set-to-start-position enemy))
        (swap! enemy update-in [:x] + (get @enemy :speed))))))

(defn draw-left-enemy
  "Drawing left enemy object" [x y width]
  (let [image-size (case width
                     35 :enemy-left-35
                     40 :enemy-left-40
                     50 :enemy-left-50
                     60 :enemy-left-60)
        enemy-left (quil/state image-size)]
    (when (quil/loaded? enemy-left)
      (quil/image enemy-left x y))))

(defn left-enemies-draw
  "Drawing all left screen enemies in the list" []
  (doseq [enemy left-enemies]
    (draw-left-enemy (get @enemy :x) (get @enemy :y) (get @enemy :width))))