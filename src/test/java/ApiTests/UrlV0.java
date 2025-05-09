package ApiTests;
import org.junit.jupiter.api.Test;

public class UrlV0 extends BaseApiTest{
    private static final String V0_PATH = "/public-api";
    @Test
    void postsValidation() {
        validateUserCount(V0_PATH, 10);
    }

    @Test
    void testPerPageReturnsCorrectNumber() {
        validatePerPage(V0_PATH, 5);
    }

    @Test
    void testAllUsersAreFemale() {
        validateGender(V0_PATH, "female");
    }

    @Test
    public void testDeleteRandomUserFromV0() {
        deleteRandomUserv0("/public-api"); // V0
    }
}
