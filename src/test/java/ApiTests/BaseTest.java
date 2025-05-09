package ApiTests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseTest {

    private static final String BASE_URI = "https://gorest.co.in";
    private static final String TOKEN = "Bearer 5c3607d95a41d3e662a9d5cf7c6d93dcd6c84a86aef2d85171c297354f8b26c0";

    public static RequestSpecification getRequestSpec(String basePath) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(basePath)
                .addHeader("Authorization", "Bearer 5c3607d95a41d3e662a9d5cf7c6d93dcd6c84a86aef2d85171c297354f8b26c0")
                .addHeader("Content-Type", "application/json")
                .build()
                .log()
                .all();
    }
}
