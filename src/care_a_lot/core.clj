(ns care-a-lot.core
  (:use [clojure.data.json :only (read-str)]
        [ring.adapter.jetty :only (run-jetty)]
        [compojure.core :only (defroutes GET)]
        [hiccup.page :only (html5 include-js include-css)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

(def care-bear "abedra")

(defn fetch-repositories
  []
  (read-str
   (slurp (str "https://api.github.com/users/" care-bear "/repos?per_page=100"))))

(defn format-repositories
  []
  (map
   (fn [repo]
     [:tr
      [:td (repo "name")]
      [:td (repo "watchers")]
      [:td (repo "open_issues_count")]
      [:td {:class "timeago" :title (repo "pushed_at")} (repo "pushed_at")]
      [:td (repo "forks_count")]
      [:td (if (repo "fork") "No" "Yes")]])
   (fetch-repositories)))

(defn index
  []
  (html5
   [:html
    [:head
     [:title "Care Bear Stare!"]
     (include-js "javascripts/jquery.js"
                 "javascripts/jquery.tablesorter.js"
                 "javascripts/jquery.timeago.js"
                 "javascripts/application.js")
     (include-css "stylesheets/style.css")
     [:link {:href "images/favicon.ico" :rel "shortcut icon"}]]
    [:body
     [:h2 "Care Bear Stare"]
     [:table#projects {:class "tablesorter"}
      [:thead
       [:tr
        [:th {:align "left"} "Name"]
        [:th "Watchers"]
        [:th "Open Issues"]
        [:th "Last Pushed"]
        [:th "Number of Forks"]
        [:th "Original Work?"]]]
      [:tbody (format-repositories)]]]]))

(defroutes routes
  (GET "/" [] (index))
  (route/resources "/"))

(def application
  (-> (handler/site routes)))

(defn -main
  []
  (run-jetty (var application) {:port 8080
                                :join? false}))
