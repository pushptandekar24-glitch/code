package Exp7;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class PeerChat {

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println(
                "Listen: java PeerChat listen <port>");
            System.out.println(
                "Connect: java PeerChat connect <IP> <port>");
            return;
        }

        Socket socket;

        if (args[0].equalsIgnoreCase("listen")) {

            int port = Integer.parseInt(args[1]);

            try (ServerSocket listener = new ServerSocket(port)) {
                System.out.println("Waiting on port " + port + "...");
                socket = listener.accept();
            }

        } else if (args[0].equalsIgnoreCase("connect")
                && args.length == 3) {

            String ip = args[1];
            int port = Integer.parseInt(args[2]);

            socket = new Socket(ip, port);

        } else {
            System.out.println("Invalid arguments.");
            return;
        }

        chat(socket);
    }

    public static void chat(Socket socket) throws IOException {

        try (Socket connection = socket) {

            BufferedReader networkIn = new BufferedReader(
                new InputStreamReader(
                    connection.getInputStream(),
                    StandardCharsets.UTF_8));

            PrintWriter networkOut = new PrintWriter(
                new OutputStreamWriter(
                    connection.getOutputStream(),
                    StandardCharsets.UTF_8), true);

            BufferedReader keyboard = new BufferedReader(
                new InputStreamReader(System.in));

            System.out.println(
                "Connected. Type messages; /quit to exit.");

            Thread sender = new Thread(() -> {

                try {
                    String message;

                    while ((message = keyboard.readLine()) != null) {

                        if (message.equalsIgnoreCase("/quit")) {
                            break;
                        }

                        networkOut.println(message);

                        if (networkOut.checkError()) {
                            break;
                        }
                    }

                } catch (IOException e) {
                    System.out.println("Keyboard input error.");

                } finally {
                    try {
                        connection.close();
                    } catch (IOException e) {
                    }
                }
            });

            sender.setDaemon(true);
            sender.start();

            try {
                String received;

                while ((received = networkIn.readLine()) != null) {
                    System.out.println(received);
                }

            } catch (IOException e) {
                if (!connection.isClosed()) {
                    System.out.println("Connection interrupted.");
                }
            }

            System.out.println("Chat ended.");
        }
    }
}

// cd "$HOME\OneDrive\Desktop\code\CN"
// javac Exp7\PeerChat.java Exp7\MultiChatServer.java Exp7\MultiChatClient.java
// java -cp . Exp7.PeerChat listen 5000 (this all in termianl 1)

// java -cp . Exp7.PeerChat connect localhost 5000 (in terminal 2)