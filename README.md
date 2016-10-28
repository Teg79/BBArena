# BBArena
Java Open Source version of a fantasy football game, that allow roster creation, network play, match report generation and easy rules customization.

# Roadmap
- Modernize the model
- Add events and hooks
- Add LRB 6 rules
- Web interface

# Test the project

Test the Crap Rules (Work In Progress)
```
cd bbarena-rules
mvn test -Dtest=TestCrap
```

Test with the Swing Prototype
```
cd bbarena-view
mvn exec:java -Dexec.mainClass="net.sf.bbarena.view.BBArenaGUIProto1"
```

# Prototype
## Team SetUp
![Team SetUp](/docs/team_setup.jpg)
## Interception
![Interception](/docs/interception.jpg)
## Action Ring
![Action Ring](/docs/action_ring.jpg)
