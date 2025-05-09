package ApiTests;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;
import java.util.Random;

public class BaseApiTests extends BaseTest {
    protected void validateUserCount(String path, int expectedCount) {
        given()
                .spec(getRequestSpec(path))
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("data", hasSize(expectedCount));
    }
    protected void validatePerPage(String path, int perPage) {
        given()
                .spec(getRequestSpec(path))
                .queryParam("per_page", perPage)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("data.size()", equalTo(perPage));
    }
    protected void validateGender(String path, String gender) {
        given()
                .spec(getRequestSpec(path))
                .queryParam("gender", gender)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("data.gender", everyItem(equalTo(gender)));
    }
    protected void deleteRandomUserv0(String path) {
        List<Integer> userIds = given()
                .spec(getRequestSpec(path))
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .path("data.id");

        if (userIds.isEmpty()) {
            System.out.println("⚠️ No users available to delete.");
            return;
        }

        int randomUserId = userIds.get(new Random().nextInt(userIds.size()));
        System.out.println("🎯 Selected random user ID: " + randomUserId);

        // DELETE request
        int deleteCode = given()
                .spec(getRequestSpec(path))
                .when()
                .delete("/users/" + randomUserId)
                .then()
                .statusCode(200) // V0 always returns 200
                .extract()
                .path("code");

        assertThat(deleteCode, anyOf(is(204), is(404)));

        // GET request to confirm deletion
        int getCode = given()
                .spec(getRequestSpec(path))
                .when()
                .get("/users/" + randomUserId)
                .then()
                .statusCode(200)
                .extract()
                .path("code");

        assertThat(getCode, is(404));
        System.out.println("✅ Confirmed user is deleted (code 404): " + randomUserId);
    }


    protected void deleteRandomUser(String path) {
        List<Integer> userIds = given()
                .spec(getRequestSpec(path))
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .path("data.id");

        if (userIds.isEmpty()) {
            System.out.println("⚠️ No users available to delete.");
            return;
        }

        int randomUserId = userIds.get(new Random().nextInt(userIds.size()));
        System.out.println("🎯 Random user ID: " + randomUserId);

        given()
                .spec(getRequestSpec(path))
                .when()
                .delete("/users/" + randomUserId)
                .then()
                .statusCode(anyOf(is(204), is(404),is(200)));

        given()
                .spec(getRequestSpec(path))
                .when()
                .get("/users/" + randomUserId)
                .then()
                .statusCode(404);

        System.out.println("✅ Confirmed deletion of: " + randomUserId);
    }
}
