FROM openjdk:8-jdk-alpine
COPY /target/Bubble.jar Bubble.jar 
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/Bubble.jar"]
