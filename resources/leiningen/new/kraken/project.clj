(defproject {{name}} "0.1.0-SNAPSHOT"
  :description ""
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha5"]
                 [org.clojure/tools.logging "0.3.1"]
                 [turbovote.resource-config "0.1.4"]
                 [com.novemberain/langohr "3.2.0"]
                 [democracyworks/datomic-toolbox "1.0.0" :exclusions [com.datomic/datomic-pro]]
                 [com.datomic/datomic-pro "0.9.5153" :exclusions [joda-time]]
                 [org.immutant/core "2.0.0"]
                 [democracyworks/kehaar "0.2.1"]]
  :plugins [[lein-immutant "2.0.0"]]
  :main ^:skip-aot {{name}}.core
  :target-path "target/%s"
  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :username [:gpg :env]
                                   :password [:gpg :env]}}
  :uberjar-name "{{name}}.jar"
  :profiles {:uberjar {:aot :all}
             :dev {:resource-paths ["dev-resources"]}})
