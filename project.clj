(defproject cheffy "1.0.0"
  :description "Cheffy REST API"
  :url "https://github.com/leoguilen/clojure-recipe-rest-api"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring "1.8.1"]
                 [integrant "0.8.0"]
                 [environ "1.2.0"]
                 [metosin/reitit "0.5.5"]
                 [seancorfield/next.jdbc "1.1.582"]
                 [org.postgresql/postgresql "42.2.14"]
                 [clj-http "3.10.0"]
                 [ovotech/ring-jwt "1.3.0"]
                 [camel-snake-kebab/camel-snake-kebab "0.4.1"]
                 [com.zaxxer/HikariCP "3.4.5"]]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:source-paths ["dev/src"]
                   :resources-paths ["dev/resources"]
                   :dependencies [[ring/ring-mock "0.4.0"]
                                  [integrant/repl "0.3.1"]]}}
  :uberjar-name "cheffy.jar")
