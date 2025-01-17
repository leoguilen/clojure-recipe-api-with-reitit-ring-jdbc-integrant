
(ns cheffy.test-system
  (:require [integrant.repl.state :as state]
            [ring.mock.request :as mock]
            [muuntaja.core :as m]
            [cheffy.auth0 :as auth0]
            [clj-http.client :as http]))

(defn get-test-token
  [email]
  (->> {:content-type :json
        :cookie-policy :standard
        :body (m/encode "application/json"
                        {:client_id "0IQUaIHbDSKPqgrgTiSWEqXUNCfbFUfR"
                         :audience "<auth0-tenant-url>/api/v2/"
                         :grant_type "password"
                         :username email
                         :password "<random-password>"
                         :scope "openid profile email"})}
       (http/post "<auth0-tenant-url>/oauth/token")
       (m/decode-response-body)
       :access_token))

(defn create-auth0-test-user
  [{:keys [connection email password]}]
  (let [auth0 (-> state/system :auth/auth0)]
    (->> {:headers {"Authorization" (str "Bearer " (auth0/get-management-token auth0))} 
          :content-type :json
          :cookie-policy :standard
          :body (m/encode "application/json"
                          {:connection connection
                           :email email
                           :password password})}
         (http/post "<auth0-tenant-url>/api/v2/users")
         (m/decode-response-body))))

(def token (atom nil))

(defn test-endpoint
  ([method uri]
   (test-endpoint method uri nil))
  ([method uri opts]
   (let [app (-> state/system :cheffy/app)
         request (app (-> (mock/request method uri)
                          (cond-> (:auth opts) (mock/header :authorization (str "Bearer " (or @token (get-test-token "testing@cheffy.app"))))
                                  (:body opts) (mock/json-body (:body opts)))))]
     (update request :body (partial m/decode "application/json")))))

(comment
  (let [request (test-endpoint :get "/v1/recipes")
        decoded-request (m/decode-response-body request)]
    (assoc request :body decoded-request))
  (test-endpoint :post "/v1/recipes" {:img "string"
                                      :name "my name"
                                      :prep-time 30}))
