package ApiTests;

import org.junit.jupiter.api.Test;

public class UrlV2 extends BaseApiTest {
    private static final String V2_PATH = "/public/v2";

    @Test
    void postsValidation() {
        validateUserCount(V2_PATH, 10);
    }

    @Test
    void testPerPageReturnsCorrectNumber() {
        validatePerPage(V2_PATH, 5);
    }

    @Test
    void testAllUsersAreFemale() {
        validateGender(V2_PATH, "female");
    }

    @Test
    void testDeleteRandomUser() {
        deleteRandomUser(V2_PATH);
    }
}
