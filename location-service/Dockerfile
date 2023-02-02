FROM openjdk:16-jdk-alpine

EXPOSE 8081

RUN mkdir scr
WORKDIR scr

ARG ARTIFACT=*.jar
RUN echo pwd

COPY /build/libs/$ARTIFACT app.jar
ENTRYPOINT java -jar app.jar