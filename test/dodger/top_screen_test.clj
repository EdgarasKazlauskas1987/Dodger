(ns dodger.top-screen-test
  (:require [clojure.test :refer :all]
            [dodger.enemies.top-screen :refer :all]))

(deftest outside?-test-true
  (testing "If top screen enemy is outside of screen limits"
    (is (outside? (atom {:y 651})))))

(deftest outside?-test-false
  (testing "If top screen enemy is outside of screen limits"
    (is (false? (outside? (atom {:y 650}))))))
