(ns dodger.right-screen-test
  (:require [clojure.test :refer :all]
            [dodger.enemies.right-screen :refer :all]))

(deftest outside?-test-true
  (testing "If right screen enemy is outside of screen limits"
    (is (outside? (atom {:x -1})))))

(deftest outside?-test-false
  (testing "If right screen enemy is outside of screen limits"
    (is (false? (outside? (atom {:x 0}))))))