package com.bookstore.api.tests.authors;

import com.bookstore.api.services.AuthorService;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authors API")
@Feature("Get Authors")
@Owner("qa-team")
public class GetAuthorsTest {

    private final AuthorService authorService = new AuthorService();

    @Test(description = "Get all authors")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies GET /Authors returns valid authors list")
    public void shouldGetAllAuthors() {
        var response = authorService.getAllAuthors();

        assertThat(response.statusCode()).isEqualTo(200);
        response.then().body(matchesJsonSchemaInClasspath("schemas/authors.schema.json"));
    }

    @Test(description = "Get non-existing author")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies GET /Authors/{id} returns 404 for invalid id")
    public void shouldReturn404ForInvalidAuthor() {
        var response = authorService.getAuthorById(999999);
        assertThat(response.statusCode()).isIn(400, 404);
    }
}