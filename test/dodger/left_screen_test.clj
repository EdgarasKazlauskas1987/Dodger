(ns dodger.left-screen-test
  (:require [clojure.test :refer :all]
            [dodger.enemies.left-screen :refer :all]))

(deftest outside?-test-true
  (testing "If left screen enemy is outside of screen limits"
    (is (outside? (atom {:x 901})))))

(deftest outside?-test-false
  (testing "If left screen enemy is outside of screen limits"
    (is (false? (outside? (atom {:x 900}))))))
