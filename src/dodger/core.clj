(ns dodger.core
  (:gen-class)
  (:require [quil.core :as quil]
            [dodger.player :as player]
            [dodger.enemies.top-screen :as top-screen]))

(defn draw []
  (quil/background 11)
  (player/draw-player (get @player/player-coordinates :x) (get @player/player-coordinates :y))
  (top-screen/top-enemies-update)
  (top-screen/top-enemies-draw)
  )

(quil/defsketch pong
                :title "Dodge"
                :size [900 650]
                :setup (fn [] (quil/smooth) (quil/no-stroke) (quil/frame-rate 80))
                :draw (fn [] (draw))
                :key-pressed player/key-pressed)