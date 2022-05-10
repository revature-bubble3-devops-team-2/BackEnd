# FROM openjdk:8-jdk-alpine
# COPY /target/Bubble.jar Bubble.jar 
# EXPOSE 5000
# ENTRYPOINT ["java", "-jar", "/Bubble.jar"]

FROM maven:3.6.3-openjdk-8 as builder
COPY src/ src/
COPY pom.xml pom.xml
RUN mvn clean package -Dmaven.test.skip

FROM openjdk:8-jdk-alpine as runner
# Copy the JAR from the target folder into the container
COPY --from=builder target/Bubble.jar Bubble.jar 
EXPOSE 5000

ENTRYPOINT ["java", "-jar", "/Bubble.jar"]

