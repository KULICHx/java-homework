package edu.hw2;

import edu.hw2.Task3.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class Task3Test {

    @Test
    @DisplayName("StableConnection всегда выполняет команду")
    void stableConnectionExecutesCommandSuccessfully() {
        Connection connection = new StableConnection();

        assertThatCode(() -> {
            connection.execute("echo Hello");
            connection.close();
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("FaultyConnection иногда бросает исключение")
    void faultyConnectionThrowsSometimes() {
        Connection connection = new FaultyConnection();
        boolean thrown = false;

        for (int i = 0; i < 100; i++) {
            try {
                connection.execute("any command");
            } catch (ConnectionException e) {
                thrown = true;
                break;
            }
        }

        assertThat(thrown).isTrue();
    }

    @Test
    @DisplayName("DefaultConnectionManager может вернуть и стабильное, и проблемное соединение")
    void defaultConnectionManagerReturnsBoth() {
        ConnectionManager manager = new DefaultConnectionManager(0.5);

        boolean gotStable = false;
        boolean gotFaulty = false;

        for (int i = 0; i < 100; i++) {
            Connection conn = manager.getConnection();
            if (conn instanceof StableConnection) gotStable = true;
            if (conn instanceof FaultyConnection) gotFaulty = true;

            if (gotStable && gotFaulty) break;
        }

        assertThat(gotStable).isTrue();
        assertThat(gotFaulty).isTrue();
    }

    @Test
    @DisplayName("FaultyConnectionManager всегда возвращает FaultyConnection")
    void faultyConnectionManagerAlwaysReturnsFaulty() {
        ConnectionManager manager = new FaultyConnectionManager();

        for (int i = 0; i < 10; i++) {
            assertThat(manager.getConnection()).isInstanceOf(FaultyConnection.class);
        }
    }

    @Test
    @DisplayName("tryExecute выполняется с первой попытки, если соединение стабильное")
    void tryExecuteSucceedsImmediately() {
        ConnectionManager stableManager = () -> new StableConnection();
        PopularCommandExecutor executor = new PopularCommandExecutor(stableManager, 3);

        assertThatCode(() -> executor.updatePackages()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("tryExecute выполняется после нескольких неудачных попыток")
    void tryExecuteSucceedsAfterRetries() {
        class CustomManager implements ConnectionManager {
            int attempt = 0;

            @Override
            public Connection getConnection() {
                attempt++;
                if (attempt < 3) {
                    return new Connection() {
                        @Override
                        public void execute(String command) {
                            throw new ConnectionException("fail " + attempt);
                        }

                        @Override
                        public void close() {
                        }
                    };
                } else {
                    return new StableConnection();
                }
            }
        }

        PopularCommandExecutor executor = new PopularCommandExecutor(new CustomManager(), 5);

        assertThatCode(() -> executor.updatePackages()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("tryExecute бросает исключение, если все попытки неудачны")
    void tryExecuteFailsAfterMaxAttempts() {
        class AlwaysFailingManager implements ConnectionManager {
            @Override
            public Connection getConnection() {
                return new Connection() {
                    @Override
                    public void execute(String command) {
                        throw new ConnectionException("always fails");
                    }

                    @Override
                    public void close() {
                    }
                };
            }
        }

        PopularCommandExecutor executor = new PopularCommandExecutor(new AlwaysFailingManager(), 3);

        assertThatThrownBy(() -> executor.updatePackages())
            .isInstanceOf(ConnectionException.class)
            .hasMessageContaining("Failed after 3 attempts")
            .hasCauseInstanceOf(ConnectionException.class);
    }
}
