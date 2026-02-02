FROM maven:3.9.9-eclipse-temurin-21

# Create a non-root user to avoid permission issues
ARG USER_ID=1000
ARG GROUP_ID=1000
RUN groupadd -g $GROUP_ID appgroup \
    && useradd -m -u $USER_ID -g $GROUP_ID appuser
USER appuser

WORKDIR /app

# Copy Maven config and download dependencies
COPY --chown=appuser:appgroup pom.xml .
RUN mvn -q dependency:go-offline

# Copy source code and test configuration
COPY --chown=appuser:appgroup src ./src
COPY --chown=appuser:appgroup testng.xml .

# Run tests AND generate Allure report directly in target/allure-maven
CMD ["mvn", "test", "allure:report"]