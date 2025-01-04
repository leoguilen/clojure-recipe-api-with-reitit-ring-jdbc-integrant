(ns cheffy.server
  (:require
   [ring.adapter.jetty :as jetty]
   [integrant.core :as ig]
   [environ.core :refer [env]]
   [cheffy.router :as router]))

(defn app
  [env]
  ;;=> [{:java-class-path
  ;;     "/workspaces/clojure-recipe-rest-api/test:/workspaces/clojure-recipe-rest-api/dev/src:/workspaces/clojure-recipe-rest-api/src:/workspaces/clojure-recipe-rest-api/dev-resources:/workspaces/clojure-recipe-rest-api/resources:/workspaces/clojure-recipe-rest-api/target/classes:/home/lguilen/.m2/repository/ring/ring-core/1.8.1/ring-core-1.8.1.jar:/home/lguilen/.m2/repository/commons-io/commons-io/2.6/commons-io-2.6.jar:/home/lguilen/.m2/repository/cider/orchard/0.23.3/orchard-0.23.3.jar:/home/lguilen/.m2/repository/org/nrepl/incomplete/0.1.0/incomplete-0.1.0.jar:/home/lguilen/.m2/repository/org/msgpack/msgpack/0.6.12/msgpack-0.6.12.jar:/home/lguilen/.m2/repository/org/clojure/tools.reader/1.3.2/tools.reader-1.3.2.jar:/home/lguilen/.m2/repository/org/apache/httpcomponents/httpmime/4.5.8/httpmime-4.5.8.jar:/home/lguilen/.m2/repository/org/eclipse/jetty/jetty-http/9.4.28.v20200408/jetty-http-9.4.28.v20200408.jar:/home/lguilen/.m2/repository/org/eclipse/jetty/jetty-io/9.4.28.v20200408/jetty-io-9.4.28.v20200408.jar:/home/lguilen/.m2/repository/metosin/jsonista/0.2.6/jsonista-0.2.6.jar:/home/lguilen/.m2/repository/potemkin/potemkin/0.4.5/potemkin-0.4.5.jar:/home/lguilen/.m2/repository/metosin/reitit-frontend/0.5.5/reitit-frontend-0.5.5.jar:/home/lguilen/.m2/repository/org/clojure/java.data/1.0.78/java.data-1.0.78.jar:/home/lguilen/.m2/repository/metosin/reitit-schema/0.5.5/reitit-schema-0.5.5.jar:/home/lguilen/.m2/repository/org/clojure/clojure/1.11.1/clojure-1.11.1.jar:/home/lguilen/.m2/repository/ovotech/ring-jwt/1.3.0/ring-jwt-1.3.0.jar:/home/lguilen/.m2/repository/clj-http/clj-http/3.10.0/clj-http-3.10.0.jar:/home/lguilen/.m2/repository/metosin/reitit-interceptors/0.5.5/reitit-interceptors-0.5.5.jar:/home/lguilen/.m2/repository/meta-merge/meta-merge/1.0.0/meta-merge-1.0.0.jar:/home/lguilen/.m2/repository/com/fasterxml/jackson/dataformat/jackson-dataformat-smile/2.10.2/jackson-dataformat-smile-2.10.2.jar:/home/lguilen/.m2/repository/org/clojure/test.check/1.0.0/test.check-1.0.0.jar:/home/lguilen/.m2/repository/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/home/lguilen/.m2/repository/tigris/tigris/0.1.2/tigris-0.1.2.jar:/home/lguilen/.m2/repository/javax/xml/bind/jaxb-api/2.3.0/jaxb-api-2.3.0.jar:/home/lguilen/.m2/repository/org/apache/httpcomponents/httpclient/4.5.8/httpclient-4.5.8.jar:/home/lguilen/.m2/repository/metosin/reitit-sieppari/0.5.5/reitit-sieppari-0.5.5.jar:/home/lguilen/.m2/repository/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1.jar:/home/lguilen/.m2/repository/ring/ring-servlet/1.8.1/ring-servlet-1.8.1.jar:/home/lguilen/.m2/repository/expound/expound/0.8.5/expound-0.8.5.jar:/home/lguilen/.m2/repository/prismatic/schema/1.1.12/schema-1.1.12.jar:/home/lguilen/.m2/repository/mvxcvi/arrangement/1.2.0/arrangement-1.2.0.jar:/home/lguilen/.m2/repository/metosin/reitit-core/0.5.5/reitit-core-0.5.5.jar:/home/lguilen/.m2/repository/com/gfredericks/test.chuck/0.2.10/test.chuck-0.2.10.jar:/home/lguilen/.m2/repository/ring/ring-codec/1.1.1/ring-codec-1.1.1.jar:/home/lguilen/.m2/repository/clj-time/clj-time/0.10.0/clj-time-0.10.0.jar:/home/lguilen/.m2/repository/metosin/reitit/0.5.5/reitit-0.5.5.jar:/home/lguilen/.m2/repository/ring/ring-devel/1.8.1/ring-devel-1.8.1.jar:/home/lguilen/.m2/repository/environ/environ/1.2.0/environ-1.2.0.jar:/home/lguilen/.m2/repository/borkdude/edamame/0.0.11-alpha.12/edamame-0.0.11-alpha.12.jar:/home/lguilen/.m2/repository/integrant/repl/0.3.1/repl-0.3.1.jar:/home/lguilen/.m2/repository/metosin/reitit-swagger/0.5.5/reitit-swagger-0.5.5.jar:/home/lguilen/.m2/repository/org/eclipse/jetty/jetty-util/9.4.28.v20200408/jetty-util-9.4.28.v20200408.jar:/home/lguilen/.m2/repository/com/andrewmcveigh/cljs-time/0.5.1/cljs-time-0.5.1.jar:/home/lguilen/.m2/repository/org/eclipse/jetty/jetty-server/9.4.28.v20200408/jetty-server-9.4.28.v20200408.jar:/home/lguilen/.m2/repository/org/codehaus/mojo/animal-sniffer-annotations/1.17/animal-sniffer-annotations-1.17.jar:/home/lguilen/.m2/repository/crypto-equality/crypto-equality/1.0.0/crypto-equality-1.0.0.jar:/home/lguilen/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.11.0/jackson-datatype-jsr310-2.11.0.jar:/home/lguilen/.m2/repository/com/auth0/java-jwt/3.10.3/java-jwt-3.10.3.jar:/home/lguilen/.m2/repository/cheshire/cheshire/5.10.0/cheshire-5.10.0.jar:/home/lguilen/.m2/repository/mx/cider/logjam/0.3.0/logjam-0.3.0.jar:/home/lguilen/.m2/repository/org/clojure/tools.logging/1.1.0/tools.logging-1.1.0.jar:/home/lguilen/.m2/repository/borkdude/sci/0.1.0/sci-0.1.0.jar:/home/lguilen/.m2/repository/metosin/muuntaja/0.6.7/muuntaja-0.6.7.jar:/home/lguilen/.m2/repository/com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar:/home/lguilen/.m2/repository/hiccup/hiccup/1.0.5/hiccup-1.0.5.jar:/home/lguilen/.m2/repository/lambdaisland/deep-diff/0.0-47/deep-diff-0.0-47.jar:/home/lguilen/.m2/repository/clj-tuple/clj-tuple/0.2.2/clj-tuple-0.2.2.jar:/home/lguilen/.m2/repository/org/clojure/java.classpath/0.3.0/java.classpath-0.3.0.jar:/home/lguilen/.m2/repository/ring/ring-jetty-adapter/1.8.1/ring-jetty-adapter-1.8.1.jar:/home/lguilen/.m2/repository/com/auth0/jwks-rsa/0.12.0/jwks-rsa-0.12.0.jar:/home/lguilen/.m2/repository/nrepl/nrepl/1.1.1/nrepl-1.1.1.jar:/home/lguilen/.m2/repository/org/clojure/core.specs.alpha/0.2.62/core.specs.alpha-0.2.62.jar:/home/lguilen/.m2/repository/metosin/ring-swagger-ui/3.25.3/ring-swagger-ui-3.25.3.jar:/home/lguilen/.m2/repository/org/apache/httpcomponents/httpcore/4.4.11/httpcore-4.4.11.jar:/home/lguilen/.m2/repository/ns-tracker/ns-tracker/0.4.0/ns-tracker-0.4.0.jar:/home/lguilen/.m2/repository/slingshot/slingshot/0.12.2/slingshot-0.12.2.jar:/home/lguilen/.m2/repository/fipp/fipp/0.6.23/fipp-0.6.23.jar:/home/lguilen/.m2/repository/org/apache/httpcomponents/httpclient-cache/4.5.8/httpclient-cache-4.5.8.jar:/home/lguilen/.m2/repository/metosin/spec-tools/0.10.3/spec-tools-0.10.3.jar:/home/lguilen/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.11.0/jackson-annotations-2.11.0.jar:/home/lguilen/.m2/repository/borkdude/sci.impl.reflector/0.0.1/sci.impl.reflector-0.0.1.jar:/home/lguilen/.m2/repository/metosin/reitit-spec/0.5.5/reitit-spec-0.5.5.jar:/home/lguilen/.m2/repository/mvxcvi/puget/1.1.2/puget-1.1.2.jar:/home/lguilen/.m2/repository/com/google/guava/guava/27.1-jre/guava-27.1-jre.jar:/home/lguilen/.m2/repository/org/apache/httpcomponents/httpasyncclient/4.1.4/httpasyncclient-4.1.4.jar:/home/lguilen/.m2/repository/org/clojure/spec.alpha/0.3.218/spec.alpha-0.3.218.jar:/home/lguilen/.m2/repository/com/bhauman/spell-spec/0.1.2/spell-spec-0.1.2.jar:/home/lguilen/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.11.0/jackson-databind-2.11.0.jar:/home/lguilen/.m2/repository/org/clojure/tools.namespace/0.2.11/tools.namespace-0.2.11.jar:/home/lguilen/.m2/repository/cider/cider-nrepl/0.47.1/cider-nrepl-0.47.1.jar:/home/lguilen/.m2/repository/weavejester/dependency/0.2.1/dependency-0.2.1.jar:/home/lguilen/.m2/repository/joda-time/joda-time/2.7/joda-time-2.7.jar:/home/lguilen/.m2/repository/com/fasterxml/jackson/dataformat/jackson-dataformat-cbor/2.10.2/jackson-dataformat-cbor-2.10.2.jar:/home/lguilen/.m2/repository/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar:/home/lguilen/.m2/repository/seancorfield/next.jdbc/1.1.582/next.jdbc-1.1.582.jar:/home/lguilen/.m2/repository/metosin/reitit-ring/0.5.5/reitit-ring-0.5.5.jar:/home/lguilen/.m2/repository/metosin/reitit-swagger-ui/0.5.5/reitit-swagger-ui-0.5.5.jar:/home/lguilen/.m2/repository/integrant/integrant/0.8.0/integrant-0.8.0.jar:/home/lguilen/.m2/repository/crypto-random/crypto-random/1.2.0/crypto-random-1.2.0.jar:/home/lguilen/.m2/repository/metosin/malli/0.0.1-SNAPSHOT/malli-0.0.1-SNAPSHOT.jar:/home/lguilen/.m2/repository/javax/servlet/javax.servlet-api/3.1.0/javax.servlet-api-3.1.0.jar:/home/lguilen/.m2/repository/instaparse/instaparse/1.3.6/instaparse-1.3.6.jar:/home/lguilen/.m2/repository/org/postgresql/postgresql/42.2.14/postgresql-42.2.14.jar:/home/lguilen/.m2/repository/com/google/errorprone/error_prone_annotations/2.2.0/error_prone_annotations-2.2.0.jar:/home/lguilen/.m2/repository/riddley/riddley/0.1.12/riddley-0.1.12.jar:/home/lguilen/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/home/lguilen/.m2/repository/com/cognitect/transit-clj/1.0.324/transit-clj-1.0.324.jar:/home/lguilen/.m2/repository/org/apache/httpcomponents/httpcore-nio/4.4.10/httpcore-nio-4.4.10.jar:/home/lguilen/.m2/repository/commons-fileupload/commons-fileupload/1.4/commons-fileupload-1.4.jar:/home/lguilen/.m2/repository/metosin/reitit-malli/0.5.5/reitit-malli-0.5.5.jar:/home/lguilen/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar:/home/lguilen/.m2/repository/ring/ring-mock/0.4.0/ring-mock-0.4.0.jar:/home/lguilen/.m2/repository/metosin/reitit-middleware/0.5.5/reitit-middleware-0.5.5.jar:/home/lguilen/.m2/repository/metosin/schema-tools/0.12.2/schema-tools-0.12.2.jar:/home/lguilen/.m2/repository/org/javassist/javassist/3.18.1-GA/javassist-3.18.1-GA.jar:/home/lguilen/.m2/repository/tech/droit/clj-diff/1.0.1/clj-diff-1.0.1.jar:/home/lguilen/.m2/repository/commons-codec/commons-codec/1.12/commons-codec-1.12.jar:/home/lguilen/.m2/repository/com/cognitect/transit-java/1.0.343/transit-java-1.0.343.jar:/home/lguilen/.m2/repository/metosin/reitit-dev/0.5.5/reitit-dev-0.5.5.jar:/home/lguilen/.m2/repository/ring/ring/1.8.1/ring-1.8.1.jar:/home/lguilen/.m2/repository/metosin/sieppari/0.0.0-alpha13/sieppari-0.0.0-alpha13.jar:/home/lguilen/.m2/repository/metosin/reitit-http/0.5.5/reitit-http-0.5.5.jar:/home/lguilen/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.11.0/jackson-core-2.11.0.jar:/home/lguilen/.m2/repository/clj-stacktrace/clj-stacktrace/0.2.8/clj-stacktrace-0.2.8.jar:/home/lguilen/.m2/repository/org/clojure/core.rrb-vector/0.0.14/core.rrb-vector-0.0.14.jar:/home/lguilen/.m2/repository/org/checkerframework/checker-qual/2.5.2/checker-qual-2.5.2.jar",
  ;;     :lein-java-cmd "java",
  ;;     :path
  ;;     "/vscode/vscode-server/bin/linux-x64/fabdb6a30b49f79a7aba0f2ad9df9b399473380f/bin/remote-cli:/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/bin/:/home/lguilen/.local/bin",
  ;;     :stderr-encoding "ANSI_X3.4-1968",
  ;;     :oldpwd "/vscode/vscode-server/bin/linux-x64/fabdb6a30b49f79a7aba0f2ad9df9b399473380f",
  ;;     :electron-run-as-node "1",
  ;;     :classpath "/usr/share/java/leiningen-2.11.2-standalone.jar",
  ;;     :sun-management-compiler "HotSpot 64-Bit Tiered Compilers",
  ;;     :java-vendor-url "https://adoptium.net/",
  ;;     :home "/home/lguilen",
  ;;     :wayland-display "vscode-wayland-51b9026e-99c0-442a-a399-552b41c76e02.sock",
  ;;     :user-country "US",
  ;;     :jack-in-lein-profiles "",
  ;;     :lscolors "Gxfxcxdxbxegedabagacad",
  ;;     :shlvl "2",
  ;;     :remote-containers-display-sock "/tmp/.X11-unix/X0",
  ;;     :user-language "en",
  ;;     :clojure-compile-path "/workspaces/clojure-recipe-rest-api/target/classes",
  ;;     :trampoline-file "/tmp/lein-trampoline-A7oCPihb5WFEe",
  ;;     :java-vm-specification-name "Java Virtual Machine Specification",
  ;;     :vscode-esm-entrypoint "vs/workbench/api/node/extensionHostProcess",
  ;;     :sun-java-command "clojure.main -i /tmp/form-init17576494408460591433.clj",
  ;;     :pwd "/workspaces/clojure-recipe-rest-api",
  ;;     :java-runtime-name "OpenJDK Runtime Environment",
  ;;     :browser "/vscode/vscode-server/bin/linux-x64/fabdb6a30b49f79a7aba0f2ad9df9b399473380f/bin/helpers/browser.sh",
  ;;     :java-vm-compressedoopsmode "Zero based",
  ;;     :jack-in-undefined "1.1.1",
  ;;     :file-separator "/",
  ;;     :vscode-ipc-hook-cli "/tmp/user/1000/vscode-ipc-e10da91b-a6e4-4623-990c-8051996d446f.sock",
  ;;     :ssh-auth-sock "/tmp/vscode-ssh-auth-14c07d74-ab22-4548-bd8f-d384c9e088ea.sock",
  ;;     :jack-in-cider-nrepl-version "0.47.1",
  ;;     :user-name "lguilen",
  ;;     :- "/opt/java/openjdk/bin/java",
  ;;     :user-home "/home/lguilen",
  ;;     :sun-cpu-endian "little",
  ;;     :cheffy-version "0.1.0-SNAPSHOT",
  ;;     :java-specification-vendor "Oracle Corporation",
  ;;     :lein-home "/home/lguilen/.lein",
  ;;     :vscode-handles-uncaught-errors "true",
  ;;     :native-encoding "ANSI_X3.4-1968",
  ;;     :remote-containers "true",
  ;;     :java-vendor "Eclipse Adoptium",
  ;;     :hostname "d80b20024f01",
  ;;     :jack-in-nrepl-port-file ".nrepl-port",
  ;;     :logname "lguilen",
  ;;     :sun-io-unicode-encoding "UnicodeLittle",
  ;;     :java-io-tmpdir "/tmp",
  ;;     :lein-jvm-opts
  ;;     "-Xbootclasspath/a:/usr/share/java/leiningen-2.11.2-standalone.jar -XX:+TieredCompilation -XX:TieredStopAtLevel=1",
  ;;     :xdg-runtime-dir "/tmp/user/1000",
  ;;     :sun-boot-library-path "/opt/java/openjdk/lib",
  ;;     ...}]
  (router/routes env))

(defmethod ig/prep-key :server/jetty
  [_ config]
  (merge config {:port (Integer/parseInt (env :port))}))

(defmethod ig/init-key :server/jetty
  [_ {:keys [handler port]}]
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/init-key :cheffy/app
  [_ config]
  (println "\nStarted app")
  (app config))

(defmethod ig/init-key :db/postgres
  [_ config]
  (println "\nConfigured db")
  (:jdbc-url config))

(defmethod ig/halt-key! :server/jetty
  [_ jetty]
  (.stop jetty))

(defn -main
  [config-file]
  (let [config (-> config-file slurp ig/read-string)]
    (-> config ig/prep ig/init)))
