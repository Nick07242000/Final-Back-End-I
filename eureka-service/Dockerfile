FROM openjdk:17-alpine
RUN apk update && apk add bash
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
COPY wait-for-it.sh .
ENTRYPOINT ["./wait-for-it.sh", "-t", "30","zipkin:9411", "--", "java", "-jar", "app.jar"]