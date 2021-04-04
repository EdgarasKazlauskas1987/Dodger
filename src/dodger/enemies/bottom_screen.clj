(ns dodger.enemies.bottom-screen
  (:require [quil.core :as quil]
            [dodger.utils :as utils]
            [dodger.player :as player]
            [dodger.controls :as controls]))

(defstruct bottom-enemy :x :y :speed)

(def default-y-coordinate 650)

;; Creating initial objects of enemies
(def bottom-enemies (atom (seq '())))

(defn generate-bottom-enemies []
  (let [size (utils/generate-size)]
    (while (< (count @bottom-enemies) 5)
      (swap! bottom-enemies conj (atom
                                 (struct-map bottom-enemy
                                   :x (utils/generate-x-coordinate size) :y default-y-coordinate :width size :height size :speed (utils/generate-speed)))))))

(defn set-to-start-position
  "Setting enemy to starting position" [enemy]
  (let [size (utils/generate-size)]
    (swap! enemy assoc :y 650)
    (swap! enemy assoc :x (utils/generate-x-coordinate size))
    (swap! enemy assoc :width size)
    (swap! enemy assoc :height size)
    (swap! enemy assoc :speed (utils/generate-speed))))

(defn set-all-enemies-to-start-position
  "Setting all bottom enemies to starting position" []
  (doseq [enemy @bottom-enemies]
    (set-to-start-position enemy)))

(defn bottom-enemies-update
  "Updating positions of all top screen enemies in the list" []
  (doseq [enemy @bottom-enemies]
    (if (controls/outside? enemy)
      (set-to-start-position enemy)
      (if (controls/collision? @player/player-coordinates enemy)
        (do (player/dec-player-lives) (set-to-start-position enemy))
        (swap! enemy update-in [:y] - (get @enemy :speed))))))

(defn draw-bottom-enemy
  "Drawing bottom enemy object" [{:keys [x y width]}]
  (let [image-size (utils/img-size-converter width "bottom")
        enemy-bottom (quil/state image-size)]
    (when (quil/loaded? enemy-bottom)
      (quil/image enemy-bottom x y))))

(defn bottom-enemies-draw
  "Drawing all bottom screen enemies in the list" []
  (doseq [enemy @bottom-enemies]
    (draw-bottom-enemy @enemy)))