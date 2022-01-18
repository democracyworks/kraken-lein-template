(ns leiningen.new.kraken-works
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.string :as str]))

(def render (renderer "kraken-works"))

(def default-options {:datomic? true})

(def valid-options {"--datomic" {:datomic? true}
                    "--no-datomic" {:datomic? false}})

(defn parse-opt [opt]
  (if-let [opt-map (valid-options opt)]
    opt-map
    (main/abort "Invalid option:" opt)))

(defn make-data [name opts]
  (apply merge
    {:name name
     :sanitized (name-to-path name)
     :env-var-style (-> name name-to-path str/upper-case)}
    default-options
    (map parse-opt opts)))

(defn kraken-works
  "FIXME: write documentation"
  [name & opts]
  (let [data (make-data name opts)]
    (main/info (str "Generating fresh kraken-works project in directory " (:sanitized data) "/."))

    (main/info (str "TODO: (you probably want to `cd " name "` first)"))
    (main/info " * Review and address the TODO items in the README.")
    (main/info " * Add any other needed kubernetes resources to the `deploy/` dirs (see existing similar components).")
    (main/info " * `git init`")
    (main/info " * `git add .`")
    (main/info " * `git commit -am \"initial commit\"`")
    (main/info " * push this to github")
    (main/info " * make a Buildkite project")
    (->> [[".buildkite/pipeline.yml" (render "buildkite/pipeline.yml" data)]
          [".gitignore" (render "gitignore" data)]
          ["Dockerfile" (render "Dockerfile" data)]
          ["docker/app_container/usr/local/bin/entrypoint.sh"
           (render "entrypoint.sh" data) :executable true]
          ["docker-compose.yml" (render "docker-compose.yml" data)]
          ["deploy/build.config" (render "deploy/build.config" data)]
          (when (:datomic? data)
            ["profiles.clj.sample" (render "profiles.clj.sample" data)])
          ["project.clj" (render "project.clj" data)]
          ["README.md" (render "README.md" data)]

          ["script/env.sh" (render "script/env.sh" data)]
          ["script/build" (render "script/build" data) :executable true]
          ["script/deploy" (render "script/deploy" data) :executable true]
          ["script/deploy_wrap.sh" (render "script/deploy_wrap.sh" data)
           :executable true]

          ["resources/config.edn" (render "config.edn" data)]
          (when (:datomic? data)
            ["resources/schemas/.gitkeep" ""])
          ["resources/logback.xml" (render "logback.xml" data)]

          ["src/{{sanitized}}/config.clj" (render "config.clj" data)]
          ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
          ["src/{{sanitized}}/queue.clj" (render "queue.clj" data)]
          (when (:datomic? data)
            ["src/{{sanitized}}/entities.clj" (render "entities.clj" data)])
          ["src/{{sanitized}}/channels.clj" (render "channels.clj" data)]
          ["src/{{sanitized}}/handlers.clj" (render "handlers.clj" data)]
          ["test/{{sanitized}}/handlers_test.clj" (render "handlers_test.clj" data)]]
         (remove nil?)
         (apply ->files data))))
