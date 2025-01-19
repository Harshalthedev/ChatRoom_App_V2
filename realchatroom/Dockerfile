FROM maven:3-eclipse-temurin-17 AS BUILD
COPY . . 
RUN mvn clean package -DskipTests
 
FROM eclipse-temurin:17-alpine
COPY --from=BUILD /target/*.java chatroom.jar
EXPOSE 8080 
ENTRYPOINT [ "java","-jar","chatroom.jar" ]