(ns {{name}}.queue
  (:require [langohr.core :as rmq]
            [kehaar.rabbitmq]
            [kehaar.configured :as kehaar]))

(defn initialize [{:keys [connection kehaar]}]
  (let [max-retries 5
        rmq-conn (kehaar.rabbitmq/connect-with-retries connection max-retries)
        kehaar-resources (kehaar/init! rmq-conn kehaar)]
    {:connections [connection]
     :kehaar-resources kehaar-resources}))

(defn close-resources! [resources]
  (doseq [resource resources]
    (when-not (rmq/closed? resource) (rmq/close resource))))

(defn close-all! [{:keys [connections kehaar-resources]}]
  (kehaar/shutdown! kehaar-resources)
  (close-resources! connections))
