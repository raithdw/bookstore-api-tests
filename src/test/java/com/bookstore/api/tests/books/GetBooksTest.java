package com.bookstore.api.tests.books;

import com.bookstore.api.services.BookService;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Epic("Books API")
@Feature("Get Books")
@Owner("qa-team")
public class GetBooksTest {

    private final BookService bookService = new BookService();

    @Test(description = "Get all books")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies GET /Books returns a valid list of books")
    public void shouldReturnBooksList() {
        var response = bookService.getAllBooks();

        assertThat(response.statusCode()).isEqualTo(200);
        response.then().body(matchesJsonSchemaInClasspath("schemas/books.schema.json"));
    }

    @Test(description = "Get non-existing book")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies GET /Books/{id} returns 404 for invalid id")
    public void shouldReturn404ForInvalidBook() {
        var response = bookService.getBookById(999999);
        assertThat(response.statusCode()).isIn(404, 400);
    }
}