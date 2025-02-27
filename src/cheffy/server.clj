(ns cheffy.server
  (:require
   [ring.adapter.jetty :as jetty]
   [integrant.core :as ig]
   [environ.core :refer [env]]
   [cheffy.router :as router]
   [next.jdbc :as jdbc]
   [next.jdbc.connection :as njc]))

(defn app
  [env]
  (router/routes env))

(defmethod ig/prep-key :server/jetty
  [_ config]
  (merge config {:port (Integer/parseInt (env :port))}))

(defmethod ig/prep-key :db/postgres
  [_ config]
  (merge config {:jdbc-url (env :jdbc-url)}))

(defmethod ig/prep-key :auth/auth0
  [_ config]
  (merge config {:client-secret (env :auth0-client-secret)}))

(defmethod ig/init-key :server/jetty
  [_ {:keys [handler port]}]
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/init-key :cheffy/app
  [_ config]
  (println "\nStarted app")
  (app config))

(defmethod ig/init-key :db/postgres
  [_ {:keys [jdbc-url]}]
  (println "\nConfigured db")
  (jdbc/with-options
    (njc/->pool com.zaxxer.hikari.HikariDataSource {:jdbcUri jdbc-url}) 
    jdbc/snake-kebab-opts))

(defmethod ig/init-key :auth/auth0
  [_ auth0]
  (println "\nConfigured auth0")
  auth0)

(defmethod ig/halt-key! :db/postgres
  [_ config]
  (.close ˆcom.zaxxer.hikari.HikariDataSource (:connectable config)))

(defmethod ig/halt-key! :server/jetty
  [_ jetty]
  (.stop jetty))

(defn -main
  [config-file]
  (let [config (-> config-file slurp ig/read-string)]
    (-> config ig/prep ig/init)))
