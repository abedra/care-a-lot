(ns care-a-lot
  (:use compojure
        clojure.contrib.json.read)
  (:require [clojure.http.client :as http]))

(def care-bear "relevance")

(defn fetch-repositories
  []
  ((read-json
    (first
     (:body-seq
      (http/request (str "http://github.com/api/v2/json/repos/show/" care-bear)))))
   "repositories"))

(defn issues
  [repo]
  (count
   ((read-json
     (first
      (:body-seq
       (http/request (str "http://github.com/api/v2/json/issues/list/" care-bear "/" repo "/open")))))
    "issues")))

(defn last-commit
  [repo]
  ((first
    ((read-json
      (first
       (:body-seq
        (http/request (str "http://github.com/api/v2/json/commits/list/" care-bear "/" repo "/master")))))
     "commits"))
   "committed_date"))

(defn last-commit-html
  [repo]
  (let [last (last-commit repo)]
    [:td {:class "timeago" :title last} last]))

(defn format-repositories
  []
  (map (fn [repo] [:tr
                   [:td (link-to (repo "url") (repo "name"))]
                   [:td (repo "watchers")]
                   [:td (issues (repo "name"))]
                   [:td (repo "fork")]
                   [:td (repo "forks")]
                   (last-commit-html (repo "name"))])
       (fetch-repositories)))

(defn index
  [request]
  (html
   (doctype :html4)
   [:html
    [:head
     [:title "Care Bear Stare!"]
     (include-js "/assets/javascripts/jquery.js"
                 "/assets/javascripts/jquery.tablesorter.js"
                 "/assets/javascripts/jquery.timeago.js"
                 "/assets/javascripts/application.js")
     (include-css "/assets/stylesheets/style.css")
     [:link {:href "/assets/images/favicon.ico" :rel "shortcut icon"}]]
    [:body
     [:h2 "\"It takes you and me, working in harmony, working for the things we love, caring for each other\""]
     [:table#projects {:class "tablesorter"}
      [:thead
       [:tr
        [:th {:align "left"} "Name"]
        [:th "Watchers"]
        [:th "# of Issues"]
        [:th "Is it a Fork?"]
        [:th "# Forks"]
        [:th "Last Pushed"]]]
      [:tbody (format-repositories)]]]
    ]))

(defroutes care-a-lot-routes
  (GET "/" index)
  (GET "/favicon.ico" (serve-file "/assets/images/favicon.ico"))
  (GET "/assets/*" (or (serve-file (params :*)) :next)))

(defserver care-a-lot
  {:port 8080}
  "/*"
  (servlet care-a-lot-routes))

(defn -main
  []
  (start care-a-lot))