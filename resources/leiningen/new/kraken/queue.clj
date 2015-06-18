(ns {{name}}.queue
  (:require [clojure.tools.logging :as log]
            [langohr.core :as rmq]
            [langohr.channel :as lch]
            [langohr.exchange :as le]
            [langohr.queue :as lq]
            [kehaar.core :as k]
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
    (let [ok-ch (lch/open @connection)]
      (lq/declare ok-ch
                  "{{name}}.ok"
                  (config :rabbitmq :queues "{{name}}.ok"))
      (k/responder ok-ch "{{name}}.ok" handlers/ok)
      {:connections #{@connection}
       :channels #{ok-ch}})))

(defn close-resources! [resources]
  (doseq [resource resources]
    (when-not (rmq/closed? resource) (rmq/close resource))))

(defn close-all! [{:keys [connections channels]}]
  (close-resources! channels)
  (close-resources! connections))
