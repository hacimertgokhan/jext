package github.hacimertgokhan.framework.service;

import github.hacimertgokhan.framework.Router;
import java.io.IOException;
import java.nio.file.*;

public class FileWatcher {
    private final Path dirPath;
    private final Router router;

    public FileWatcher(String dirPath) {
        this.dirPath = Paths.get(dirPath);
        this.router = new Router();
    }

    public void startWatching(Runnable onChange) {
        try {
            // Burada eski yol yerine jext/pages dizini kullanılacak
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path watchDir = Paths.get("jext/pages");  // jext/pages dizinini izle
            watchDir.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            System.out.println("Started watching directory: " + watchDir);

            new Thread(() -> {
                while (true) {
                    try {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            @SuppressWarnings("unchecked")
                            WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                            Path fileName = pathEvent.context();

                            if (fileName.toString().startsWith("_")) {
                                String pageName = fileName.toString().substring(1);
                                Path fullPath = watchDir.resolve(fileName);

                                if (Files.exists(fullPath) && Files.isDirectory(fullPath)) {
                                    System.out.println("Creating files for directory: " + fullPath);
                                    router.createDefaultFilesForNewPage(pageName);
                                    router.reloadRoutes();

                                    if (onChange != null) {
                                        onChange.run();
                                    }
                                }
                            }
                        }
                        key.reset();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }).start();
        } catch (IOException e) {
            System.err.println("Error starting file watcher: " + e.getMessage());
        }
    }

}
