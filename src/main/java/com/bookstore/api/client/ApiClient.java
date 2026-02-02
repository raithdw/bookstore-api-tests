package com.bookstore.api.client;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    private final RequestSpecification requestSpec;

    public ApiClient(String baseUri) {
        RestAssured.baseURI = baseUri;
        requestSpec = RestAssured.given()
                .header("Content-Type", "application/json");
    }

    public Response get(String path) {
        Response response = requestSpec.get(path);
        attachAllure("GET", path, null, response);
        return response;
    }

    public Response post(String path, Object body) {
        Response response = requestSpec.body(body).post(path);
        attachAllure("POST", path, body, response);
        return response;
    }

    public Response put(String path, Object body) {
        Response response = requestSpec.body(body).put(path);
        attachAllure("PUT", path, body, response);
        return response;
    }

    public Response delete(String path) {
        Response response = requestSpec.delete(path);
        attachAllure("DELETE", path, null, response);
        return response;
    }

    private void attachAllure(String method, String path, Object requestBody, Response response) {
        String requestContent = requestBody != null ? requestBody.toString() : "{}";
        String responseContent = response.getBody().asString().isEmpty() ? "{}" : response.getBody().asString();

        Allure.addAttachment(method + " " + path + " - Request", "application/json",
                requestContent, ".json");

        Allure.addAttachment(method + " " + path + " - Response", "application/json",
                responseContent, ".json");
    }
}