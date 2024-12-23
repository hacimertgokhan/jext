package github.hacimertgokhan.framework;

import java.nio.file.*;
import java.util.Map;

public class Engine {
    public String render(String templatePath, Map<String, String> variables) {
        try {
            String content = Files.readString(Path.of(templatePath));
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }
            return content;
        } catch (Exception e) {
            return "Error rendering template: " + e.getMessage();
        }
    }
}
