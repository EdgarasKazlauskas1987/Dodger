(ns dodger.graphics
  (:require [quil.core :as quil]
            [dodger.utils :as utils]))

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
  "Drawing screen when game is over" [time-elapsed current-record]
  (let [result (int (Math/floor (/ time-elapsed 80.0)))]
    (quil/fill 0 0 0)
    (quil/rect 250 100 400 450)
    (quil/fill 255 255 255)
    (quil/text-font (quil/create-font "Tahoma Bold" 35))
    (quil/text "GAME OVER" 338 180)
    (quil/text-font (quil/create-font "Courier New Bold" 30))
    (quil/text (str "Your result is " (str (int (Math/floor (/ time-elapsed 80.0))))) 295 275)
    (when (utils/new-record? result (int current-record))
      (quil/text "NEW RECORD!" 345 330))
    (quil/text "Play again?" 340 395)
    (quil/text "Y/N" 417 445)))

(defn draw-elapsed-time
  "Drawing elapsed time in seconds" [time-elapsed]
  (quil/fill (quil/color 255 255 255))
  (quil/text-size 20)
  (quil/text (str "TIME: " (str (int (Math/floor (/ time-elapsed 80.0))))) 10 30 40))

(defn draw-player-lives-left
  "Drawing amount of player lives left" [player-lives]
  (quil/fill (quil/color 255 255 255))
  (quil/text-size 20)
  (quil/text (str "LIVES: " (str player-lives)) 10 65 60))

(defn load-images
  "Loading images that are used in the game" []
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
