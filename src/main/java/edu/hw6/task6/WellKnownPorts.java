package edu.hw6.task6;

import java.util.Map;
import static java.util.Map.entry;

public final class WellKnownPorts {
    private WellKnownPorts() {}

    public static final PortRegistry TCP = new PortRegistry(
        Map.ofEntries(
            entry(20, "FTP Data Transfer"),
            entry(21, "FTP Control"),
            entry(22, "SSH"),
            entry(23, "Telnet"),
            entry(25, "SMTP"),
            entry(53, "DNS"),
            entry(80, "HTTP"),
            entry(110, "POP3"),
            entry(135, "EPMAP"),
            entry(139, "NetBIOS Session Service"),
            entry(143, "IMAP"),
            entry(443, "HTTPS"),
            entry(445, "Microsoft-DS"),
            entry(3306, "MySQL"),
            entry(3389, "RDP")
        )
    );

    public static final PortRegistry UDP = new PortRegistry(
        Map.ofEntries(
            entry(67, "DHCP Server"),
            entry(68, "DHCP Client"),
            entry(69, "TFTP"),
            entry(123, "NTP"),
            entry(137, "NetBIOS Name Service"),
            entry(138, "NetBIOS Datagram Service"),
            entry(161, "SNMP"),
            entry(162, "SNMP Trap"),
            entry(500, "ISAKMP"),
            entry(1900, "SSDP")
        )
    );

    public static final class PortRegistry {
        private final Map<Integer, String> portMap;

        PortRegistry(Map<Integer, String> portMap) {
            this.portMap = Map.copyOf(portMap);
        }

        public String getServiceName(int port) {
            return portMap.getOrDefault(port, "Unknown service");
        }
    }
}
