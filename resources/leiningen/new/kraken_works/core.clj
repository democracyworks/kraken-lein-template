(ns {{name}}.core
  (:require [{{name}}.channels :as channels]
            [{{name}}.queue :as queue]
            [turbovote.resource-config :refer [config]]
            [datomic-toolbox.core :as db]
            [clojure.tools.logging :as log])
  (:gen-class))

(defn -main [& args]
  (cond (config [:datomic :initialize]) (db/initialize (config [:datomic]))
        (config [:datomic :run-migrations]) (do (db/configure! (config [:datomic]))
                                                (db/run-migrations)))
  (let [rabbit-resources (queue/initialize (config [:rabbitmq]))]
    (.addShutdownHook (Runtime/getRuntime)
     (Thread. (fn []
                (queue/close-all! rabbit-resources)
                (channels/close-all!))))))
