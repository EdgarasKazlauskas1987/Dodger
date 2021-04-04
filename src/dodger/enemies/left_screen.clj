(ns dodger.enemies.left-screen
  (:require [quil.core :as quil]
            [dodger.utils :as utils]
            [dodger.player :as player]
            [dodger.controls :as controls]))

(defstruct left-enemy :x :y :speed)

(def default-x-coordinate 0)

;; Creating initial objects of enemies
(def left-enemies (atom (seq '())))

(defn generate-left-enemies []
  (let [amount (get-in (utils/read-settings) [:amount :left-enemies])
        size (utils/generate-size)]
    (while (< (count @left-enemies) amount)
      (swap! left-enemies conj (atom
                                (struct-map left-enemy
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
  "Setting all left enemies to starting position" []
  (doseq [enemy @left-enemies]
    (set-to-start-position enemy)))

(defn left-enemies-update
  "Updating positions of all left screen enemies in the list" []
  (doseq [enemy @left-enemies]
    (if (controls/outside? enemy)
      (set-to-start-position enemy)
      (if (controls/collision? @player/player-coordinates enemy)
        (do (player/dec-player-lives) (set-to-start-position enemy))
        (swap! enemy update-in [:x] + (get @enemy :speed))))))

(defn draw-left-enemy
  "Drawing left enemy object" [{:keys [x y width]}]
  (let [image-size (utils/img-size-converter width "left")
        enemy-left (quil/state image-size)]
    (when (quil/loaded? enemy-left)
      (quil/image enemy-left x y))))

(defn left-enemies-draw
  "Drawing all left screen enemies in the list" []
  (doseq [enemy @left-enemies]
    (draw-left-enemy @enemy)))