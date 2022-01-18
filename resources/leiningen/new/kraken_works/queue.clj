(ns {{name}}.queue
  (:require [{{name}}.config :refer [config]]
            [kehaar.rabbitmq]
            [kehaar.configured :as kehaar]
            [langohr.core :as rmq]
            [mount.core :refer [defstate]]))

(defn initialize [{connection-config :connection kehaar-config :kehaar}]
  (let [max-retries 5
        rabbit-cfg (kehaar.rabbitmq/dissoc-blank-config-params-with-defaults
                    connection-config)
        rmq-conn (kehaar.rabbitmq/connect-with-retries rabbit-cfg max-retries)
        kehaar-resources (kehaar/init! rmq-conn kehaar-config)]
    {:connections [rmq-conn]
     :kehaar-resources kehaar-resources}))

(defn close-resources! [resources]
  (doseq [resource resources]
    (when-not (rmq/closed? resource) (rmq/close resource))))

(defn close-all! [{:keys [connections kehaar-resources]}]
  (kehaar/shutdown! kehaar-resources)
  (close-resources! connections))

(defstate rabbit-resources
  :start (let [rabbitmq-config (get config :rabbitmq)]
           (initialize rabbitmq-config))
  :stop (close-all! rabbit-resources))
