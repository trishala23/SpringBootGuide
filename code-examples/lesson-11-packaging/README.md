# Lesson 11 code example: packaging and running your app

Companion project for
[docs/lessons/11-packaging.md](../../docs/lessons/11-packaging.md).

## Package it

```bash
mvn clean package
```

## Run the packaged jar

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Override the port:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=9090
```
