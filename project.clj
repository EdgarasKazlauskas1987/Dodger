(defproject dodger "0.1.0-SNAPSHOT"
  :description "Desktop game where a player must avoid a bunch of enemies that fall from all sides of
  the screen. The longer the player can keep avoiding the bodies of enemies, the higher the score will get.
  The player is controlled with the arrow keys ↑ ↓ → ←."
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [quil "3.1.0"]]
  :repl-options {:init-ns dodger.core}
  :plugins [[lein-kibit "0.1.8"]
            [jonase/eastwood "0.3.11"]])
