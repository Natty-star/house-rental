#FROM maven:3-amazoncorretto-17 as build
#COPY /src /tmp/src
#COPY /pom.xml /tmp/pom.xml
#WORKDIR /tmp
#RUN mvn package
#
#
#FROM openjdk:17
#COPY --from=build /tmp/target/payment-0.0.1-SNAPSHOT.jar /tmp/payment-0.0.1-SNAPSHOT.jar
#EXPOSE 8086
#ENTRYPOINT ["java", "-jar" , "/tmp/payment-0.0.1-SNAPSHOT.jar"]

FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} payment-v1.jar
ENTRYPOINT ["java","-jar","/payment-v1.jar"]
EXPOSE 8086

