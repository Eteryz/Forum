# DEPLOY

### Manual build to jar:
- FRONT
```
    cd ForumFrontend
    ng build --configuration production
```
- BACKEND
```
    cp -R ../ForumFrontend/dist/forum/* src/main/resources/static/
    mvn clean package
    java -jar ForumBackend\target\ForumBackend-0.0.1-SNAPSHOT.jar
```

### Build with maven: 
    cd Forum
    mvn install
    java -jar ForumBackend\target\ForumBackend-0.0.1-SNAPSHOT.jar