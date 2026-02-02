package com.bookstore.api.tests.books;

import com.bookstore.api.services.BookService;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Books API")
@Feature("Delete Book")
@Owner("qa-team")
public class DeleteBookTest {

    private final BookService bookService = new BookService();

    @Test(description = "Delete an existing book")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies DELETE /Books/{id} removes the author")
    @Story("Happy path")
    public void shouldDeleteExistingBook() {
        // First, create a book to delete
        var createdBook = bookService.createBook(com.bookstore.api.utils.TestDataFactory.randomBook())
                .then().statusCode(200).extract().response();
        Integer bookId = createdBook.path("id");

        var response = bookService.deleteBook(bookId);
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test(description = "Delete non-existing book")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that deleting a non-existing book is handled gracefully")
    @Story("Edge case")
    public void shouldHandleDeleteNonExistingBook() {
        var response = bookService.deleteBook(999999);
        assertThat(response.statusCode()).isIn(200, 404);
    }

    @Test(description = "Get all books after deletion")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that GET /Books returns a valid list after deletion")
    @Story("Happy path")
    public void shouldReturnBooksList() {
        var response = bookService.getAllBooks();
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getList("$")).isNotEmpty();
        response.then().body(matchesJsonSchemaInClasspath("schemas/books.schema.json"));
    }
}