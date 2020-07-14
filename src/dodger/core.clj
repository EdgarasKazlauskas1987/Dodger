(ns dodger.core
  (:gen-class)
  (:require [quil.core :as quil]
            [dodger.player :as player]
            [dodger.enemies.top-screen :as top-screen]
            [dodger.enemies.bottom-screen :as bottom-screen]
            [dodger.enemies.left-screen :as left-screen]
            [dodger.enemies.right-screen :as right-screen]
            [dodger.bonus.life :as bonus-life]))

(def time-elapsed (atom 0))
(def game-running (atom true))

(defn count-time-elapsed
  "Counting time elapsed by inc after every frame"
  []
  (swap! time-elapsed inc))

(defn stop-game
  "Stopping the game when player lost all lives"
  []
  (reset! game-running false))

(defn check-lives-left
  "Checking player's lives left
  If player has no lives left the game is stopped"
  []
  (when (< @player/player-lives 0)
    (stop-game)))

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

(defn draw-game-over-screen
  "Drawing screen when game is over"
  []
  (quil/fill 0 0 0)
  (quil/rect 250 100 400 450)
  (quil/fill 255 255 255)
  (quil/text-size 15)
  (quil/text "GAME OVER" 260 120 50)
  (quil/text (str "Your score is " (str (int (Math/floor (/ @time-elapsed 80.0)))) " seconds") 260 150 50)
  (quil/text "Do you want to play again?" 260 170 50)
  (quil/text "Y/N" 260 190 50))

(defn start-new-game
  "Starting new game"
  []
  (reset! game-running true)
  (reset! time-elapsed 0)
  (reset! player/player-lives 5))

(defn menu-key-pressed
  "Is activated when a player is choosing to play again or close the game"
  []
  (when (quil/key-pressed?)
    (cond
      (= (quil/key-as-keyword) :y) (start-new-game)
      (= (quil/key-as-keyword) :n) (System/exit 0))))

(defn draw
  "Drawing all elements of the game"
  []
  (quil/background 11)
  (when (true? @game-running)
    (do
      (draw-elapsed-time)
      (draw-player-lives-left)
      (player/draw-player (get @player/player-coordinates :x) (get @player/player-coordinates :y))))
  (bonus-life/bonus-life-update)
  (bonus-life/bonus-lifes-draw)
  (top-screen/top-enemies-update)
  (top-screen/top-enemies-draw)
  (when (> (/ @time-elapsed 80.0) 1)
    (do
      (bottom-screen/bottom-enemies-update)
      (bottom-screen/bottom-enemies-draw)))
  (when (> (/ @time-elapsed 80.0) 3)
    (do
      (left-screen/left-enemies-update)
      (left-screen/left-enemies-draw)))
  (when (> (/ @time-elapsed 80.0) 4)
    (do
      (right-screen/right-enemies-update)
      (right-screen/right-enemies-draw)))
  (when (false? @game-running)
    (draw-game-over-screen)))

(quil/defsketch dodger
                :title "Dodger"
                :size [900 650]
                :setup (fn [] (quil/smooth) (quil/no-stroke) (quil/frame-rate 80)
                         (quil/set-state!
                           :star (quil/load-image "resources/star.png")
                           :ghost (quil/load-image "resources/ghost40.png")
                           :enemy-top-35 (quil/load-image "resources/ghosts/red-ghost-35.png")
                           :enemy-top-40 (quil/load-image "resources/ghosts/red-ghost-40.png")
                           :enemy-top-50 (quil/load-image "resources/ghosts/red-ghost-50.png")
                           :enemy-top-60 (quil/load-image "resources/ghosts/red-ghost-60.png")
                           :enemy-bottom-35 (quil/load-image "resources/ghosts/yellow-ghost-35.png")
                           :enemy-bottom-40 (quil/load-image "resources/ghosts/yellow-ghost-40.png")
                           :enemy-bottom-50 (quil/load-image "resources/ghosts/yellow-ghost-50.png")
                           :enemy-bottom-60 (quil/load-image "resources/ghosts/yellow-ghost-60.png")
                           :enemy-left-35 (quil/load-image "resources/ghosts/blue-ghost-35.png")
                           :enemy-left-40 (quil/load-image "resources/ghosts/blue-ghost-40.png")
                           :enemy-left-50 (quil/load-image "resources/ghosts/blue-ghost-50.png")
                           :enemy-left-60 (quil/load-image "resources/ghosts/blue-ghost-60.png")
                           :enemy-right-35 (quil/load-image "resources/ghosts/green-ghost-35.png")
                           :enemy-right-40 (quil/load-image "resources/ghosts/green-ghost-40.png")
                           :enemy-right-50 (quil/load-image "resources/ghosts/green-ghost-50.png")
                           :enemy-right-60 (quil/load-image "resources/ghosts/green-ghost-60.png")))
                :draw (fn [] (draw)
                        (when (true? @game-running)
                          (do
                            (count-time-elapsed)
                            (check-lives-left))))
                :key-pressed (fn [] (player/key-pressed)
                               (when (false? @game-running)
                                 (menu-key-pressed))))