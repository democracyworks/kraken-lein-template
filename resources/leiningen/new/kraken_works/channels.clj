(ns {{name}}.channels
  (:require [clojure.core.async :as async]))

(defn close-all! []
  (doseq [c []]
    (async/close! c)))
