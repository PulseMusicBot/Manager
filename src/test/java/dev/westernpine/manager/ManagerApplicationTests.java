package dev.westernpine.manager;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;
import java.util.Scanner;

class ManagerApplicationTests {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("ws://localhost:8080/backend");
        Map<String, String> headers = Map.of("Authorization", "h8AVoMwlPj6IkZD20D8D");
        WebSocketClient client = new WebSocketClient(uri, headers) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("Connected!");
            }

            @Override
            public void onMessage(String s) {
                System.out.println("Received: " + s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                System.out.println("Connection closed! [%d] %s (%b)".formatted(i, s, b));
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        };

        client.connect();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (!line.equals("quit"))
                client.send(line);
            else {
                client.close(1000, "Session completed.");
                System.exit(0);
            }
        }
    }
}
