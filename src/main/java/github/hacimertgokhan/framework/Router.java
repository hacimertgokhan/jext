package github.hacimertgokhan.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

public class Router {
    private final Map<String, Function<Map<String, String>, String>> routes = new HashMap<>();
    private final String cssLink = "<link rel=\"stylesheet\" href=\"./../../jext/opt/style.css\">"; // CSS Linki

    public void addRoute(String path, Function<Map<String, String>, String> handler) {
        routes.put(path, handler);
    }

    public String handleRequest(String path, Map<String, String> params) {
        Function<Map<String, String>, String> handler = routes.get(path);
        if (handler != null) {
            return handler.apply(params);
        }
        return "404 - Not Found";
    }

    public void reloadRoutes() {
        routes.clear();
        File pagesDir = new File("jext/pages"); // "src/main/resources/pages" yerine "jext/pages" kullanıyoruz.
        File[] directories = pagesDir.listFiles(File::isDirectory);

        if (directories != null) {
            for (File dir : directories) {
                if (dir.getName().startsWith("_")) {
                    String folderName = dir.getName().substring(1);  // "_home" -> "home"

                    // properties dosyasını oku
                    File propertiesFile = new File(dir, "route.properties");
                    if (propertiesFile.exists()) {
                        Properties properties = new Properties();
                        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
                            properties.load(fis);
                            String path = properties.getProperty("path");

                            if (path != null) {
                                String finalFolderName = folderName;
                                addRoute(path, params -> renderPage(dir, "index.html"));
                                System.out.println("Route for " + finalFolderName + " loaded at path " + path);
                            }
                        } catch (IOException e) {
                            System.err.println("Failed to read properties for " + dir.getName() + ": " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    private String renderPage(File dir, String fileName) {
        File pageFile = new File(dir, fileName);
        if (pageFile.exists()) {
            try {
                String pageContent = new String(java.nio.file.Files.readAllBytes(pageFile.toPath()));
                return addCssToPage(pageContent);
            } catch (IOException e) {
                System.err.println("Failed to read page file " + pageFile.getName() + ": " + e.getMessage());
            }
        }
        return "Page not found!";
    }

    private String addCssToPage(String pageContent) {
        String headEndTag = "</head>";
        int insertIndex = pageContent.indexOf(headEndTag);
        if (insertIndex != -1) {
            return pageContent.substring(0, insertIndex) + cssLink + pageContent.substring(insertIndex);
        }
        return pageContent;
    }
    public void createDefaultFilesForNewPage(String pageName) {
        try {
            // jext/pages altında yeni sayfa dizini oluştur
            Path pageDir = Paths.get("jext/pages", "_" + pageName);

            // Eğer dizin yoksa oluştur
            if (!Files.exists(pageDir)) {
                Files.createDirectories(pageDir);
                System.out.println("Directory created for page: " + pageName);
            }

            // route.properties dosyasını oluştur
            Path propertiesFile = pageDir.resolve("route.properties");
            if (!Files.exists(propertiesFile)) {
                Properties properties = new Properties();
                properties.setProperty("path", "/" + pageName);
                try (FileOutputStream fos = new FileOutputStream(propertiesFile.toFile())) {
                    properties.store(fos, null);
                    System.out.println("Created route.properties for: " + pageName);
                }
            }

            // index.html dosyasını oluştur (CSS import ile)
            Path indexFile = pageDir.resolve("index.html");
            if (!Files.exists(indexFile)) {
                String htmlContent = String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>%s</title>
                <link rel="stylesheet" href="../../../jext/opt/style.css">
            </head>
            <body>
                <h1>Welcome to %s page!</h1>
            </body>
            </html>""", pageName, pageName);

                Files.writeString(indexFile, htmlContent);
                System.out.println("Created index.html for: " + pageName);
            }

        } catch (IOException e) {
            System.err.println("Error creating files for " + pageName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

}
