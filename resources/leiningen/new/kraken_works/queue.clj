(ns {{name}}.queue
  (:require [clojure.tools.logging :as log]
            [langohr.core :as rmq]
            [langohr.channel :as lch]
            [langohr.exchange :as le]
            [langohr.queue :as lq]
            [kehaar.core :as k]
            [kehaar.wire-up :as wire-up]
            [{{name}}.channels :as channels]
            [{{name}}.handlers :as handlers]
            [turbovote.resource-config :refer [config]]))

(defn initialize []
  (let [max-connection-attempts 5
        connection (kehaar-rabbit/connect-with-retries
                     (config [:rabbitmq :connection] {})
                     max-connection-attempts)]
    (wire-up/start-responder!
      channels/ok-requests
      channels/ok-responses
      handlers/ok)
    {:connections [connection]
     :channels [(wire-up/incoming-service-handler connection
                  "{{name}}.ok"
                  (config :rabbitmq :queues "{{name}}.ok")
                  handlers/ok)]}))

(defn close-resources! [resources]
  (doseq [resource resources]
    (when-not (rmq/closed? resource) (rmq/close resource))))

(defn close-all! [{:keys [connections channels]}]
  (close-resources! channels)
  (close-resources! connections))
