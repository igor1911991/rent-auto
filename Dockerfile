#Build
FROM maven:3.8.7-openjdk-18-slim AS MAVEN_BUILD

ARG BASE_DIR=/tmp/
ARG SOURCE_DIR=/tmp/src/

COPY pom.xml ${BASE_DIR}
COPY src ${SOURCE_DIR}

WORKDIR ${BASE_DIR}

RUN mvn clean package -DskipTests -X

#Package
FROM openjdk:18-slim
ARG BASE_DIR=/tmp/
ARG JAR_FILE=target/*.jar

COPY --from=MAVEN_BUILD ${BASE_DIR}${JAR_FILE} /app.jar
EXPOSE 5556
ENTRYPOINT ["java","-jar","/app.jar"]