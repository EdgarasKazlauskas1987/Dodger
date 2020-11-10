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

(defn start-new-game
  "Starting new game" []
  (player/set-to-central-positiion)
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

(defn starting-status-flow
  "Steps taken when game is in starting mode" []
  (graphics/draw-start-menu-screen))

(defn running-status-flow
  "Steps taken when game is in running mode" []
  (do
    (count-time-elapsed)
    (graphics/draw-elapsed-time @time-elapsed)
    (check-lives-left)
    (graphics/draw-player-lives-left @player/player-lives)
    (player/draw-player (get @player/player-coordinates :x) (get @player/player-coordinates :y))
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
      (right-screen/right-enemies-draw))))

(defn paused-status-flow
  "Steps taken when game is in paused mode" []
  (do
    (top-screen/top-enemies-draw)
    (bottom-screen/bottom-enemies-draw)
    (left-screen/left-enemies-draw)
    (right-screen/right-enemies-draw)
    (graphics/draw-pause-screen)))

  (defn stopped-status-flow
    "Steps taken when game is in stopped mode" []
    (do
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
        (right-screen/right-enemies-draw))
      (graphics/draw-game-over-screen @time-elapsed @current-record)))

(defn draw
  "Drawing all elements of the game" []
  (quil/background 11)
  (cond
    (= @game-status :starting) (starting-status-flow)
    (= @game-status :running) (running-status-flow)
    (= @game-status :stopped) (stopped-status-flow)
    (= @game-status :paused) (paused-status-flow)))

(quil/defsketch dodger
                :title "Dodger"
                :size [900 650]
                :setup (fn [] (quil/smooth) (quil/no-stroke) (quil/frame-rate 80)
                         (graphics/load-images) (utils/prepare-file))
                :draw (fn []
                        (draw) (player/player-movement))
                :key-pressed (fn [] (player/key-pressed)
                               (cond
                                 (= @game-status :starting) (start-menu-key-panel)
                                 (= @game-status :running) (pause-game)
                                 (= @game-status :paused) (pause-menu-key-panel)
                                 (= @game-status :stopped) (game-over-menu-key-panel)))
                :key-released (fn [] (player/key-released)))