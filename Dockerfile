FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app

# Copy Maven config and download dependencies
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copy source code and test config
COPY src ./src
COPY testng.xml .

# Run tests AND generate Allure report
CMD ["mvn", "test", "allure:report"]