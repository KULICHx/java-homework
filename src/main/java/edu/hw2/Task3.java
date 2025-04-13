package edu.hw2;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс для работы с соединениями.
 */
public final class Task3 {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final double DEFAULT_FAILURE_PROBABILITY = 0.5;
    private static final String COMMAND_EXECUTED_MSG = "Command executed: ";
    private static final String CONNECTION_CLOSED_MSG = "Connection closed";
    private static final Random RANDOM = new Random();

    private Task3() {
    }

    /**
     * Интерфейс соединения.
     */
    public interface Connection extends AutoCloseable {
        void execute(String command);
    }

    /**
     * Менеджер соединений.
     */
    public interface ConnectionManager {
        Connection getConnection();
    }

    /**
     * Исключение соединения.
     */
    public static final class ConnectionException extends RuntimeException {
        public ConnectionException(String message) {
            super(message);
        }

        public ConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Исполнитель команд.
     */
    public static final class PopularCommandExecutor {
        private final ConnectionManager manager;
        private final int maxAttempts;

        public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
            this.manager = manager;
            this.maxAttempts = maxAttempts;
        }

        public void updatePackages() {
            tryExecute("apt update && apt upgrade -y");
        }

        void tryExecute(String command) {
            ConnectionException lastException = null;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                try (Connection connection = manager.getConnection()) {
                    connection.execute(command);
                    return;
                } catch (ConnectionException e) {
                    lastException = e;
                    LOGGER.error("Attempt {} failed: {}", attempt, e.getMessage());
                } catch (Exception e) {
                    lastException = new ConnectionException("Unexpected error", e);
                    break;
                }
            }

            throw new ConnectionException(
                "Failed after " + maxAttempts + " attempts",
                lastException
            );
        }
    }

    /**
     * Стабильное соединение.
     */
    public static final class StableConnection implements Connection {
        @Override
        public void execute(String command) {
            LOGGER.info(COMMAND_EXECUTED_MSG + command);
        }

        @Override
        public void close() {
            LOGGER.debug(CONNECTION_CLOSED_MSG);
        }
    }

    /**
     * Нестабильное соединение.
     */
    public static final class FaultyConnection implements Connection {
        @Override
        public void execute(String command) {
            if (RANDOM.nextDouble() < DEFAULT_FAILURE_PROBABILITY) {
                throw new ConnectionException("Connection failed: " + command);
            }
            LOGGER.info(COMMAND_EXECUTED_MSG + command);
        }

        @Override
        public void close() {
            LOGGER.debug(CONNECTION_CLOSED_MSG);
        }
    }

    /**
     * Менеджер соединений по умолчанию.
     */
    public static final class DefaultConnectionManager implements ConnectionManager {
        private final double faultyProbability;

        public DefaultConnectionManager(double faultyProbability) {
            this.faultyProbability = faultyProbability;
        }

        @Override
        public Connection getConnection() {
            return RANDOM.nextDouble() < faultyProbability
                ? new FaultyConnection()
                : new StableConnection();
        }
    }

    /**
     * Менеджер нестабильных соединений.
     */
    public static final class FaultyConnectionManager implements ConnectionManager {
        @Override
        public Connection getConnection() {
            return new FaultyConnection();
        }
    }
}
