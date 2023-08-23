FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 9093
ARG APP_NAME=ms-files.jar
ADD target/*.jar ms-files.jar

ENTRYPOINT ["java","-jar", "/ms-files.jar"]

