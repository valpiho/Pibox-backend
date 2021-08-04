FROM maven:3.8.1-openjdk-11
EXPOSE 8080
COPY target/pibox-core-0.1-SNAPSHOT.jar /pibox-core.jar
CMD ["java", "-jar", "/pibox-core.jar"]