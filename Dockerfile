FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar

EXPOSE 9090
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
