package ApiPosts;

import java.util.Map;

public record Product(String id, String name, Map<String, Object> data) {}
