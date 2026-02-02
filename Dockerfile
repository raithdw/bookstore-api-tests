# Use Maven with JDK 21
FROM maven:3.9.9-eclipse-temurin-21

# Create a non-root user to avoid permission issues
RUN useradd -ms /bin/bash appuser
USER appuser

WORKDIR /app

# Copy pom.xml and download dependencies offline
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copy source code and test config
COPY src ./src
COPY testng.xml .

# Run tests AND generate Allure report inside container
CMD ["mvn", "test", "allure:report"]