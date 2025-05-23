package edu.hw8.task1.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class QuoteClient {
    private QuoteClient(Socket socket) {

    }

    private static final Logger LOGGER = Logger.getLogger(QuoteClient.class.getName());
    private static final int RECONNECTION_DELAY_MS = 1000;
    private static final int SERVER_PORT = 8080;

    public static void runClient() {
        boolean shouldExit = false;
        while (!shouldExit) {
            try (Socket socket = new Socket("localhost", SERVER_PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 Scanner scanner = new Scanner(System.in)) {

                LOGGER.info("Connected to server. Type keywords ('quit' to exit):");

                while (true) {
                    String input = scanner.nextLine();
                    out.println(input);

                    if ("quit".equalsIgnoreCase(input)) {
                        shouldExit = true;
                        break;
                    }

                    String response = in.readLine();
                    if (response == null) {
                        LOGGER.info("Server closed connection");
                        break;
                    }
                    LOGGER.info("Server response: " + response);
                }
            } catch (IOException e) {
                LOGGER.info("Connection error, retrying in 1 second...");
                try {
                    Thread.sleep(RECONNECTION_DELAY_MS);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    shouldExit = true;
                }
            }
        }
    }
}
