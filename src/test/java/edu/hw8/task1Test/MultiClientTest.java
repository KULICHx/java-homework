package edu.hw8.task1Test;

import edu.hw8.task1.server.ServerMain;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование нескольких клиентских запросов")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MultiClientTest {

    private static final int TEST_PORT = 9090;  // любой свободный порт для теста
    private ServerMain server;

    @BeforeAll
    void setup() throws IOException, InterruptedException {
        // Загружаем цитаты из ресурсов
        Map<String, String> quotes = ServerMain.loadQuotesFromFile("/hw8/phrases.txt");
        assertFalse(quotes.isEmpty(), "Цитаты не должны быть пустыми");

        // Создаем и запускаем сервер
        server = new ServerMain(TEST_PORT, 5, quotes);
        server.start();

        // Ждем, пока сервер поднимется
        waitForServerStartup();
    }

    @AfterAll
    void teardown() {
        if (server != null) {
            server.stop();
        }
    }

    @Test
    @DisplayName("Обработка нескольких клиентских запросов параллельно")
    void handleMultipleClients() throws Exception {
        final int CLIENTS_COUNT = 3;
        ExecutorService clientPool = Executors.newFixedThreadPool(CLIENTS_COUNT);
        List<String> responses = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(CLIENTS_COUNT);

        for (int i = 0; i < CLIENTS_COUNT; i++) {
            clientPool.submit(() -> {
                try (Socket socket = new Socket("localhost", TEST_PORT);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    out.println("умно");  // ключ, который есть в phrases.txt
                    String response = in.readLine();
                    responses.add(response);

                } catch (IOException e) {
                    fail("Ошибка клиента: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Не все клиенты завершили работу вовремя");
        assertEquals(CLIENTS_COUNT, responses.size(), "Должны быть ответы от всех клиентов");

        for (String resp : responses) {
            assertEquals("Ты правда думаешь, что это прозвучало умно? Мило.", resp);
        }
        clientPool.shutdown();
    }

    private void waitForServerStartup() throws InterruptedException {
        int attempts = 10;
        while (attempts-- > 0) {
            try (Socket ignored = new Socket("localhost", TEST_PORT)) {
                return;
            } catch (IOException e) {
                Thread.sleep(200);
            }
        }
        fail("Сервер не стартовал вовремя");
    }

}
