(ns {{name}}.channels
  (:require [clojure.core.async :as async]))

;;; This namespace is for core.async channels used by kehaar. Channels
;;; only need to be created for ougoing events and external
;;; services. They should be unbuffered. If no channels are needed, this
;;; namespace can be removed.
