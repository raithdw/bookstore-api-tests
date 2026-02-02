package com.bookstore.api.services;

import com.bookstore.api.client.ApiClient;
import com.bookstore.api.config.EnvironmentConfig;
import com.bookstore.api.models.Author;
import io.restassured.response.Response;

public class AuthorService {

    private final ApiClient apiClient;
    private final String BASE_PATH = "/api/v1/Authors";

    public AuthorService() {
        this.apiClient = new ApiClient(EnvironmentConfig.baseUrl());
    }

    public Response getAllAuthors() {
        return apiClient.get(BASE_PATH);
    }

    public Response getAuthorById(int id) {
        return apiClient.get(BASE_PATH + "/" + id);
    }

    public Response createAuthor(Author author) {
        return apiClient.post(BASE_PATH, author);
    }

    public Response updateAuthor(Integer id, Author author) {
        return apiClient.put(BASE_PATH + "/" + id, author);
    }

    public Response deleteAuthor(int id) {
        return apiClient.delete(BASE_PATH + "/" + id);
    }
}