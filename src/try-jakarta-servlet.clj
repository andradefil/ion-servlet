(import '[jakarta.servlet.http HttpServlet HttpServletRequest HttpServletResponse]
        '[org.eclipse.jetty.ee10.servlet ServletContextHandler ServletHolder]
        '[org.eclipse.jetty.server Server ServerConnector])

(let [servlet (proxy [HttpServlet] []
                (doGet [^HttpServletRequest req ^HttpServletResponse resp]
                  (let [conn (.getServletConnection req)]
                    (doto resp
                      (.setStatus 200)
                      (-> .getWriter (.println (str "ok, connection: " conn)))))))
      ctx (doto (ServletContextHandler.)
            (.addServlet (ServletHolder. servlet) "/*"))
      server (doto (Server.)
               (.setHandler ctx))]
  (.addConnector server (doto (ServerConnector. server) (.setPort 3000)))
  (.start server)
  (println "Server started"))
