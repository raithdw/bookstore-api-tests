package com.bookstore.api.services;

import com.bookstore.api.client.ApiClient;
import com.bookstore.api.config.EnvironmentConfig;
import com.bookstore.api.models.Book;
import io.restassured.response.Response;

public class BookService {

    private final ApiClient apiClient;
    private final String BASE_PATH = "/api/v1/Books";

    public BookService() {
        this.apiClient = new ApiClient(EnvironmentConfig.baseUrl());
    }

    public Response getAllBooks() {
        return apiClient.get(BASE_PATH);
    }

    public Response getBookById(int id) {
        return apiClient.get(BASE_PATH + "/" + id);
    }

    public Response createBook(Book book) {
        return apiClient.post(BASE_PATH, book);
    }

    public Response updateBook(Integer id, Book book) {
        return apiClient.put(BASE_PATH + "/" + id, book);
    }

    public Response deleteBook(int id) {
        return apiClient.delete(BASE_PATH + "/" + id);
    }
}