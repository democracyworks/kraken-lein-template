(defproject {{name}} "0.1.0-SNAPSHOT"
  :description ""
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [turbovote.resource-config "0.2.1"]
                 [com.novemberain/langohr "3.7.0"]
                 [democracyworks/datomic-toolbox "2.0.4"
                  :exclusions [com.datomic/datomic-pro]]
                 [prismatic/schema "1.1.6"]
                 [com.datomic/datomic-pro "0.9.5561"
                  :exclusions [org.slf4j/slf4j-nop
                               org.slf4j/slf4j-log4j12]]
                 [com.amazonaws/aws-java-sdk-dynamodb "1.11.128"
                  :exclusions [commons-codec commons-logging]]
                 [ch.qos.logback/logback-classic "1.2.2"]
                 [org.immutant/core "2.1.6"]
                 [democracyworks/kehaar "0.10.4"]]
  :plugins [[lein-immutant "2.1.0"]
            [com.carouselapps/jar-copier "0.4.0"]]
  :java-agents [[com.newrelic.agent.java/newrelic-agent "3.35.1"]]
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
