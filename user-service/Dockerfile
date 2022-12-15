FROM openjdk:16-jdk-alpine

EXPOSE 8080

RUN mkdir /app
WORKDIR /app

ARG ARTIFACT=/build/libs/*.jar
RUN echo $ARTIFACT

COPY /build/libs/$ARTIFACT user-service.jar

ENTRYPOINT java -jar user-service.jar
