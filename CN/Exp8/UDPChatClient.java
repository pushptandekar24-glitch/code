package Exp8;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPChatClient {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println(
                "Usage: java UDPChatClient <serverIP> <username>"
            );
            return;
        }

        InetAddress serverIP = InetAddress.getByName(args[0]);
        String username = args[1].trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        DatagramSocket socket = new DatagramSocket();
        socket.connect(serverIP, 6000);
        Scanner keyboard = new Scanner(System.in);

        Thread receiver = new Thread(() -> {
            byte[] buffer = new byte[4096];

            while (!socket.isClosed()) {
                try {
                    DatagramPacket packet =
                        new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String message = new String(
                        packet.getData(),
                        packet.getOffset(),
                        packet.getLength(),
                        StandardCharsets.UTF_8
                    );

                    System.out.println(message);
                } catch (Exception e) {
                    if (!socket.isClosed()) {
                        System.out.println("Receive error: " + e.getMessage());
                    }
                    break;
                }
            }
        });

        receiver.setDaemon(true);
        receiver.start();

        try {
            send(socket, "JOIN|" + username);
            System.out.println(
                "Wait for join confirmation. Type /quit to exit."
            );

            while (keyboard.hasNextLine()) {
                String message = keyboard.nextLine();

                if (message.equalsIgnoreCase("/quit")) {
                    break;
                }

                send(socket, "MSG|" + message);
            }

            send(socket, "LEAVE|");
        } finally {
            socket.close();
            keyboard.close();
        }
    }

    private static void send(DatagramSocket socket, String message)
        throws Exception {
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.send(packet);
    }
}