FROM openjdk:8
ADD target/Bubble.jar .
EXPOSE 8082
RUN java -jar Bubble.jar