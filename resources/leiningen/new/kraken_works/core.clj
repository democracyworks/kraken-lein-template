(ns {{name}}.core
  (:require [{{name}}.channels :as channels]
            [{{name}}.queue :as queue]
            [resource-config.core :refer [config]]
            {{#datomic?}}
            [datomic-toolbox.core :as db]
            {{/datomic?}}
            [clojure.tools.logging :as log])
  (:gen-class))

(defn -main [& args]
  {{#datomic?}}
  (cond (config [:datomic :initialize]) (db/initialize (config [:datomic]))
        (config [:datomic :run-migrations]) (do (db/configure! (config [:datomic]))
                                                (db/run-migrations)))
  {{/datomic?}}
  (let [rabbit-resources (queue/initialize (config [:rabbitmq]))]
    (.addShutdownHook (Runtime/getRuntime)
     (Thread. (fn []
                (queue/close-all! rabbit-resources)
                (channels/close-all!))))))
