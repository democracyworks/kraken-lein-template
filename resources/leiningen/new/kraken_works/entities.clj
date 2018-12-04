(ns {{name}}.entities
  (:require [datomic.api :as d]
            [datomic-toolbox.core :as db]
            [mount.core :refer [defstate]]
            [resource-config.core :as rc]))

(defstate datomic-config
  :start (rc/config [:datomic])
  :stop  (rc/reload-config!))

(defstate datomic-conn
  ;; TODO: Stop using datomic-toolbox (for state or at all). Probably when
  ;; we move to Datomic client.
  :start (do (db/initialize datomic-config)
             (db/connection))
  :stop  (d/release datomic-conn))