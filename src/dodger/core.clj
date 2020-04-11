(ns dodger.core
  (:gen-class)
  (:require [quil.core :as quil]
            [dodger.player :as player]
            [dodger.enemies.top-screen :as top-screen]
            [dodger.enemies.bottom-screen :as bottom-screen]
            [dodger.enemies.left-screen :as left-screen]))

(def time-elapsed (atom 0))

(defn count-time-elapsed
  "Counting time elapsed by inc after every frame"
  []
  (swap! time-elapsed inc))

(defn draw-elapsed-time
  "Drawing elapsed time in seconds"
  []
  (quil/fill (quil/color 255 255 255))
  (quil/text-size 20)
  (quil/text (str "TIME: " (str (int (Math/floor (/ @time-elapsed 80.0))))) 10 30 40))

(defn draw-player-lives-left
  "Drawing amount of player lives left"
  []
  (quil/fill (quil/color 255 255 255))
  (quil/text-size 20)
  (quil/text (str "LIVES: " (str @player/player-lives)) 10 65 60))

(defn draw
  "Drawing all elements of the game"
  []
  (quil/background 11)
  (draw-elapsed-time)
  (draw-player-lives-left)
  (player/draw-player (get @player/player-coordinates :x) (get @player/player-coordinates :y))
  (top-screen/top-enemies-update)
  (top-screen/top-enemies-draw)
  (when (> (/ @time-elapsed 80.0) 10)
    (do
      (bottom-screen/bottom-enemies-update)
      (bottom-screen/bottom-enemies-draw)))
  (when (> (/ @time-elapsed 80.0) 20)
    (do
      (left-screen/left-enemies-update)
      (left-screen/left-enemies-draw))))

(quil/defsketch pong
                :title "Dodge"
                :size [900 650]
                :setup (fn [] (quil/smooth) (quil/no-stroke) (quil/frame-rate 80))
                :draw (fn [] (draw) (count-time-elapsed))
                :key-pressed player/key-pressed)