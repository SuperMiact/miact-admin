FROM java:8
EXPOSE 8080
#ARG JAR_FILE
ADD target/miact-admin-0.0.1-SNAPSHOT.jar /miact-admin.jar
ENTRYPOINT ["java", "-jar","miact-admin.jar"]