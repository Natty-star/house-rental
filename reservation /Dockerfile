#FROM maven:3.8.3-openjdk-17
#WORKDIR /app
#COPY . .
#RUN mvn clean install
#CMD mvn spring-boot:run

FROM openjdk:17-alpine
ADD target/*.jar app.jar
ENTRYPOINT ["java", "-jar","app.jar"]