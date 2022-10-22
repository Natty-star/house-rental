#FROM maven:3.8.3-openjdk-17
#WORKDIR /app
#COPY . .
#RUN mvn clean install
#CMD mvn spring-boot:run

#FROM openjdk:17-alpine
#ADD target/*.jar reservation.jar
#ENTRYPOINT ["java", "-jar","reservation.jar"]

FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} reservation-v1.jar
ENTRYPOINT ["java","-jar","/reservation-v1.jar"]
EXPOSE 8084