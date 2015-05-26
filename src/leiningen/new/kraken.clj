(ns leiningen.new.kraken
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "kraken"))

(defn kraken
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info (str "Generating fresh kraken project in directory " (:sanitized data) "/."))

    (main/info (str "TODO: (you probably want to `cd " name "` first)"))
    (main/info " * in resources/config.edn, change queue name to something more appropriate")
    (main/info " * Review and address the TODO items in the README.")
    (main/info " * `chmod +x script/*`")
    (main/info " * `git init`")
    (main/info " * `git add .`")
    (main/info " * `git commit -am \"Initial commit.\"`")
    (main/info " * push this to github")
    (main/info " * make a Buildkite project")
    (->files data
             [".gitignore" (render ".gitignore" data)]
             ["Dockerfile" (render "Dockerfile" data)]
             ["{{name}}@.service.template" (render "PROJECT@.service.template" data)]
             ["docker-compose.yml" (render "docker-compose.yml" data)]
             ["profiles.clj.sample" (render "profiles.clj.sample" data)]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["LICENSE" (render "LICENSE" data)]

             ["script/deploy" (render "script/deploy" data)]
             ["script/build" (render "script/build" data)]

             ["resources/config.edn" (render "config.edn" data)]

             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["src/{{sanitized}}/queue.clj" (render "queue.clj" data)]
             ["src/{{sanitized}}/channels.clj" (render "channels.clj" data)]
             ["src/{{sanitized}}/handlers.clj" (render "handlers.clj" data)]
             ["test/{{sanitized}}/handlers_test.clj" (render "handlers_test.clj" data)]
             )))
