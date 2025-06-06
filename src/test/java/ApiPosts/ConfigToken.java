package ApiPosts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigToken {
    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

