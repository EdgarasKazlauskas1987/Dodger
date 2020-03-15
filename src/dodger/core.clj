(ns dodger.core
  (:gen-class)
  (:require [quil.core :as quil]))

(defn draw-player [x y]
  (quil/rect x y 50 50))

(def player-coordinates (atom {:x 410 :y 400}))

(defn move-player-up []
  (swap! player-coordinates update-in [:y] - 7))

(defn move-player-down []
  (swap! player-coordinates update-in [:y] + 7))

(defn move-player-left []
  (swap! player-coordinates update-in [:x] - 7))

(defn move-player-right []
  (swap! player-coordinates update-in [:x] + 7))

(defn player-movement
  "Checking which key is pressed and calling corresponding function"
  [key]
  (cond
    (= key :up) (move-player-up)
    (= key :down) (move-player-down)
    (= key :left) (move-player-left)
    (= key :right) (move-player-right)))

(defn key-pressed
  "Is activated when a key is pressed"
  []
  (when (quil/key-pressed?)
    (player-movement (quil/key-as-keyword))))

(defn draw []
  (quil/background 11)
  (draw-player (get @player-coordinates :x) (get @player-coordinates :y)))

(quil/defsketch pong
                :title "Dodge"
                :size [900 550]
                :setup (fn [] (quil/smooth) (quil/no-stroke) (quil/frame-rate 60))
                :draw (fn [] (draw))
                :key-pressed key-pressed)