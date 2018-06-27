(defproject {{name}} "0.1.0-SNAPSHOT"
  :description ""
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.logging "0.4.1"]
                 [turbovote.resource-config "0.2.1"]
                 [democracyworks/datomic-toolbox "2.0.5"
                  :exclusions [com.datomic/datomic-pro]]
                 [com.datomic/datomic-pro "0.9.5703"
                  :exclusions [org.slf4j/slf4j-nop
                               org.slf4j/slf4j-log4j12]]
                 [com.amazonaws/aws-java-sdk-dynamodb "1.11.355"
                  :exclusions [commons-codec commons-logging]]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [democracyworks/kehaar "0.11.4"]]
  :plugins [[com.pupeno/jar-copier "0.4.0"]]
  :java-agents [[com.newrelic.agent.java/newrelic-agent "4.2.0"]]
  :jar-copier {:java-agents true
               :destination "resources/jars"}
  :prep-tasks ["javac" "compile" "jar-copier"]

  :main ^:skip-aot {{name}}.core
  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :username [:gpg :env/datomic_username]
                                   :password [:gpg :env/datomic_password]}}
  :uberjar-name "{{name}}.jar"
  :profiles {:uberjar {:aot :all}
             :dev-common {:resource-paths ["dev-resources"]}
             :dev-overrides {}
             :dev [:dev-common :dev-overrides]
             :test {:jvm-opts ["-Dlog-level=OFF"]}})
