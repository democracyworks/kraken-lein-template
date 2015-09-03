(ns {{name}}.handlers
  (:require [clojure.tools.logging :as log]))

(defn ok
  "A handler that does nothing and responds ok."
  [message]
  (log/debug "Received:" message)
  {:status :ok})
