package Exp8;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPPeerChat {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println(
                "Usage: java UDPPeerChat <localPort> <peerIP> <peerPort>"
            );
            return;
        }

        int localPort = Integer.parseInt(args[0]);
        InetAddress peerIP = InetAddress.getByName(args[1]);
        int peerPort = Integer.parseInt(args[2]);

        DatagramSocket socket = new DatagramSocket(localPort);
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

                    System.out.println("Peer: " + message);
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

        System.out.println("Chat started. Type /quit to exit.");

        try {
            while (keyboard.hasNextLine()) {
                String message = keyboard.nextLine();

                if (message.equalsIgnoreCase("/quit")) {
                    break;
                }

                byte[] data = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet =
                    new DatagramPacket(data, data.length, peerIP, peerPort);
                socket.send(packet);
            }
        } finally {
            socket.close();
            keyboard.close();
        }
    }
}