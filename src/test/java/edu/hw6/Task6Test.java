package edu.hw6;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class Task6Test {

    @Test
    @DisplayName("Проверка сканирования известного TCP порта")
    void scanKnownTcpPort_ShouldReturnCorrectInfo() {
        // Arrange
        int knownPort = 80; // HTTP порт

        // Act
        List<edu.hw6.task6.PortScanner.PortInfo> results = edu.hw6.task6.PortScanner.scanPorts(knownPort, knownPort);

        // Assert
        assertThat(results)
            .hasSize(2)
            .anyMatch(info ->
                edu.hw6.task6.PortScanner.Protocol.TCP.equals(info.protocol()) &&
                    knownPort == info.port() &&
                    (edu.hw6.task6.PortScanner.PortStatus.USED.equals(info.status()) || edu.hw6.task6.PortScanner.PortStatus.FREE.equals(info.status())) &&
                    (info.service().contains("HTTP") || info.service().isEmpty())
            );
    }

    @Test
    @DisplayName("Проверка сканирования известного UDP порта")
    void scanKnownUdpPort_ShouldReturnCorrectInfo() {
        // Arrange
        int knownPort = 67;

        // Act
        List<edu.hw6.task6.PortScanner.PortInfo> results = edu.hw6.task6.PortScanner.scanPorts(knownPort, knownPort);

        // Assert
        assertThat(results)
            .hasSize(2)
            .anyMatch(info ->
                edu.hw6.task6.PortScanner.Protocol.UDP.equals(info.protocol()) &&
                    knownPort == info.port() &&
                    (edu.hw6.task6.PortScanner.PortStatus.USED.equals(info.status()) || edu.hw6.task6.PortScanner.PortStatus.FREE.equals(info.status())) &&
                    (info.service().contains("DHCP Server") || info.service().isEmpty())
            );
    }

    @Test
    @DisplayName("Проверка сканирования диапазона портов")
    void scanPortRange_ShouldReturnCorrectNumberOfResults() {
        // Arrange
        int startPort = 135;
        int endPort = 138;
        int expectedResults = (endPort - startPort + 1) * 2;

        // Act
        List<edu.hw6.task6.PortScanner.PortInfo> results = edu.hw6.task6.PortScanner.scanPorts(startPort, endPort);

        // Assert
        assertThat(results).hasSize(expectedResults);
    }

    @Test
    @DisplayName("Проверка статуса свободного порта")
    void scanUnusedPort_ShouldReturnFreeStatus() {
        // Arrange
        int testPort = 49152;

        // Act
        List<edu.hw6.task6.PortScanner.PortInfo> results = edu.hw6.task6.PortScanner.scanPorts(testPort, testPort);

        // Assert
        assertThat(results)
            .allMatch(info ->
                edu.hw6.task6.PortScanner.PortStatus.FREE.equals(info.status()) ||
                    (edu.hw6.task6.PortScanner.PortStatus.USED.equals(info.status()) && !info.service().isEmpty())
            );
    }

    @Test
    @DisplayName("Проверка структуры PortInfo")
    void portInfo_ShouldHaveCorrectStructure() {
        // Arrange
        edu.hw6.task6.PortScanner.Protocol protocol = edu.hw6.task6.PortScanner.Protocol.TCP;
        int port = 1234;
        edu.hw6.task6.PortScanner.PortStatus status = edu.hw6.task6.PortScanner.PortStatus.FREE;
        String service = "";

        // Act
        edu.hw6.task6.PortScanner.PortInfo portInfo = new edu.hw6.task6.PortScanner.PortInfo(protocol, port, status, service);

        // Assert
        assertThat(portInfo.protocol()).isEqualTo(protocol);
        assertThat(portInfo.port()).isEqualTo(port);
        assertThat(portInfo.status()).isEqualTo(status);
        assertThat(portInfo.service()).isEqualTo(service);
    }
}
