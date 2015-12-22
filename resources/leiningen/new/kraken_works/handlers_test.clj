(ns {{name}}.handlers-test
  (:require [{{name}}.handlers :refer :all]
            [clojure.test :refer :all]))

(deftest ok-test
  (is (= :ok (:status (ok {})))))
