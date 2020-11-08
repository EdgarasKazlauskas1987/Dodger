(ns dodger.core
  (:gen-class)
  (:require [quil.core :as quil]
            [dodger.player :as player]
            [dodger.enemies.top-screen :as top-screen]
            [dodger.enemies.bottom-screen :as bottom-screen]
            [dodger.enemies.left-screen :as left-screen]
            [dodger.enemies.right-screen :as right-screen]
            [dodger.star :as star]
            [dodger.graphics :as graphics]
            [dodger.settings :as settings]
            [dodger.utils :as utils]))

(def time-elapsed (atom 0))
(def game-status (atom :starting))
(def current-record (atom (read-string (utils/read-from-file))))

(defn read-current-record
  "Reading current record" []
  (reset! current-record (read-string (utils/read-from-file))))

(defn count-time-elapsed
  "Counting time elapsed by inc after every frame" []
  (swap! time-elapsed inc))

(defn stop-game
  "Stopping the game when player lost all lives" []
  (let [current-result (int (Math/floor (/ @time-elapsed 80.0)))]
    (when (utils/new-record? current-result (int @current-record))
      (utils/write-to-file current-result)))
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

(defn draw-pause-screen
  "Drawing screen when game is paused" []
  (quil/fill 0 0 0)
  (quil/rect 250 100 400 350)
  (quil/fill 255 255 255)
  (quil/text-font (quil/create-font "Tahoma Bold" 35))
  (quil/text "PAUSED" 360 180)
  (quil/text-font (quil/create-font "Courier New Bold" 30))
  (quil/text "Continue C" 360 250)
  (quil/text "Restart R" 360 320)
  (quil/text "Quit Q" 360 390))

(defn draw-game-over-screen
  "Drawing screen when game is over" []
  (let [result (int (Math/floor (/ @time-elapsed 80.0)))]
    (quil/fill 0 0 0)
    (quil/rect 250 100 400 450)
    (quil/fill 255 255 255)
    (quil/text-font (quil/create-font "Tahoma Bold" 35))
    (quil/text "GAME OVER" 338 180)
    (quil/text-font (quil/create-font "Courier New Bold" 30))
    (quil/text (str "Your result is " (str (int (Math/floor (/ @time-elapsed 80.0))))) 295 275)
    (when (utils/new-record? result (int @current-record))
      (quil/text "NEW RECORD!" 345 330))
    (quil/text "Play again?" 340 395)
    (quil/text "Y/N" 417 445))
)

(defn start-new-game
  "Starting new game" []
  (top-screen/set-all-enemies-to-start-position)
  (bottom-screen/set-all-enemies-to-start-position)
  (left-screen/set-all-enemies-to-start-position)
  (right-screen/set-all-enemies-to-start-position)
  (read-current-record)
  (reset! game-status :running)
  (reset! time-elapsed 0)
  (reset! player/player-lives settings/starting-lives))

(defn pause-game
  "Pausing game" []
  (when (quil/key-pressed?)
    (when (= (quil/key-as-keyword) :space)
      (reset! game-status :paused))))

(defn shut-down
  "Closing the game" []
  (System/exit 0))

(defn start-menu-key-panel
  "Is activated when a player is in the start menu" []
  (when (quil/key-pressed?)
    (cond
      (= (quil/key-as-keyword) :p) (start-new-game)
      (= (quil/key-as-keyword) :q) (shut-down))))

(defn pause-menu-key-panel
  "Is activated when a player is in the pause menu" []
  (when (quil/key-pressed?)
    (cond
      (= (quil/key-as-keyword) :c) (reset! game-status :running)
      (= (quil/key-as-keyword) :r) (start-new-game)
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
    (count-time-elapsed)
    (draw-elapsed-time)
    (check-lives-left)
    (draw-player-lives-left)
    (player/draw-player (get @player/player-coordinates :x) (get @player/player-coordinates :y)))
  (when (and (not= @game-status :starting) (not= @game-status :paused))
    (star/star-update)
    (star/stars-draw)
    (top-screen/top-enemies-update)
    (top-screen/top-enemies-draw)
    (when (> (/ @time-elapsed 80.0) settings/start-top-enemies-time)
      (bottom-screen/bottom-enemies-update)
      (bottom-screen/bottom-enemies-draw))
    (when (> (/ @time-elapsed 80.0) settings/start-left-enemies-time)
      (left-screen/left-enemies-update)
      (left-screen/left-enemies-draw))
    (when (> (/ @time-elapsed 80.0) settings/start-right-enemies-time)
      (right-screen/right-enemies-update)
      (right-screen/right-enemies-draw)))
  (when (= @game-status :stopped)
    (draw-game-over-screen))
  (when (= @game-status :paused)
    (top-screen/top-enemies-draw)
    (bottom-screen/bottom-enemies-draw)
    (left-screen/left-enemies-draw)
    (right-screen/right-enemies-draw)
    (draw-pause-screen)))

(quil/defsketch dodger
                :title "Dodger"
                :size [900 650]
                :setup (fn [] (quil/smooth) (quil/no-stroke) (quil/frame-rate 80)
                         (graphics/load-images) (utils/prepare-file))
                :draw (fn []
                        (draw)
                        (player/player-movement))
                :key-pressed (fn [] (player/key-pressed)
                               (when (= @game-status :starting)
                                 (start-menu-key-panel))
                               (when (= @game-status :running)
                                 (pause-game))
                               (when (= @game-status :paused)
                                 (pause-menu-key-panel))
                               (when (= @game-status :stopped)
                                 (game-over-menu-key-panel)))
                :key-released (fn [] (player/key-released)))