package com.bookstore.api.utils;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class AllureAttachments {

    // Attach request body if it exists
    public static void attachRequest(Object body) {
        if (body != null) {
            String content = body.toString();
            if (!content.isEmpty()) {
                Allure.addAttachment("Request", "application/json", prettyPrintJson(content), ".json");
            }
        }
    }

    // Attach response if it has content
    public static void attachResponse(Response response) {
        if (response != null && response.asByteArray().length > 0) {
            Allure.addAttachment("Response", "application/json", prettyPrintJson(response.asString()), ".json");
        }
    }

    // Optional: pretty-print JSON for Allure
    private static String prettyPrintJson(String json) {
        try {
            if (json.trim().startsWith("{") || json.trim().startsWith("[")) {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                Object obj = mapper.readValue(json, Object.class);
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }
        } catch (Exception ignored) {}
        return json; // return original if not valid JSON
    }
}