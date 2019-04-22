This project currently requires a special Flow build:
```
git clone https://github.com/vaadin/flow.git
cd flow
git checkout lit-element
mvn install -DskipTests
```

When you have installed the custom snapshot, run the project as
```
mvn
```

Open http://localhost:8080/
