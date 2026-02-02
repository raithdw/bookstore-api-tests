package com.bookstore.api.client;

import com.bookstore.api.config.EnvironmentConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {
    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(EnvironmentConfig.baseUrl())
                .setContentType(ContentType.JSON)
                .build();
    }
}