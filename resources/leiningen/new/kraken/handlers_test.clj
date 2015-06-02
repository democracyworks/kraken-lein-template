(ns {{name}}.handlers-test
    (:require [{{name}}.handlers :refer :all]
              [clojure.test :refer :all]))

(deftest ping-test
  (is (= :ok (:status (ping {})))))
