FROM openjdk:8
ADD target/Bubble.jar .
EXPOSE 8082
CMD java -jar Bubble.jar