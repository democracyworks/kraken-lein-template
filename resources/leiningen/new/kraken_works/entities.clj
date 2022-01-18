(ns {{name}}.entities
  (:require [{{name}}.config :refer [config]]
            [datomic.api :as d]
            [datomic-toolbox.core :as dt]
            [mount.core :refer [defstate]]))

(defstate datomic-conn
  ;; TODO: Stop using datomic-toolbox (for state or at all). Probably when
  ;; we move to Datomic client.
  :start (let [datomic-config (get config :datomic)]
           (dt/initialize datomic-config)
           (dt/connection))
  :stop (d/release datomic-conn))
