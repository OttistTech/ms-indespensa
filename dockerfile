FROM maven:3.8.3-openjdk-17 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN cd src/main/resources && \
    openssl genrsa > app.key && \
    openssl rsa -in app.key -pubout -out app.pub

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]