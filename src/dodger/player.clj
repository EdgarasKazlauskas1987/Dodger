(ns dodger.player
  (:require [quil.core :as quil]
            [dodger.settings :as settings]))

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

(def active-keys (atom #{}))

(defn move-player-up []
  (when (within-min-y-border? (get @player-coordinates :y))
    (swap! player-coordinates update-in [:y] - 2)))

(defn move-player-down []
  (when (within-max-y-border? (get @player-coordinates :y))
    (swap! player-coordinates update-in [:y] + 2)))

(defn move-player-left []
  (when (within-min-x-border? (get @player-coordinates :x))
    (swap! player-coordinates update-in [:x] - 2)))

(defn move-player-right []
  (when (within-max-x-border? (get @player-coordinates :x))
    (swap! player-coordinates update-in [:x] + 2)))

(defn draw-player
  "Drawing player" [x y]
  (let [ghost (quil/state :ghost)]
    (when (quil/loaded? ghost) (quil/image ghost x y))))

(def player-lives (atom settings/starting-lives))

(defn inc-player-lives
  "Incrementing amount of player lives left" []
  (swap! player-lives inc))

(defn dec-player-lives
  "Decrementing amount of player lives left" []
  (swap! player-lives dec))

(defn key-pressed
  "Activated when a key is pressed" []
  (swap! active-keys conj (quil/key-as-keyword)))

(defn key-released
  "Activated when a key is released" []
  (swap! active-keys disj (quil/key-as-keyword)))

(defn player-movement
  "Checking which keys are active and calling corresponding function" []
  (when (contains? @active-keys :up) (move-player-up))
  (when (contains? @active-keys :down) (move-player-down))
  (when (contains? @active-keys :left) (move-player-left))
  (when (contains? @active-keys :right) (move-player-right)))