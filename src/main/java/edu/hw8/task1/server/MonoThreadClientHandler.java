package edu.hw8.task1.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonoThreadClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Map<String, String> quotes;

    public MonoThreadClientHandler(Socket socket, Map<String, String> quotes) {
        this.clientSocket = socket;
        this.quotes = quotes;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String request = reader.readLine();
            if (request == null) {
                return;
            }
            String response = quotes.getOrDefault(request, "Ключевое слово не найдено :(");
            writer.println(response);
        } catch (IOException e) {
            Logger.getLogger(MonoThreadClientHandler.class.getName()).log(Level.WARNING, "ОШибка Client handler", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                Logger.getLogger(MonoThreadClientHandler.class.getName())
                    .log(Level.FINE, "Ошибка завершения сокета", e);
            }
        }
    }
}
