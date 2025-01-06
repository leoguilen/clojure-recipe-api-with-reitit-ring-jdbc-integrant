(ns user
  (:require
   [cheffy.server]
   [integrant.core :as ig]
   [integrant.repl :as ig-repl]
   [integrant.repl.state :as state]
   [next.jdbc :as jdbc]
   [next.jdbc.sql :as sql]))

(ig-repl/set-prep!
 (fn [] (-> "dev/resources/config.edn" slurp ig/read-string)))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(def app (-> state/system :cheffy/app))
(def db (-> state/system :db/postgres))

(comment
  (app {:request-method :get :uri "/v1/recipes"})
  (app {:request-method :get :uri "/swagger.json"})
  (app {:request-method :post
        :uri "/v1/recipes"
        :body-params {:name "my recipe"
                      :prep-time 49
                      :img "image-url"}})
  (jdbc/execute! db ["SELECT * FROM recipe WHERE public = true"])
  (sql/find-by-keys db :recipe {:public false})
  (with-open [conn (jdbc/get-connection db)]
    {:public (sql/find-by-keys conn :recipe {:public true})
     :drafts (sql/find-by-keys conn :recipe {:public false :uid "auth0|5ef440986e8fbb001355fd9c"})})
  (with-open [conn (jdbc/get-connection db)]
    (let [recipe-id "a1995316-80ea-4a98-939d-7c6295e4bb46"]
      (sql/get-by-id conn :recipe recipe-id :recipe_id {})))
  (go)
  (halt)
  (reset))