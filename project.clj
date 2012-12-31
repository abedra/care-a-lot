(defproject care-a-lot "0.0.1"
  :description "Show people how much you care on Github"
  :url "http://github.com/abedra/care-a-lot"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.json "0.2.0"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [compojure "1.1.3"]
                 [hiccup "1.0.2"]]
  :main care-a-lot.core)