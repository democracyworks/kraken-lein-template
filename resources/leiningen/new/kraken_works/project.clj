(defproject {{name}} "0.1.0-SNAPSHOT"
  :description ""
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/tools.logging "1.2.4"]
                 [org.clojure/core.async "1.5.648"]
                 [aero "1.1.6"]
{{#datomic?}}
                 [democracyworks/datomic-toolbox "2.0.5"
                  :exclusions [com.datomic/datomic-pro]]
                 [com.datomic/datomic-pro "1.0.6222"]
                 [com.amazonaws/aws-java-sdk-dynamodb "1.11.913"]
                 [com.amazonaws/aws-java-sdk-sts "1.11.913"]
{{/datomic?}}
                 [democracyworks/kehaar "1.0.3"]
                 [mount "0.1.16"]

                 ;;; Logging: see https://github.com/stuartsierra/log.dev

                 [ch.qos.logback/logback-core "1.2.10"]
                 [ch.qos.logback/logback-classic "1.2.10"]
                 [org.apache.logging.log4j/log4j-to-slf4j "2.17.1"]
                 [org.slf4j/slf4j-api "1.7.33"]
                 [org.slf4j/jcl-over-slf4j "1.7.33"]
                 [org.slf4j/log4j-over-slf4j "1.7.33"]
                 [org.slf4j/osgi-over-slf4j "1.7.33"]]
  :exclusions [;;; Logging: see https://github.com/stuartsierra/log.dev

               commons-logging
               log4j
               org.apache.logging.log4j/log4j
               org.clojure/clojurescript
               org.slf4j/simple
               org.slf4j/slf4j-jcl
               org.slf4j/slf4j-nop
               org.slf4j/slf4j-log4j12
               org.slf4j/slf4j-log4j13]
  :main ^:skip-aot {{name}}.core
{{#datomic?}}
  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :username [:gpg :env/datomic_username]
                                   :password [:gpg :env/datomic_password]}}
{{/datomic?}}
  :uberjar-name "{{name}}.jar"
  :profiles {:uberjar {:aot :all}
             :dev-common {:resource-paths ["dev-resources"]}
             :dev-overrides {}
             :dev [:dev-common :dev-overrides]
             :test {:jvm-opts ["-Dlog-level=OFF"]}})
