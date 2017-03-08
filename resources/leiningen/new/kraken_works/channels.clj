(ns {{name}}.channels
  (:require [clojure.core.async :as async]))

;;; This namespace is for core.async channels used by kehaar. Channels
;;; only need to be created for ougoing events and external
;;; services. If neither are required, this namespace can be
;;; removed. Add any created channels to the vector of channels
;;; defined in `close-all!`.

(defn close-all! []
  (let [channels []]
    (doseq [c channels]
      (async/close! c))))
