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
  (let [connection (atom nil)
        max-retries 5]
    (loop [attempt 1]
      (try
        (reset! connection
                (rmq/connect (or (config :rabbitmq :connection)
                                 {})))
        (log/info "RabbitMQ connected.")
        (catch Throwable t
          (log/warn "RabbitMQ not available:" (.getMessage t) "attempt:" attempt)))
      (when (nil? @connection)
        (if (< attempt max-retries)
          (do (Thread/sleep (* attempt 1000))
              (recur (inc attempt)))
          (do (log/error "Connecting to RabbitMQ failed. Bailing.")
              (throw (ex-info "Connecting to RabbitMQ failed" {:attempts attempt}))))))
    (let [ins []
          in-outs [(wire-up/incoming-service-handler @connection
                                                     "{{name}}.ok"
                                                     (config :rabbitmq :queues "{{name}}.ok")
                                                     handlers/ok)]
          out-ins []
          outs []]
      {:connections #{@connection}
       :channels (concat
                  ins
                  in-outs
                  out-ints
                  outs)})))

(defn close-resources! [resources]
  (doseq [resource resources]
    (when-not (rmq/closed? resource) (rmq/close resource))))

(defn close-all! [{:keys [connections channels]}]
  (close-resources! channels)
  (close-resources! connections))
