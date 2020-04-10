(ns dodger.core
  (:gen-class)
  (:require [quil.core :as quil]
            [dodger.player :as player]))

(quil/defsketch pong
                :title "Dodge"
                :size [900 650]
                :setup (fn [] (quil/smooth) (quil/no-stroke) (quil/frame-rate 60))
                :draw (fn [] (player/draw))
                :key-pressed player/key-pressed)