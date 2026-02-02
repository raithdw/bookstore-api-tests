FROM maven:3.9.9-eclipse-temurin-21
WORKDIR /app
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
COPY testng.xml .
CMD ["mvn", "test"]