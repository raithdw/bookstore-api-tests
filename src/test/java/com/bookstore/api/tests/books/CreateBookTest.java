package com.bookstore.api.tests.books;

import com.bookstore.api.models.Book;
import com.bookstore.api.services.BookService;
import com.bookstore.api.utils.TestDataFactory;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Books API")
@Feature("Create Book")
@Owner("qa-team")
public class CreateBookTest {

    private final BookService bookService = new BookService();

    @Test(description = "Create book successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a book can be created via POST /Books")
    @Story("Happy path")
    public void shouldCreateBook() {
        Book book = TestDataFactory.randomBook();

        var response = bookService.createBook(book);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("title")).isEqualTo(book.title());
        response.then().body(matchesJsonSchemaInClasspath("schemas/book.schema.json"));
    }

    @Test(description = "Create book with missing required fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that creating a book with invalid/missing fields returns an error")
    @Story("Edge case")
    public void shouldFailToCreateBookWithMissingFields() {
        // Create an invalid book object (e.g., missing title)
        Book invalidBook = new Book(
                null,
                null,
                "Test description",
                100,
                "Test excerpt",
                "2026-01-01"
        );

        var response = bookService.createBook(invalidBook);

        // Typically API should return 400 Bad Request
        assertThat(response.statusCode()).isIn(400, 422);
    }

    @Test(description = "Create book with extremely long title")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that creating a book with a very long title is handled correctly")
    @Story("Edge case")
    public void shouldHandleLongTitle() {
        Book book = new Book(
                null,
                "L".repeat(500), // extremely long title
                "Description",
                120,
                "Excerpt",
                "2026-01-01"
        );

        var response = bookService.createBook(book);
        assertThat(response.statusCode()).isIn( 400, 422);
    }
}