(ns dodger.bottom-screen-test
  (:require [clojure.test :refer :all]
            [dodger.enemies.bottom-screen :refer :all]))

(deftest outside?-test-true
  (testing "If bottom screen enemy is outside of screen limits"
    (is (outside? (atom {:y -1})))))

(deftest outside?-test-false
  (testing "If bottom screen enemy is outside of screen limits"
    (is (false? (outside? (atom {:y 0}))))))
