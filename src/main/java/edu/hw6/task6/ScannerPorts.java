package edu.hw6.task6;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScannerPorts {

    private final static String HEADERS = "Port  Service";
    private final static int MAX_RESERVED_PORT = 49151;
    private final static int SSH_PORT = 22;
    private final static int SMTP_PORT = 25;
    private final static int DNS_PORT = 53;
    private final static int POP3_PORT = 110;
    private final static int NETBIOS_SESSION_SERVICE_PORT = 139;
    private final static int HTTPS_PORT = 443;
    private static final Map<Integer, String> DESCRIPTION_TCP = new HashMap<>() {
        {
            put(SSH_PORT, "SSH");
            put(SMTP_PORT, "SMTP");
            put(DNS_PORT, "DNS");
            put(POP3_PORT, "POP3");
            put(NETBIOS_SESSION_SERVICE_PORT, "NetBIOS Session Service");
            put(HTTPS_PORT, "HTTPS");
        }
    };
    private final static int NETBIOS_NAME_SERVICE_PORT = 137;
    private final static int NETBIOS_DATAGRAM_SERVICE_PORT = 138;
    private final static int ISAKMP_PORT = 500;
    private final static int SSDP_PORT = 1900;
    private final static int WS_DISCOVERY_PORT = 3702;
    private static final Map<Integer, String> DESCRIPTION_UDP = new HashMap<>() {{
        put(NETBIOS_NAME_SERVICE_PORT, "NetBIOS Name Service");
        put(NETBIOS_DATAGRAM_SERVICE_PORT, "NetBIOS Datagram Service");
        put(ISAKMP_PORT, "ISAKMP");
        put(SSDP_PORT, "Simple Service Discovery Protocol (SSDP)");
        put(WS_DISCOVERY_PORT, "Web Services Dynamic Discovery (WS-Discovery)");
    }};

    private ScannerPorts() {
    }

    public static void scanPorts(PrintStream printStream) {
        List<Integer> openTCPPorts = new ArrayList<>();
        List<Integer> openUDPPorts = new ArrayList<>();
        int countOpenPort = 0;
        for (int port = 0; port <= MAX_RESERVED_PORT; port++) {
            try (ServerSocket socket = new ServerSocket()) {
                countOpenPort--;
            } catch (IOException e) {
                countOpenPort++;
                openTCPPorts.add(port);
            }
            try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
                countOpenPort--;
            } catch (SocketException e) {
                countOpenPort++;
                openUDPPorts.add(port);
            }
        }

        printStream.println("TCP");
        printStream.println(HEADERS);

        openTCPPorts.stream().forEach((x) -> printStream.println(x + " " + DESCRIPTION_TCP.getOrDefault(x, "")));

        printStream.println("UDP");
        printStream.println(HEADERS);

        openUDPPorts.stream().forEach((x) -> printStream.println(x + " " + DESCRIPTION_UDP.getOrDefault(x, "")));
    }
}
