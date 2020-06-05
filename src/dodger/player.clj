(ns dodger.player
  (:require [quil.core :as quil]))

;;Checking if player object is withing the screen borders
(defn within-max-x-border?
  [player-x]
  (if (<= player-x 850) true false))

(defn within-min-x-border?
  [player-x]
  (if (>= player-x 10) true false))

(defn within-max-y-border?
  [player-y]
  (if (<= player-y 600) true false))

(defn within-min-y-border?
  [player-y]
  (if (>= player-y 10) true false))

(def player-coordinates (atom {:x 410 :y 230}))

(defn move-player-up []
  (when (within-min-y-border? (get @player-coordinates :y))
    (swap! player-coordinates update-in [:y] - 10)))

(defn move-player-down []
  (when (within-max-y-border? (get @player-coordinates :y))
    (swap! player-coordinates update-in [:y] + 10)))

(defn move-player-left []
  (when (within-min-x-border? (get @player-coordinates :x))
    (swap! player-coordinates update-in [:x] - 10)))

(defn move-player-right []
  (when (within-max-x-border? (get @player-coordinates :x))
    (swap! player-coordinates update-in [:x] + 10)))

(defn draw-player
  "Drawing player"
  [x y]
  (let [ghost (quil/state :ghost)]
    (when (quil/loaded? ghost) (quil/image ghost x y))))

(def player-lives (atom 5))

(defn inc-player-lives
  "Incrementing amount of player lives left"
  []
  (swap! player-lives inc))

(defn dec-player-lives
  "Decrementing amount of player lives left"
  []
  (swap! player-lives dec))

(defn player-movement
  "Checking which key is pressed and calling corresponding function"
  [key]
  (cond
    (= key :up) (move-player-up)
    (= key :down) (move-player-down)
    (= key :left) (move-player-left)
    (= key :right) (move-player-right)))

(defn key-pressed
  "Is activated when a key is pressed"
  []
  (when (quil/key-pressed?)
    (player-movement (quil/key-as-keyword))))