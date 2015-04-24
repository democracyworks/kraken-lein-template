(ns {{name}}.core_test
    (:require [{{name}}.core :refer :all]
              [clojure.test :refer :all]))

(deftest handler-test
  (is (= :ok (:status (handler {})))))
