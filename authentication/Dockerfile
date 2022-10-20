#FROM openjdk:17
#ADD target/spring-batch-0.0.1-SNAPSHOT.jar spring-batch-0.0.1-SNAPSHOT.jar
#EXPOSE 8086
#ENTRYPOINT ["java", "-jar" , "spring-batch-0.0.1-SNAPSHOT.jar"]

FROM maven:3.8.3-openjdk-17
WORKDIR /app
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
