package ApiPosts;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTestUrlV2 {

    private static final String V2_PATH = "/public/v2";

    @Test
    public void getAllPosts() {
        List<Post> posts = RestAssured
                .given()
                .spec(HelperClass.getRequestSpec(V2_PATH))
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {
                });

        System.out.println("Total Posts Fetched: " + posts.size());
        System.out.println("First Post Title: " + posts.get(0).getTitle());
        Random random = new Random();
        int userId = posts.get(random.nextInt(posts.size())).getUser_id();
        System.out.println("Selected Random User ID: " + userId);
        Post post = new Post();
        post.setUser_id(userId);
        post.setTitle("New Post Title " + System.currentTimeMillis());
        post.setBody("This is the content of the post.");
        Post createdPost = RestAssured
                .given()
                .spec(HelperClass.getRequestSpec(V2_PATH))
                .body(post)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .extract()
                .as(Post.class);

        System.out.println(" New Post Created with ID: " + createdPost.getId());
    }

    @Test
    public void getAndUpdatePost() {
        List<User> users = RestAssured
                .given()
                .spec(HelperClass.getRequestSpec(V2_PATH))
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<User>>() {
                });

        List<User> inactiveUsers = users.stream()
                .filter(user -> "inactive".equalsIgnoreCase(user.getStatus()))
                .collect(Collectors.toList());

        if (!inactiveUsers.isEmpty()) {
            int inactiveUserId = inactiveUsers.get(0).getId();
            System.out.println("First Inactive User ID: " + inactiveUserId);
            String updateStatusJson = "{\"status\": \"active\"}";
            RestAssured
                    .given()
                    .spec(HelperClass.getRequestSpec(V2_PATH))
                    .body(updateStatusJson)
                    .when()
                    .put("/users/" + inactiveUserId)
                    .then()
                    .statusCode(200);

            System.out.println("User status updated to 'active' for ID: " + inactiveUserId);

            User updatedUser = RestAssured
                    .given()
                    .spec(HelperClass.getRequestSpec(V2_PATH))
                    .when()
                    .get("/users/" + inactiveUserId)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(User.class);

            System.out.println("Updated user status: " + updatedUser.getStatus());
            assertEquals("active", updatedUser.getStatus());
        } else {
            System.out.println("No inactive users found!");
        }
    }
    @Test
    public void overrideBaseUriTemporarily() {
        List<Product> products = RestAssured
                .given()
                .baseUri("https://api.restful-api.dev")
                .when()
                .get("/objects")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Product.class);

        products.forEach(System.out::println);
    }
}


