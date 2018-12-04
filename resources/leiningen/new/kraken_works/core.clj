(ns {{name}}.core
  (:require {{#datomic?}}[{{name}}.entities]{{/datomic?}}
            [{{name}}.handlers]
            [{{name}}.queue]
            [mount.core :as mount])
  (:gen-class))

(defn -main [& args]
  (mount/start))
