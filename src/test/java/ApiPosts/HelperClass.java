package ApiPosts;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class HelperClass {

    private static final String BASE_URI = "https://gorest.co.in";
    private static final String TOKEN = ConfigToken.getProperty("token");

    public static RequestSpecification getRequestSpec(String basePath) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(basePath)
                .addHeader("Authorization", TOKEN)
                .addHeader("Content-Type", "application/json")
                .build()
                .log()
                .all();
    }
}