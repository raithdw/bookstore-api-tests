package com.bookstore.api.utils;

import com.bookstore.api.models.Book;
import com.bookstore.api.models.Author;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public final class TestDataFactory {

    private TestDataFactory() {
        // utility class
    }

    public static Book randomBook() {
        return new Book(
                0,
                "Automation Book " + System.nanoTime(),
                "API Automation Testing",
                250,
                "Excerpt",
                Instant.now().toString()
        );
    }

    public static Author randomAuthor() {
        // Random book ID for association
        int randomBookId = ThreadLocalRandom.current().nextInt(1, 1000);

        return new Author(
                0,
                randomBookId,
                "FirstName" + System.nanoTime(),
                "LastName" + System.nanoTime()
        );
    }
}