(ns {{name}}.core
    (:require [{{name}}.channels :as channels]
              [{{name}}.queue :as queue]
              [turbovote.resource-config :refer [config]]
              [datomic-toolbox :as db]
              [clojure.tools.logging :as log]
              [immutant.util :as immutant]))

(defn -main [& args]
  (when (config :datomic :intitialize)
    (db/initialize))
  (when (config :datomic :run-migrations)
    (db/run-migrations))
  (let [rabbit-resources (queue/initialize)]
    (immutant/at-exit (fn []
                        (queue/close-all! rabbit-resources)
                        (channels/close-all!)))))
