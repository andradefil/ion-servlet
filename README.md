# Jetty Servlet API Upgrade Experiments

Reproducing classpath conflicts that ion users may hit when Datomic upgrades its internal Jetty from 9 to 12.

`override-deps` simulates the ion runtime behavior where Datomic's classpath wins.

## Run

```bash
clj -M:<alias> src/<script>
```

Example:

```bash
clj -M:old-ring-old-jetty-servlet try-ring.clj
```

Then in another terminal:

```bash
curl http://localhost:3000/
```

## Results

| Alias | Lib | Servlet Container | Script | Result |
|-------|-----|--------------------|--------|--------|
| `:old-ring-old-jetty-servlet` | ring 1.10.0 | jetty 9.4.53, javax.servlet 3.1.0 | try-ring.clj | 200 OK |
| `:new-ring-old-jetty-servlet` | ring 1.15.4 | jetty 9.4.53 | try-ring.clj | ClassNotFoundException: `Request$Handler` |
| `:new-ring-old-adapter-old-jetty` | ring 1.15.4 (adapter 1.10) | jetty 9.4.53 | try-ring.clj | 200 OK |
| `:new-ring-new-jetty-servlet` | ring 1.15.4 | jetty 12.1.9, javax.servlet 4.0 (ee8) | try-ring.clj | 200 OK |
| `:new-ring-new-jetty-ee10` | ring 1.15.4 | jetty 12.1.9, jakarta.servlet 6.1 (ee10) | try-ring.clj | 200 OK |
| `:jakarta-6-provided-5` | jakarta.servlet 6.1 app | jetty 12.1.9, jakarta.servlet 5.0 (ee10) | try-jakarta-servlet.clj | 500 NoClassDefFoundError: `ServletConnection` |
| `:jakarta-6-provided-6` | jakarta.servlet 6.1 app | jetty 12.1.9, jakarta.servlet 6.1 (ee10) | try-jakarta-servlet.clj | 200 OK |
| `:jakarta-6-with-javax-4` | jakarta.servlet 6.1 app | jetty 12.1.9, jakarta.servlet 6.1 + javax.servlet 4.0 | try-jakarta-servlet.clj | 200 OK |
| `:ring-15-jakarta-6-with-javax-4` | ring 1.15.4 | jetty 12.1.9, jakarta.servlet 6.1 + javax.servlet 4.0 (ee8+ee10) | try-ring.clj, try-coexist.clj | 200 OK (all three) |
