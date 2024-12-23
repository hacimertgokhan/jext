package github.hacimertgokhan;

import com.sun.net.httpserver.HttpServer;
import github.hacimertgokhan.framework.Engine;
import github.hacimertgokhan.framework.Router;
import github.hacimertgokhan.framework.service.FileWatcher;

import java.io.File;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // İlk olarak gerekli dizinlerin olup olmadığını kontrol et
        createDirectoryIfNotExists("jext/pages");
        createDirectoryIfNotExists("jext/opt");

        // Varsayılan style.css dosyasını oluştur
        createDefaultStyleCss();

        Router router = new Router();
        Engine templateEngine = new Engine();

        HttpServer server = HttpServer.create(new InetSocketAddress(5807), 0);
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            String response = router.handleRequest(path, Map.of());
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        System.out.println("\uD83E\uDDED | Server started on http://localhost:5807");
        server.start();
        router.reloadRoutes();
        FileWatcher watcher = new FileWatcher("src/main/resources/pages");
        watcher.startWatching(() -> {
            System.out.println("File changed, reloading routes...");
            router.reloadRoutes();
        });
    }

    // Dizinin var olup olmadığını kontrol edip yoksa oluşturur
    private static void createDirectoryIfNotExists(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Created directory: " + dirPath);
        }
    }

    // style.css dosyasını oluşturur
    private static void createDefaultStyleCss() {
        String stylePath = "jext/opt/style.css";
        File styleFile = new File(stylePath);
        if (!styleFile.exists()) {
            try {
                Files.writeString(Paths.get(stylePath), "/* Default CSS */\nbody { font-family: Arial, sans-serif; }");
                System.out.println("Created default style.css in /jext/opt/");
            } catch (Exception e) {
                System.err.println("Error creating style.css: " + e.getMessage());
            }
        }
    }
}
