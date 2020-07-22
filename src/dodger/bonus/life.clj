(ns dodger.bonus.life
  (:require [quil.core :as quil]
            [dodger.utils :as utils]
            [dodger.player :as player]
            [dodger.controls :as controls]))

(def bonus-life (atom {:x (utils/generate-x-coordinate) :y (utils/generate-y-coordinate)
                       :width 50 :height 50 :time 0}))

(def bonus-lives (seq [bonus-life]))

(defn set-to-new-position
  "Setting bonus life object to new position" [bonus-life]
  (swap! bonus-life assoc :x (utils/generate-x-coordinate))
  (swap! bonus-life assoc :y (utils/generate-y-coordinate))
  (swap! bonus-life assoc :time 0))

(defn bonus-life-update
  "Updating bonus life objects" []
  (doseq [life bonus-lives]
    (do
      (swap! life update-in [:time] inc)
      (when (> (/ (get @life :time) 80.0) 15)
        (set-to-new-position life))
      (when (controls/collision? @player/player-coordinates life)
        (set-to-new-position life)
        (player/inc-player-lives)))))

(defn draw-bonus-life
  "Drawing bonus life object" [x y]
  (let [star (quil/state :star)]
    (when (quil/loaded? star) (quil/image star x y))))

(defn bonus-lifes-draw
  "Drawing all bonus life objects in the list" []
  (doseq [life bonus-lives]
    (draw-bonus-life (get @life :x) (get @life :y))))