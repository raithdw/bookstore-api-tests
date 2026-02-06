# Bookstore API Automation Tests

**API automation framework for **FakeRestAPI**.**
-   API under test: https://fakerestapi.azurewebsites.net
-   Covers **Books** and **Authors** APIs with full CRUD, edge cases, and schema validation.

## 🌲PROJECT STRUCTURE
    bookstore-api-tests/
    ├── .github/
    │   └── workflows/
    │       └── ci.yml
    ├── src/
    │   ├── main/
    │   │   └── java/com/bookstore/api/
    │   │       ├── client/
    │   │       │   ├── ApiClient.java
    │   │       │   └── RequestSpecFactory.java
    │   │       ├── config/
    │   │       │   └── EnvironmentConfig.java
    │   │       ├── models/
    │   │       │   ├── Book.java
    │   │       │   └── Author.java
    │   │       ├── services/
    │   │       │   ├── BookService.java
    │   │       │   └── AuthorService.java
    │   │       └── utils/
    │   │           ├── TestDataFactory.java
    │   │           └── AllureAttachments.java
    │   └── test/
    │       ├── java/com/bookstore/api/tests/
    │       │   ├── books/
    │       │   │   ├── CreateBookTest.java
    │       │   │   ├── GetBooksTest.java
    │       │   │   ├── UpdateBookTest.java
    │       │   │   └── DeleteBookTest.java
    │       │   └── authors/
    │       │       └── AuthorCrudTest.java
    │       └── resources/
    │           └── schemas/
    │               ├── book.schema.json
    │               ├── books.schema.json
    │               ├── author.schema.json
    │               └── authors.schema.json
    ├── Dockerfile
    ├── pom.xml
    ├── testng.xml
    └── README.md



## 🧠 Design Decisions
- Service layer abstracts HTTP calls from tests
- ApiClient centralizes request/response handling
- Allure attachments added at client level for consistency

## 🔧 Prerequisites
- Java 17+
- Maven 3.8+
- Docker (optional, for containerized runs)
- Allure CLI (for local report viewing)

## 🚀 Stack

- **Language:** Java 17
- **Testing:** TestNG, RestAssured
- **Reporting:** Allure
- **Containerization:** Docker
- **CI/CD:** GitHub Actions

---

## ✅ Test Coverage

- **Books API:** Create, Read, Update, Delete (happy paths + edge cases)
- **Authors API:** Create, Read, Update, Delete (happy paths + edge cases)
- **Validation:** JSON schema checks, response assertions
- **Extras:** Request/response logging, Allure attachments

### Note: DELETE endpoints may return empty bodies, therefore attachments may be empty by design.

---

## ⚡ Run Locally

### Maven
```bash
mvn clean test
allure serve target/allure-results
```

## 🐳 Run with Docker
-   _**Make sure Docker is running first**_

### Run for Windows:
```bash
docker build -t bookstore-api-tests .
```
```bash
docker run -v "${PWD}\target\allure-results:/app/target/allure-results" bookstore-api-tests
```

### Run for Linux / Mac:
```bash
docker build -t bookstore-api-tests .
```
```bash
docker run -v "$(pwd)/target/allure-results:/app/target/allure-results" bookstore-api-tests
```

## 🧪 CI/CD

-   Runs in Docker to ensure consistent environments

-   Allure results are uploaded as artifacts

-   Fully parallelize tests thanks to TestDataFactory.randomBook() and randomAuthor()

## ⚙️ Utilities

-   TestDataFactory: Randomizes test data for full isolation

-   JSON Schemas: Validates API responses automatically

## 📊 Reporting

-   All tests generate Allure reports

-   Includes request/response details, attachments, and step logs

-   Visual test history and statistics available in Allure

## 🌐 Live Allure Report

-   Latest test execution report is automatically published via GitHub Pages:

-   👉 **https://raithdw.github.io/bookstore-api-tests/index.html**

-   The report is updated on every push and pull request.

## 🔁 Test Retry Strategy

- Failed tests are retried **once automatically**
- Designed to handle intermittent API instability
- Retry attempts are fully visible in Allure reports


## 📝 Notes

-   Use Docker for cross-platform consistency

-   API base URL is configurable in ApiClient

-   Fully supports parallel execution and edge case testing

## ✒️ Author
Mihai Constantin

## 🪪 License
This project is licensed under the MIT License.
