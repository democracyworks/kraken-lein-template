(ns {{name}}.core
    (:require [{{name}}.channels :as channels]
              [{{name}}.queue :as queue]
              [turbovote.resource-config :refer [config]]
              [clojure.tools.logging :as log]
              [immutant.util :as immutant]))

(defn -main [& args]
  (let [rabbit-resources (queue/initialize)]
    (immutant/at-exit (fn []
                        (queue/close-all! rabbit-resources)
                        (channels/close-all!)))))
