(ns {{name}}.handlers
    (:require [clojure.tools.logging :as log]
              [kehaar.power :as power]))

(power/def-service-handler ok "{{name}}.ok"
  "A handler that does nothing and responds ok."
  [message]
  (log/debug "Received:" message)
  {:status :ok})
