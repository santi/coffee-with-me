FROM openjdk:11-jre-slim

ARG JAR_FILE=build/libs/coffee-backend*.jar

WORKDIR /app

COPY ${JAR_FILE} coffee-backend.jar

EXPOSE 8080
ENTRYPOINT java -jar /app/coffee-backend.jar
