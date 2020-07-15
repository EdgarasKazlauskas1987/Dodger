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
(def game-status (atom :starting))

(defn count-time-elapsed
  "Counting time elapsed by inc after every frame" []
  (swap! time-elapsed inc))

(defn stop-game
  "Stopping the game when player lost all lives" []
  (reset! game-status :stopped))

(defn check-lives-left
  "Checking player's lives left
  If player has no lives left the game is stopped" []
  (when (neg? @player/player-lives)
    (stop-game)))

(defn draw-elapsed-time
  "Drawing elapsed time in seconds" []
  (quil/fill (quil/color 255 255 255))
  (quil/text-size 20)
  (quil/text (str "TIME: " (str (int (Math/floor (/ @time-elapsed 80.0))))) 10 30 40))

(defn draw-player-lives-left
  "Drawing amount of player lives left" []
  (quil/fill (quil/color 255 255 255))
  (quil/text-size 20)
  (quil/text (str "LIVES: " (str @player/player-lives)) 10 65 60))

(defn draw-start-menu-screen
  "Drawing start screen" []
  (quil/background 0 0 0)
  (quil/fill 255 255 255)
  (quil/text-font (quil/create-font "Tahoma Bold" 70))
  (quil/text "DODGER" 300 180)
  (quil/text-font (quil/create-font "Courier New Bold" 50))
  (quil/text "Play P" 360 300)
  (quil/text "Quit Q" 360 370)
  (let [enemy-img-1 (quil/state :enemy-right-160)
        enemy-img-2 (quil/state :enemy-bottom-160)
        player-img-3 (quil/state :ghost-140)]
    (when (quil/loaded? enemy-img-1)
      (quil/image enemy-img-1 742 170))
    (when (quil/loaded? enemy-img-2)
      (quil/image enemy-img-2 280 497))
    (when (quil/loaded? player-img-3)
      (quil/image player-img-3 60 162))))

(defn draw-game-over-screen
  "Drawing screen when game is over" []
  (quil/fill 0 0 0)
  (quil/rect 250 100 400 450)
  (quil/fill 255 255 255)
  (quil/text-font (quil/create-font "Tahoma Bold" 35))
  (quil/text "GAME OVER" 338 180)
  (quil/text-font (quil/create-font "Courier New Bold" 30))
  (quil/text (str "Your result is " (str (int (Math/floor (/ @time-elapsed 80.0))))) 295 275)
  (quil/text "Play again?" 340 395)
  (quil/text "Y/N" 417 445))

(defn start-new-game
  "Starting new game" []
  (top-screen/set-all-enemies-to-start-position)
  (bottom-screen/set-all-enemies-to-start-position)
  (left-screen/set-all-enemies-to-start-position)
  (right-screen/set-all-enemies-to-start-position)
  (reset! game-status :running)
  (reset! time-elapsed 0)
  (reset! player/player-lives 5))

(defn shut-down
  "Closing the game" []
  (System/exit 0))

(defn start-menu-key-panel
  "Is activated when a player is in the start menu" []
  (when (quil/key-pressed?)
    (cond
      (= (quil/key-as-keyword) :p) (start-new-game)
      (= (quil/key-as-keyword) :q) (shut-down))))

(defn game-over-menu-key-panel
  "Is activated when a player is choosing to play again or close the game" []
  (when (quil/key-pressed?)
    (cond
      (= (quil/key-as-keyword) :y) (start-new-game)
      (= (quil/key-as-keyword) :n) (shut-down))))

(defn draw
  "Drawing all elements of the game" []
  (quil/background 11)
  (when (= @game-status :starting)
    (draw-start-menu-screen))
  (when (= @game-status :running)
    (draw-elapsed-time)
    (draw-player-lives-left)
    (player/draw-player (get @player/player-coordinates :x) (get @player/player-coordinates :y)))
  (when (not= @game-status :starting)
    (bonus-life/bonus-life-update)
    (bonus-life/bonus-lifes-draw)
    (top-screen/top-enemies-update)
    (top-screen/top-enemies-draw)
    (when (> (/ @time-elapsed 80.0) 1)
      (bottom-screen/bottom-enemies-update)
      (bottom-screen/bottom-enemies-draw))
    (when (> (/ @time-elapsed 80.0) 3)
      (left-screen/left-enemies-update)
      (left-screen/left-enemies-draw))
    (when (> (/ @time-elapsed 80.0) 4)
      (right-screen/right-enemies-update)
      (right-screen/right-enemies-draw)))
  (when (= @game-status :stopped)
    (draw-game-over-screen)))

(quil/defsketch dodger
                :title "Dodger"
                :size [900 650]
                :setup (fn [] (quil/smooth) (quil/no-stroke) (quil/frame-rate 80)
                         (quil/set-state!
                           :star (quil/load-image "resources/star.png")
                           :ghost (quil/load-image "resources/ghost40.png")
                           :ghost-140 (quil/load-image "resources/ghost140.png")
                           :enemy-top-35 (quil/load-image "resources/ghosts/red-ghost-35.png")
                           :enemy-top-40 (quil/load-image "resources/ghosts/red-ghost-40.png")
                           :enemy-top-50 (quil/load-image "resources/ghosts/red-ghost-50.png")
                           :enemy-top-60 (quil/load-image "resources/ghosts/red-ghost-60.png")
                           :enemy-bottom-35 (quil/load-image "resources/ghosts/yellow-ghost-35.png")
                           :enemy-bottom-40 (quil/load-image "resources/ghosts/yellow-ghost-40.png")
                           :enemy-bottom-50 (quil/load-image "resources/ghosts/yellow-ghost-50.png")
                           :enemy-bottom-60 (quil/load-image "resources/ghosts/yellow-ghost-60.png")
                           :enemy-bottom-160 (quil/load-image "resources/ghosts/yellow-ghost-160.png")
                           :enemy-left-35 (quil/load-image "resources/ghosts/blue-ghost-35.png")
                           :enemy-left-40 (quil/load-image "resources/ghosts/blue-ghost-40.png")
                           :enemy-left-50 (quil/load-image "resources/ghosts/blue-ghost-50.png")
                           :enemy-left-60 (quil/load-image "resources/ghosts/blue-ghost-60.png")
                           :enemy-right-35 (quil/load-image "resources/ghosts/green-ghost-35.png")
                           :enemy-right-40 (quil/load-image "resources/ghosts/green-ghost-40.png")
                           :enemy-right-50 (quil/load-image "resources/ghosts/green-ghost-50.png")
                           :enemy-right-60 (quil/load-image "resources/ghosts/green-ghost-60.png")
                           :enemy-right-160 (quil/load-image "resources/ghosts/green-ghost-160.png")))
                :draw (fn [] (draw)
                        (when (= @game-status :running)
                          (count-time-elapsed)
                          (check-lives-left)))
                :key-pressed (fn [] (player/key-pressed)
                               (when (= @game-status :stopped)
                                 (game-over-menu-key-panel))
                               (when (= @game-status :starting
                                        (start-menu-key-panel)))))