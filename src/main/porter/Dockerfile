FROM openjdk:17-alpine

RUN addgroup -S porter && adduser -S porter -G porter
USER porter:porter

ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
