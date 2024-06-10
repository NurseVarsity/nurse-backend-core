FROM openjdk:17-jdk-alpine
LABEL authors="akintolafelix"
EXPOSE 8888
COPY ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]