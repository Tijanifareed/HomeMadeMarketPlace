# Build stage: Use Maven to build the application
FROM maven:3.9.9-ibm-semeru-21-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests && ls -l target

# Runtime stage: Use OpenJDK to run the application
FROM openjdk:24-slim-bullseye
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
