(ns dodger.bonus.life
  (:require [quil.core :as quil]
            [dodger.player :as player]))

(def bonus-life (atom {:x 10 :y 20}))

(defn draw-bonus-life
  "Drawing a bonus life object"
  []
  (quil/fill (quil/color 245 245 245))
  (quil/rect 50 50 40 40))
