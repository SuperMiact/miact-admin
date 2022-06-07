FROM java:8
EXPOSE 8080
ARG JAR_FILE
ADD target/${JAR_FILE}/miact-admin-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","miact-admin-0.0.1-SNAPSHOT.jar"]