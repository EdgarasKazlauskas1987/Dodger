(ns dodger.enemies.right-screen
  (:require [quil.core :as quil]
            [dodger.utils :as utils]
            [dodger.player :as player]
            [dodger.controls :as controls]))

(defstruct right-enemy :x :y :speed)

(def default-x-coordinate 900)

;; Creating initial objects of enemies
(def right-enemies (atom (seq '())))

(defn generate-right-enemies []
  (let [amount (get-in (utils/read-settings) [:amount :right-enemies])
        size (utils/generate-size)]
    (while (< (count @right-enemies) amount)
      (swap! right-enemies conj (atom
                                  (struct-map right-enemy
                                    :x default-x-coordinate :y (utils/generate-y-coordinate size) :width size :height size :speed (utils/generate-speed)))))))

(defn set-to-start-position
  "Setting enemy to starting position" [enemy]
  (let [size (utils/generate-size)]
    (swap! enemy assoc :y (utils/generate-y-coordinate size))
    (swap! enemy assoc :x default-x-coordinate)
    (swap! enemy assoc :width size)
    (swap! enemy assoc :height size)
    (swap! enemy assoc :speed (utils/generate-speed))))

(defn set-all-enemies-to-start-position
  "Setting all right enemies to starting position" []
  (doseq [enemy @right-enemies]
    (set-to-start-position enemy)))

(defn right-enemies-update
  "Updating positions of all right screen enemies in the list" []
  (doseq [enemy @right-enemies]
    (if (controls/outside? enemy)
      (set-to-start-position enemy)
      (if (controls/collision? @player/player-coordinates enemy)
        (do (player/dec-player-lives) (set-to-start-position enemy))
        (swap! enemy update-in [:x] - (get @enemy :speed))))))

(defn draw-right-enemy
  "Drawing right enemy object" [{:keys [x y width]}]
  (let [image-size (utils/img-size-converter width "right")
        enemy-right (quil/state image-size)]
    (when (quil/loaded? enemy-right)
      (quil/image enemy-right x y))))

(defn right-enemies-draw
  "Drawing all right screen enemies in the list" []
  (doseq [enemy @right-enemies]
    (draw-right-enemy @enemy)))