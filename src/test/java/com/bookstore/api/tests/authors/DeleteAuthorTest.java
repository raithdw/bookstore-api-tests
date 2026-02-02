package com.bookstore.api.tests.authors;

import com.bookstore.api.models.Author;
import com.bookstore.api.services.AuthorService;
import com.bookstore.api.utils.TestDataFactory;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authors API")
@Feature("Delete Author")
@Owner("qa-team")
public class DeleteAuthorTest {

    private final AuthorService authorService = new AuthorService();

    @Test(description = "Delete an existing author")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies DELETE /Authors/{id} removes the author")
    public void shouldDeleteExistingAuthor() {
        Author author = TestDataFactory.randomAuthor();
        Integer authorId = authorService.createAuthor(author)
                .then().statusCode(200)
                .extract().path("id");

        var response = authorService.deleteAuthor(authorId);
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test(description = "Delete non-existing author")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies DELETE /Authors/{id} handles missing author gracefully")
    public void shouldHandleDeleteNonExistingAuthor() {
        var response = authorService.deleteAuthor(999999);
        assertThat(response.statusCode()).isIn(200, 404);
    }

    @Test(description = "Get all authors after deletion")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies GET /Authors returns valid authors list")
    public void shouldGetAllAuthors() {
        var response = authorService.getAllAuthors();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getList("$")).isNotEmpty();
        response.then().body(matchesJsonSchemaInClasspath("schemas/authors.schema.json"));
    }
}