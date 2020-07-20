(ns dodger.player-test
  (:require [clojure.test :refer :all]
            [dodger.player :refer :all]))

(deftest within-max-x-border?-test-true
  (testing "If players x coordinate is withing the border"
    (is (within-max-x-border? 850))))

(deftest within-max-x-border?-test-false
  (testing "If players x coordinate is withing the border"
    (is (false? (within-max-x-border? 851)))))

(deftest within-min-x-border?-test-true
  (testing "If players x coordinate is withing the border"
    (is (within-min-x-border? 10))))

(deftest within-min-x-border?-test-false
  (testing "If players x coordinate is withing the border"
    (is (false? (within-min-x-border? 9)))))

(deftest within-max-y-border?-test-true
  (testing "If players y coordinate is withing the border"
    (is (within-max-y-border? 600))))

(deftest within-max-y-border?-test-false
  (testing "If players y coordinate is withing the border"
    (is (false? (within-max-y-border? 601)))))

(deftest within-min-y-border?-test-true
  (testing "If players y coordinate is withing the border"
    (is (within-min-x-border? 10))))

(deftest within-min-y-border?-test-false
  (testing "If players y coordinate is withing the border"
    (is (false? (within-min-y-border? 9)))))