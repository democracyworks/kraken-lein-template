(ns {{name}}.handlers
  (:require [clojure.tools.logging :as log]))

(defn ping
  "A handler that does nothing and responds ok."
  [message]
  (log/info "Received:" message)
  {:status :ok})
