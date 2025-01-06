(ns cheffy.auth0
  (:require [clj-http.client :as http]
            [muuntaja.core :as m]))

(defn get-management-token
  [auth0]
  (->> {:throw-exceptions false
        :content-type :json
        :cookie-policy :standard
        :body (m/encode "application/json"
                        {:client_id "<auth0-client-id"
                         :client_secret (:client-secret auth0)
                         :audience "<auth0-tenant-url>/api/v2/"
                         :grant_type "client_credentials"})}
       (http/post "<auth0-tenant-url>/oauth/token")
       (m/decode-response-body)
       :access_token))

(defn get-role-id
  [token]
  (->> {:headers {"Authorization" (str "Bearer " token)}
        :throw-exceptions false
        :content-type :json
        :cookie-policy :standard}
       (http/get "<auth0-tenant-url>/api/v2/roles")
       (m/decode-response-body)
       (filter #(= (:name %) "manage-recipes"))
       (first)
       :id))
