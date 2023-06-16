FROM eclipse-temurin:17-jdk-jammy
MAINTAINER logicea.com
COPY target/card.jar card.jar
ENTRYPOINT ["java","-jar","/card.jar"]