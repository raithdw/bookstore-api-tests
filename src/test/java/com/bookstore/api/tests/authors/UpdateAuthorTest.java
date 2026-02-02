package com.bookstore.api.tests.authors;

import com.bookstore.api.models.Author;
import com.bookstore.api.services.AuthorService;
import com.bookstore.api.utils.TestDataFactory;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authors API")
@Feature("Update Author")
@Owner("qa-team")
public class UpdateAuthorTest {

    private final AuthorService authorService = new AuthorService();

    @Test(description = "Update an existing author")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies PUT /Authors/{id} updates author correctly")
    public void shouldUpdateAuthor() {
        // Create an author first
        Author author = TestDataFactory.randomAuthor();
        Integer authorId = authorService.createAuthor(author)
                .then().statusCode(200)
                .extract().path("id");

        // Update author data
        Author updated = new Author(
                authorId,
                author.idBook(),
                author.firstName(),
                author.lastName() + " Updated"
        );

        var response = authorService.updateAuthor(authorId, updated);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("lastName")).isEqualTo(updated.lastName());
        response.then().body(matchesJsonSchemaInClasspath("schemas/author.schema.json"));
    }

    @Test(description = "Update non-existing author")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies PUT /Authors/{id} fails for non-existing author")
    public void shouldFailToUpdateNonExistingAuthor() {
        Author author = TestDataFactory.randomAuthor();
        var response = authorService.updateAuthor(null, author);
        assertThat(response.statusCode()).isIn(400, 404);
    }
}