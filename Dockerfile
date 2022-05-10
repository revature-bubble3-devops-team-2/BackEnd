FROM openjdk:8-jdk-alpine as runner
# Copy the JAR from the target folder into the container
COPY target/Bubble.jar Bubble.jar 
EXPOSE 5000

ENTRYPOINT ["java", "-jar", "/Bubble.jar"]

