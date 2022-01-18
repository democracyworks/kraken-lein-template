(ns {{name}}.handlers-test
  (:require [{{name}}.handlers :as handlers]
            [clojure.test :refer [deftest is]]))

(deftest ok-test
  (is (= :ok (:status (handlers/ok {})))))
