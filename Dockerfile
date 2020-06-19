FROM java:8
COPY target/ProjectManager-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar" ]
