(ns {{name}}.config
  (:require [aero.core :as aero]
            [clojure.java.io :as io]
            [mount.core :refer [defstate]]))

(defn read-config
  "Reads project configuration."
  [profile]
  (aero/read-config (io/resource "config.edn") {:profile profile}))

;; Use `mount.core/start-with` to start with a different aero profile.
(defstate config
  :start (read-config :default))
