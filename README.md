# API Upgrade Experiments

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

| Alias | Script | Result |
|-------|--------|--------|
| `:old-ring-old-jetty-servlet` | try-ring.clj | 200 OK |
| `:new-ring-old-jetty-servlet` | try-ring.clj | ClassNotFoundException: `Request$Handler` |
| `:new-ring-old-adapter-old-jetty` | try-ring.clj | 200 OK |
| `:new-ring-new-jetty-servlet` | try-ring.clj | 200 OK |
| `:new-ring-new-jetty-ee10` | try-ring.clj | 200 OK |
| `:jakarta-6-provided-5` | try-jakarta-servlet.clj | 500 NoClassDefFoundError: `ServletConnection` |
| `:jakarta-6-provided-6` | try-jakarta-servlet.clj | 200 OK |
| `:jakarta-6-with-javax-4` | try-jakarta-servlet.clj | 200 OK |
| `:ring-15-jakarta-6-with-javax-4` | try-ring.clj, try-coexist.clj | 200 OK (all three) |
