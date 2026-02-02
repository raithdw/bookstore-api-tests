package com.bookstore.api.tests.authors;

import com.bookstore.api.models.Author;
import com.bookstore.api.models.Book;
import com.bookstore.api.services.AuthorService;
import com.bookstore.api.utils.RetryAnalyzer;
import com.bookstore.api.utils.TestDataFactory;
import io.qameta.allure.*;
import org.assertj.core.api.AssertionsForClassTypes;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authors API")
@Feature("Create Author")
@Owner("qa-team")
public class CreateAuthorTest {

    private final AuthorService authorService = new AuthorService();

    @Test(description = "Create author successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies POST /Authors creates a new author")
    @Story("Happy path")
    public void shouldCreateAuthor() {
        Author author = TestDataFactory.randomAuthor();

        var response = authorService.createAuthor(author);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("firstName")).isEqualTo(author.firstName());
        response.then().body(matchesJsonSchemaInClasspath("schemas/author.schema.json"));

    }

    @Test(description = "Create author with missing fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies creating author with missing fields returns error")
    @Story("Edge case")
    public void shouldFailToCreateAuthorWithMissingFields() {
        Author invalidAuthor = new Author(
                null,
                null,
                null,
                null
        );

        var response = authorService.createAuthor(invalidAuthor);
        assertThat(response.statusCode()).isIn(400, 422);
    }

    @Test(description = "Create author with extremely long name")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that creating an author with a very long name is handled correctly")
    @Story("Edge case")
    public void shouldHandleLongName() {
        Author book = new Author(
                null,
                null,
                "L".repeat(500), // extremely long first name
                "Constantin"
        );

        var response = authorService.createAuthor(book);
        AssertionsForClassTypes.assertThat(response.statusCode()).isIn( 400, 422);
    }

//    Test for verifying Retry mechanism
//    @Test(retryAnalyzer = RetryAnalyzer.class)
//    public void shouldRetryOnRuntimeException() {
//            throw new RuntimeException("Forced failure to test retry");
//    }
}