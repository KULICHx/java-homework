package edu.hw8.task1.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger LOGGER = Logger.getLogger(ServerMain.class.getName());
    public static final int SERVER_SHUTDOWN_TIMEOUT_SEC = 5;

    private final int port;
    public final Map<String, String> quotes;
    private final int maxConnections;

    private volatile boolean isRunning;
    private ExecutorService threadPool;
    private ServerSocket serverSocket;

    public ServerMain(int port, int maxConnections, Map<String, String> quotes) {
        this.port = port;
        this.maxConnections = maxConnections;
        this.quotes = new ConcurrentHashMap<>(quotes); // потокобезопасная копия
    }

    public void start() {
        if (isRunning) {
            return;
        }

        isRunning = true;

        threadPool = new ThreadPoolExecutor(
            maxConnections,
            maxConnections,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(maxConnections),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );

        Thread serverThread = new Thread(() -> {
            try (ServerSocket socket = new ServerSocket(port)) {
                this.serverSocket = socket;
                LOGGER.info("Сервер запущен на порту " + port);

                while (isRunning) {
                    try {
                        Socket clientSocket = socket.accept();
                        threadPool.execute(new MonoThreadClientHandler(clientSocket, quotes));
                    } catch (SocketException e) {
                        if (isRunning) {
                            LOGGER.warning("Ошибка сокета: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                LOGGER.severe("Ошибка сервера: " + e.getMessage());
            } finally {
                shutdown();
            }
        });

        serverThread.start();
    }

    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            LOGGER.warning("Ошибка завершения серверного сокета: " + e.getMessage());
        }
        shutdown();
    }

    private void shutdown() {
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(SERVER_SHUTDOWN_TIMEOUT_SEC, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    public static Map<String, String> loadQuotesFromFile(String resourcePath) throws IOException {
        Map<String, String> quotes = new HashMap<>();
        try (InputStream is = ServerMain.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Файл не найден: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }

                    String[] parts = line.split(" = ", 2);
                    if (parts.length == 2) {
                        quotes.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }
        }
        return quotes;
    }
}
