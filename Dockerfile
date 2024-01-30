FROM openjdk:21
MAINTAINER w2m
COPY target/superhero-app-0.0.1-SNAPSHOT.jar superhero-app-0.0.1.jar
ENTRYPOINT ["java","-jar","/superhero-app-0.0.1.jar"]