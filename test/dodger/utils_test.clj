(ns dodger.utils-test
  (:require [clojure.test :refer :all]
            [dodger.utils :refer :all]))

(deftest generate-speed-test
  (testing "If generated enemy speed is one of three options: 1, 2 or 3"
    (is (not (nil? (some #{(generate-speed)} '(1 2 3)))))))

(deftest generate-x-coordinate-test
  (testing "If generated x coordinate is between 0 and 880"
    (let [x-coordinate (generate-x-coordinate)]
      (is (and (>= x-coordinate 0) (<= x-coordinate 880))))))

(deftest generate-y-coordinate-test
  (testing "If generated y coordinate is between 0 and 630"
    (let [y-coordinate (generate-y-coordinate)]
      (is (and (>= y-coordinate 0) (<= y-coordinate 630))))))

(deftest generate-size-test
  (testing "If generated enemy size is one of four options: 35, 40, 50 or 60"
    (is (not (nil? (some #{(generate-size)} '(35 40 50 60)))))))