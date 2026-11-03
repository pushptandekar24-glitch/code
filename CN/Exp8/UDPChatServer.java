package Exp8;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UDPChatServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(6000);
        Map<SocketAddress, String> clients = new HashMap<>();
        byte[] buffer = new byte[4096];

        System.out.println("UDP chat server running on port 6000.");

        try {
            while (true) {
                DatagramPacket packet =
                    new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                SocketAddress sender = packet.getSocketAddress();
                String received = new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength(),
                    StandardCharsets.UTF_8
                );

                String[] parts = received.split("\\|", 2);
                String type = parts[0];
                String text = parts.length == 2 ? parts[1] : "";

                if (type.equals("JOIN")) {
                    String name = text.trim();

                    if (name.isEmpty()) {
                        send(socket, sender, "SERVER: Username cannot be empty.");
                        continue;
                    }

                    clients.put(sender, name);
                    send(socket, sender, "SERVER: Joined as " + name);
                    System.out.println(name + " joined from " + sender);

                } else if (type.equals("MSG")) {
                    String name = clients.get(sender);

                    if (name == null) {
                        send(socket, sender,
                            "SERVER: Please restart the client to join.");
                        continue;
                    }

                    String outgoing = name + ": " + text;
                    System.out.println(outgoing);

                    for (SocketAddress client : clients.keySet()) {
                        send(socket, client, outgoing);
                    }

                } else if (type.equals("LEAVE")) {
                    String name = clients.remove(sender);

                    if (name != null) {
                        System.out.println(name + " left.");
                    }
                }
            }
        } finally {
            socket.close();
        }
    }

    private static void send(
        DatagramSocket socket,
        SocketAddress destination,
        String message
    ) throws Exception {
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet =
            new DatagramPacket(data, data.length, destination);
        socket.send(packet);
    }
}