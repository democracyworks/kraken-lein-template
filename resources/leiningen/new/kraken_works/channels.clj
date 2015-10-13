(ns {{name}}.channels
    (:require [clojure.core.async :as async]))

(defonce ok-requests  (async/chan))
(defonce ok-responses (async/chan 1000))

;;; TODO: Create channels and add them to the list of channels to close.
(defn close-all! []
  (doseq [c [ok-requests ok-responses]]
    (async/close! c)))
