(ns dodger.star
  (:require [quil.core :as quil]
            [dodger.utils :as utils]
            [dodger.player :as player]
            [dodger.controls :as controls]))

(def str-width (get-in (utils/read-settings) [:sizes :star :width]))
(def str-height (get-in (utils/read-settings) [:sizes :star :height]))

(def star (atom {:x        (utils/generate-x-coordinate str-width)
                 :y        (utils/generate-y-coordinate str-height)
                 :addsLife (utils/addLife?)
                 :width str-width :height str-height :time 0}))

(def stars (seq [star]))

(defn set-to-new-position
  "Setting star object to new position" [star]
  (swap! star assoc :x (utils/generate-x-coordinate str-width))
  (swap! star assoc :y (utils/generate-y-coordinate str-height))
  (swap! star assoc :addsLife (utils/addLife?))
  (swap! star assoc :time 0))

(defn star-update
  "Updating star objects" []
  (doseq [star stars]
    (do
      (swap! star update-in [:time] inc)
      (when (> (/ (get @star :time) 80.0) 10)
        (set-to-new-position star)
        (player/dec-player-lives))
      (when (controls/collision? @player/player-coordinates star)
        (do
          (when (true? (get @star :addsLife))
            (player/inc-player-lives))
          (set-to-new-position star))))))

(defn draw-star
  "Drawing star object" [x y]
  (let [star (quil/state :star)]
    (when (quil/loaded? star) (quil/image star x y))))

(defn stars-draw
  "Drawing all star objects in the list" []
  (doseq [star stars]
    (draw-star (get @star :x) (get @star :y))))