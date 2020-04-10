(ns dodger.enemies.top-screen
  (:require [quil.core :as quil]))

(defstruct top-enemy :x :y :Speed)

(def fast-enemy (atom (struct-map top-enemy :x 20 :y 0 :Speed 2)))
(def slow-enemy (atom (struct-map top-enemy :x 50 :y 0 :Speed 1)))

(def top-screen-enemies (seq [fast-enemy slow-enemy]))

(defn outside?
  "Checks if top screen enemy is outside of screen limits"
  [enemy]
  (if (> (get @enemy :y) 650)
    true
    false))

(defn top-enemies-update
  "Updates positions of all top screen enemies"
  []
  (doseq [enemy top-screen-enemies]
    (if (outside? enemy)
      (swap! enemy update-in [:y] * 0)
      (swap! enemy update-in [:y] + (get @enemy :Speed)))))

(defn draw-top-enemy
  "Draw an enemy"
  [x y]
  (quil/rect x y 20 20))

(defn top-enemies-draw
  "Drawing all top screen enemies"
  []
  (doseq [enemy top-screen-enemies]
    (draw-top-enemy (get @enemy :x) (get @enemy :y))))

