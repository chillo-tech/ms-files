FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 9093
ARG APP_NAME=ms-zeeven.jar
ADD target/*.jar ms-zeeven.jar

ENTRYPOINT ["java","-jar", "/ms-files.jar"]

