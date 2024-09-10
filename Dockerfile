ARG JAVA_VERSION=21

ARG JAR_PATH='./build/libs/simi-0.0.1-SNAPSHOT.jar'
ARG DEST_PATH='/apps/simi.jar'
FROM amazoncorretto:${JAVA_VERSION}-alpine

RUN mkdir ./apps
COPY ./build/resources/aot /apps/aot
COPY ./build/libs/simi-0.0.1-SNAPSHOT.jar /apps/simi.jar
CMD ["java","-Dspring.aot.enabled=true","-Dspring.aot.resources=/apps/aot","-jar", "/apps/simi.jar"]