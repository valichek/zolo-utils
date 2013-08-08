(defproject zololabs/zolo-utils "0.1.0-SNAPSHOT"
  :description "Some Clojure utilities that Zolo Labs thinks are useful..."

  :dependencies [[org.clojure/clojure "1.5.1"]

                 [ring "1.0.2"]

                 [org.clojure/data.json "0.1.2"]
                 [clj-http "0.3.6"]
                 [clj-oauth2 "0.2.0"]

                 [joda-time "1.6"]
                 [clj-time "0.4.4"]
                 [com.joestelmach/natty "0.6-SNAPSHOT"]
                 
                 [slingshot "0.10.2"]

                 [org.clojure/tools.logging "0.2.4"]
                 [org.slf4j/slf4j-api "1.7.0"]
                 [clj-logging-config "1.9.10" :exclusions [log4j]]

                 [metrics-clojure "1.0.1"]
                 ]

  :exclusions [org.clojure/clojure
               org.slf4j/slf4j-log4j12
               org.slf4j/slf4j-api
               org.slf4j/slf4j-nop
               log4j/log4j
               commons-logging/commons-logging
               org.clojure/tools.logging]

  :plugins [[lein-swank "1.4.4"]
            [lein-pprint "1.1.1"]
            [lein-notes "0.0.1"]
            [lein-clojars "0.9.1"]]

  :dev-dependencies [[clj-stacktrace "0.2.4"]
                     [swank-clojure "1.3.3"]]
  
  :min-lein-version "2.0.0"

  :test-selectors {:default (fn [t] (not (:integration t)))
                   :integration :integration
                   :all (fn [t] true)}
  
  :project-init (do (use 'clojure.pprint)
                    (use 'clojure.test))

  :warn-on-reflection false
  
  :repositories {"jboss" "http://repository.jboss.org/nexus/content/groups/public/"}
  
  :resources-path "config")