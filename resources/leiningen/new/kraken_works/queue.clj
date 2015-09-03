(ns {{name}}.queue
  (:require [clojure.tools.logging :as log]
            [langohr.core :as rmq]
            [langohr.channel :as lch]
            [langohr.exchange :as le]
            [langohr.queue :as lq]
            [kehaar.rabbitmq]
            [kehaar.wire-up :as wire-up]
            [{{name}}.channels :as channels]
            [{{name}}.handlers :as handlers]
            [turbovote.resource-config :refer [config]]))

(defn initialize []
  (let [max-retries 5
        rabbit-config (config [:rabbitmq :connection] {})
        connection (kehaar.rabbitmq/connect-with-retries rabbit-config max-retries)]
    (let [incoming-events []
          incoming-services [(wire-up/incoming-service
                              connection
                              "{{name}}.ok"
                              (config [:rabbitmq :queues "{{name}}.ok"])
                              channels/ok-requests
                              channels/ok-responses)]
          external-services []
          outgoing-events []]

      (wire-up/start-responder! channels/ok-requests
                                channels/ok-responses
                                handlers/ok)

      {:connections [connection]
       :channels (vec (concat
                       incoming-events
                       incoming-services
                       external-services
                       outgoing-events))})))

(defn close-resources! [resources]
  (doseq [resource resources]
    (when-not (rmq/closed? resource) (rmq/close resource))))

(defn close-all! [{:keys [connections channels]}]
  (close-resources! channels)
  (close-resources! connections))
