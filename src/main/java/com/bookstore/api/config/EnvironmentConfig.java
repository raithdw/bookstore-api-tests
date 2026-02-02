package com.bookstore.api.config;

public final class EnvironmentConfig {
    private static final String BASE_URL =
            System.getProperty("baseUrl", "https://fakerestapi.azurewebsites.net");

    public static String baseUrl() {
        return BASE_URL;
    }
}