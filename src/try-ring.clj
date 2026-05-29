(require '[ring.adapter.jetty :refer [run-jetty]])
(run-jetty (fn [r] {:status 200 :body "ok"}) {:port 3000})
