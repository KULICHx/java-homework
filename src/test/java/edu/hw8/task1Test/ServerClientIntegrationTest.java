package edu.hw8.task1Test;

import edu.hw8.task1.server.ServerMain;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Интеграционные тесты сервера цитат и клиента")
class ServerClientIntegrationTest {

    private static final int PORT = 8080;
    private static ServerMain server;
    private static ExecutorService serverExecutor;

    @BeforeAll
    static void startServer() throws IOException {
        Map<String, String> quotes = ServerMain.loadQuotesFromFile("/hw8/phrases.txt");
        server = new ServerMain(PORT, 5, quotes);

        serverExecutor = Executors.newSingleThreadExecutor();
        serverExecutor.submit(server::start);

        // Дать серверу время стартануть
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterAll
    static void stopServer() throws InterruptedException {
        if (server != null) {
            server.stop();
        }
        if (serverExecutor != null) {
            serverExecutor.shutdownNow();
            if (!serverExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                System.err.println("Warning: Server executor did not terminate in time.");
            }
        }
    }

    @Test
    @DisplayName("Клиент получает правильную цитату по существующему ключу")
    void clientGetsCorrectQuote() throws IOException {
        try (Socket client = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(client.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

            out.println("глупость");
            String response = in.readLine();

            assertEquals("Если бы глупость была суперспособностью, ты бы уже спас мир.", response,
                "Сервер должен вернуть правильную цитату для ключа 'глупость'");
        }
    }

    @Test
    @DisplayName("Клиент получает сообщение об ошибке для неизвестного ключа")
    void clientGetsNotFoundForUnknownKey() throws IOException {
        try (Socket client = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(client.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

            out.println("unknown_key");
            String response = in.readLine();

            assertEquals("Ключевое слово не найдено :(", response,
                "Сервер должен вернуть сообщение об ошибке для неизвестного ключа");
        }
    }
}
