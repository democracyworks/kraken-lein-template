(defproject {{name}} "0.1.0-SNAPSHOT"
  :description ""
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [turbovote.resource-config "0.2.0"]
                 [com.novemberain/langohr "3.6.1"]
                 [democracyworks/datomic-toolbox "2.0.2"
                  :exclusions [com.datomic/datomic-pro]]
                 [prismatic/schema "1.1.3"]
                 [com.datomic/datomic-pro "0.9.5394"
                  :exclusions [org.slf4j/slf4j-nop
                               org.slf4j/slf4j-log4j12]]
                 [com.amazonaws/aws-java-sdk-dynamodb "1.11.6"
                  :exclusions [commons-codec commons-logging]]
                 [ch.qos.logback/logback-classic "1.1.7"]
                 [org.immutant/core "2.1.5"]
                 [democracyworks/kehaar "0.8.1"]]
  :plugins [[lein-immutant "2.1.0"]]
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
