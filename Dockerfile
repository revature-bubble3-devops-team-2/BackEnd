# FROM openjdk:8-jdk-alpine
# COPY /target/Bubble.jar Bubble.jar 
# EXPOSE 5000
# ENTRYPOINT ["java", "-jar", "/Bubble.jar"]

FROM openjdk:8-jdk-alpine

# Copy the JAR from the target folder into the container
COPY /target/Bubble.jar Bubble.jar 

ENTRYPOINT ["java", "-jar", "/Bubble.jar"]
