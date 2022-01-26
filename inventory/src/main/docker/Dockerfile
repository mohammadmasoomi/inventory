FROM openjdk:11-jdk
ARG JAR_FILE=./*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dinventory.log.configuration=/home/log/inventory/logConfiguration.properties","/app.jar"]