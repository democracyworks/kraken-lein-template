(ns {{name}}.queue
  (:require [clojure.core.async :as async]
            [clojure.tools.logging :as log]
            [langohr.core :as rmq]
            [langohr.channel :as lch]
            [langohr.exchange :as le]
            [langohr.queue :as lq]
            [kehaar :as k]
            [{{name}}.channels :as channels]
            [{{name}}.handlers :as handlers]
            [turbovote.resource-config :refer [config]]))

(defn initialize []
  (let [connection (atom nil)
        max-retries 5]
    (loop [attempt 1]
      (try
        (reset! connection
                (rmq/connect (or (config :rabbit-mq :connection)
                                 {})))
        (log/info "RabbitMQ connected.")
        (catch Throwable t
          (log/warn "RabbitMQ not available:" (.getMessage t) "attempt:" attempt)))
      (when (nil? @connection)
        (if (< attempt max-retries)
          (do (Thread/sleep (* attempt 1000))
              (recur (inc attempt)))
          (do (log/error "Connecting to RabbitMQ failed. Bailing.")
              (throw (ex-info "Connecting to RabbitMQ failed" {:attemts attempt}))))))
    (let [events-exchange "events"
          events-ch (lch/open @connection)
          heartbeat-ch (lch/open @connection)
          ping-ch (lch/open @connection)
          heartbeat (async/chan)]
      (async/go-loop []
        (async/<!! (async/timeout 1000))
        (async/put! heartbeat :beep)
        (recur))
      (le/declare events-ch events-exchange "topic" (config :rabbitmq :topics events-exchange))
      (lq/declare ping-ch
                  "{{name}}.ping"
                  (config :rabbitmq :queues "{{name}}.ping"))
      (k/async->rabbit heartbeat heartbeat-ch events-exchange "{{name}}.heartbeat")
      (k/responder ping-ch "{{name}}.ping" handlers/ping)
      {:connections #{@connection}
       :channels #{events-ch heartbeat-ch ping-ch}
       :async-channels #{heartbeat-ch}})))

(defn close-resources! [resources]
  (doseq [resource resources]
    (when-not (rmq/closed? resource) (rmq/close resource))))

(defn close-all! [{:keys [connections channels async-channels]}]
  (close-resources! channels)
  (close-resources! connections)
  (doseq [c async-channels] (async/close! c)))
