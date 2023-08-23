FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 9093
ARG APP_NAME=ms-files.jar
ARG JAR_FILE=ms-files.jar
ADD  ${JAR_FILE} ms-files.jar

ENTRYPOINT ["java","-jar", "/ms-files.jar"]

