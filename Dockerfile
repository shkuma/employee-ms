FROM bellsoft/liberica-openjdk-alpine:11
COPY target/employee-ms-0.0.1-SNAPSHOT.jar employee-ms-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","employee-ms-0.0.1-SNAPSHOT.jar"]