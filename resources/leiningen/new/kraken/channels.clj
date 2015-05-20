(ns {{name}}.channels
  (:require [clojure.core.async :as async]
            [kehaar :as k]))

;;; TODO: Create channels and add them to the list of channels to close.
(defn close-all! []
  (doseq [c []]
    (async/close! c)))
