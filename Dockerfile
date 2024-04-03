FROM openjdk:8-jdk-alpine
WORKDIR app
ARG ARTIFACT_NAME=target/servicedesk-1.0.0-SNAPSHOT.jar
COPY ${ARTIFACT_NAME} /app/servicedesk-1.0.0-SNAPSHOT.jar
EXPOSE 7100
ENTRYPOINT sh -c "java -jar -Djdk.io.File.enableADS=true -Dserver.profile=dev /app/servicedesk-1.0.0-SNAPSHOT.jar"
