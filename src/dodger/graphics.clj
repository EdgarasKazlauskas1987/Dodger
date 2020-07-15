(ns dodger.graphics
  (:require [quil.core :as quil]))

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
