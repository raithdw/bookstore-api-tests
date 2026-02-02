package com.bookstore.api.tests.books;

import com.bookstore.api.models.Book;
import com.bookstore.api.services.BookService;
import com.bookstore.api.utils.TestDataFactory;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Books API")
@Feature("Update Book")
@Owner("qa-team")
public class UpdateBookTest {

    private final BookService bookService = new BookService();

    @Test(description = "Update an existing book")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that PUT /Books/{id} updates the book correctly")
    @Story("Happy path")
    public void shouldUpdateBook() {
        // Create a book first
        Book book = TestDataFactory.randomBook();
        Integer bookId = bookService.createBook(book)
                .then().statusCode(200)
                .extract().path("id");

        // Update book data
        Book updatedBook = new Book(
                bookId,
                book.title() + " Updated",
                book.description(),
                book.pageCount(),
                book.excerpt(),
                book.publishDate()
        );

        var response = bookService.updateBook(bookId, updatedBook);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("title")).isEqualTo(updatedBook.title());
        response.then().body(matchesJsonSchemaInClasspath("schemas/book.schema.json"));
    }

    @Test(description = "Update non-existing book")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that updating a non-existing book is handled gracefully")
    @Story("Edge case")
    public void shouldHandleUpdateNonExistingBook() {
        Book fakeBook = TestDataFactory.randomBook();
        var response = bookService.updateBook(null, fakeBook);
        assertThat(response.statusCode()).isIn(400, 404);
    }
}
