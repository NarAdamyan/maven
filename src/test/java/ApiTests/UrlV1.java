package ApiTests;
import org.junit.jupiter.api.Test;

public class UrlV1 extends BaseApiTests {
    private static final String V1_PATH = "/public/v1";

    @Test
    void postsValidation() {
        validateUserCount(V1_PATH, 10);
    }

    @Test
    void testPerPageReturnsCorrectNumber() {
        validatePerPage(V1_PATH, 5);
    }

    @Test
    void testAllUsersAreFemale() {
        validateGender(V1_PATH, "female");
    }

    @Test
    void testDeleteRandomUser() {
        deleteRandomUser(V1_PATH);
    }
}
