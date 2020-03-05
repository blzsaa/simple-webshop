[TASK](task.md)

#### For rest-api documentation: 
1. http://localhost:8080/swagger-ui.html
1. open [webshop_swagger.yaml](src/main/resources/swagger/webshop_swagger.yaml)

#### Run program as docker container:
1. build docker container with: `./gradlew docker`
1. run with: `docker run -p 8080:8080 hu.blzsaa/simple-webshop:0.0.1-SNAPSHOT`

Run program as jar:
1. build jar with: `./gradlew bootJar`
1. run with: `java -jar build/libs/simple-webshop-0.0.1-SNAPSHOT.jar`
