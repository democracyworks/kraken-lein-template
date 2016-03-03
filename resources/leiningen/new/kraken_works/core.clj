(ns {{name}}.core
  (:require [turbovote.resource-config :refer [config]]
            [datomic-toolbox.core :as db]
            [clojure.tools.logging :as log]
            [immutant.util :as immutant]
            [kehaar.power :as power]
            [{{name}}.handlers]))

(defn -main [& args]
  (db/initialize (config [:datomic]))
  (power/connect-rabbit 5 (config [:rabbitmq :connection]))
  (immutant/at-exit power/disconnect-rabbit))
