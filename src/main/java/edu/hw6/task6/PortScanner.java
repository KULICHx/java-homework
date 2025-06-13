package edu.hw6.task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static edu.hw6.task6.WellKnownPorts.TCP;
import static edu.hw6.task6.WellKnownPorts.UDP;

public final class PortScanner {
    private PortScanner() {
    }

    private static final Logger LOGGER = Logger.getLogger(PortScanner.class.getName());

    public static List<PortInfo> scanPorts(int startPort, int endPort) {
        List<PortInfo> results = new ArrayList<>();

        for (int port = startPort; port <= endPort; port++) {
            boolean tcpAvailable = isTcpPortAvailable(port);
            boolean udpAvailable = isUdpPortAvailable(port);

            results.add(new PortInfo(
                Protocol.TCP,
                port,
                tcpAvailable ? PortStatus.FREE : PortStatus.USED,
                tcpAvailable ? "" : TCP.getServiceName(port)
            ));

            results.add(new PortInfo(
                Protocol.UDP,
                port,
                udpAvailable ? PortStatus.FREE : PortStatus.USED,
                udpAvailable ? "" : UDP.getServiceName(port)
            ));
        }
        return results;
    }

    private static boolean isTcpPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean isUdpPortAvailable(int port) {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            datagramSocket.setReuseAddress(true);
            return true;
        } catch (SocketException e) {
            return false;
        }
    }

    public static void printScanResults(List<PortInfo> results) {
        String header = String.format("%-8s %-6s %-8s %s", "Protocol", "Port", "Status", "Service");
        String divider = "----------------------------------------";

        LOGGER.info(header);
        LOGGER.info(divider);

        results.stream()
            .filter(info -> info.status() == PortStatus.USED)
            .forEach(info -> {
                String entry = String.format("%-8s %-6d %-8s %s",
                    info.protocol(),
                    info.port(),
                    info.status(),
                    info.service());
                LOGGER.info(entry);
            });
    }

    public enum Protocol {
        TCP, UDP
    }

    public enum PortStatus {
        FREE, USED
    }

    public record PortInfo(
        Protocol protocol,
        int port,
        PortStatus status,
        String service
    ) {}
}
