(ns {{name}}.handlers
  (:require [clojure.tools.logging :as log]
            [mount.core :refer [defstate]]
            [resource-config.core :as rc]))

(defstate config
  :start (rc/config [:{{name}}] {})
  :stop  (rc/reload-config!))

(defn ok
  "A handler that does nothing and responds ok."
  [message]
  (log/debug "Received:" message)
  {:status :ok})
