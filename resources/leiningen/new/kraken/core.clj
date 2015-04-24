(ns {{name}}.core
    (:require [kehaar :as k]
              [langohr.consumers :as lc]
              [{{name}}.queue :as queue]
              [turbovote.resource-config :refer [config]]
              [clojure.tools.logging :as log]))

(defn handler
  "A handler that does nothing and responds ok."
  [message]
  {:status :ok})

(defn -main [& args]
  (queue/initialize)
  (lc/subscribe @queue/channel
                (config :rabbit-mq :queue)
                (k/simple-responder handler)
                {:auto-ack true}))
