FROM openjdk:8
COPY . **/target/*.jar
COPY . ../**/dist/*

ENV DB_USER postgres
ENV DB_PASS Password123!
ENV DB_URL bubble.cvtq9j4axrge.us-east-1.rds.amazonaws.com