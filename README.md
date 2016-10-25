Akka Makes Lots of Connections
==============================

**This turned out to be entirely false. Ignore me. :D**

In order to test this you need to have `nginx` installed.

How to Run
----------

```
> bin/run_nginx

# In terminal 1
> sbt 'run-main Server'

# In terminal 2
> sbt 'run-main Client'
```

Observation
-----------

The `Client` will create a pool and then just reuse those connections properly.
The `Server` will spew out a ton of `Accepting` and `Connecting` messages as it
creates new connections to nginx.

