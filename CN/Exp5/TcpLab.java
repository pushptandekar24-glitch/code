
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.Scanner;

public class TcpLab {

    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 8192;
    private static final long MAX_FILE_SIZE = 100_000_000L;

    // ---------------- SERVER ----------------

    public static void startServer() throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started on port " + PORT);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream in = new DataInputStream(
                             socket.getInputStream());
                     DataOutputStream out = new DataOutputStream(
                             socket.getOutputStream())) {

                    String operation = in.readUTF();

                    switch (operation) {
                        case "HELLO":
                            String name = in.readUTF();

                            System.out.println(
                                    "Client says: Hello! I am " + name);

                            out.writeUTF(
                                    "Hello " + name + "! I am the server.");
                            break;

                        case "FILE":
                            receiveFile(in, out);
                            break;

                        case "CALC":
                            calculate(in, out);
                            break;

                        default:
                            out.writeUTF("Invalid operation");
                    }

                } catch (IOException e) {
                    System.out.println("Client error: " + e.getMessage());
                }
            }
        }
    }

    // ---------------- FILE TRANSFER ----------------

    private static void receiveFile(
            DataInputStream in,
            DataOutputStream out) throws IOException {

        String filename = in.readUTF();
        long length = in.readLong();

        // Validate the received filename and file size
        if (filename.isEmpty()
                || filename.contains("/")
                || filename.contains("\\")
                || filename.equals(".")
                || filename.equals("..")
                || length < 0
                || length > MAX_FILE_SIZE) {

            out.writeUTF("Invalid file information");
            return;
        }

        Path directory = Paths.get("received_files");
        Files.createDirectories(directory);

        Path destination = directory.resolve(filename);

        if (Files.exists(destination)) {
            out.writeUTF("File already exists on server");
            return;
        }

        long remaining = length;

        try (OutputStream fileOut =
                     Files.newOutputStream(destination)) {

            byte[] buffer = new byte[BUFFER_SIZE];

            while (remaining > 0) {

                int count = in.read(
                        buffer, 0,
                        (int) Math.min(buffer.length, remaining));

                if (count == -1) {
                    throw new EOFException("File transfer incomplete");
                }

                fileOut.write(buffer, 0, count);
                remaining -= count;
            }

        } catch (IOException e) {

            // Remove an incomplete file
            Files.deleteIfExists(destination);

            throw e;
        }

        out.writeUTF("File received successfully: " + filename);

        System.out.println("Received file: " + filename);
    }

    // ---------------- CALCULATOR ----------------

    private static void calculate(
            DataInputStream in,
            DataOutputStream out) throws IOException {

        double a = in.readDouble();
        String operator = in.readUTF();
        double b = in.readDouble();

        double result;

        switch (operator) {

            case "+":
                result = a + b;
                break;

            case "-":
                result = a - b;
                break;

            case "*":
                result = a * b;
                break;

            case "/":
                if (b == 0) {
                    out.writeUTF("Error: Division by zero");
                    return;
                }

                result = a / b;
                break;

            default:
                out.writeUTF("Error: Invalid operator");
                return;
        }

        out.writeUTF("Result = " + result);
    }

    // ---------------- CLIENT ----------------

    public static void startClient() throws IOException {

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Hello");
        System.out.println("2. File Transfer");
        System.out.println("3. Calculator");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        String host = "localhost";

        try (Socket socket = new Socket(host, PORT);
             DataInputStream in = new DataInputStream(
                     socket.getInputStream());
             DataOutputStream out = new DataOutputStream(
                     socket.getOutputStream())) {

            switch (choice) {

                case 1:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();

                    out.writeUTF("HELLO");
                    out.writeUTF(name);

                    System.out.println(
                            "Server reply: " + in.readUTF());
                    break;

                case 2:
                    System.out.print("Enter file path: ");
                    String filePath = sc.nextLine();

                    Path source = Paths.get(filePath);

                    if (!Files.isRegularFile(source)) {
                        System.out.println("Invalid file path");
                        return;
                    }

                    long length = Files.size(source);

                    if (length > MAX_FILE_SIZE) {
                        System.out.println("File exceeds 100 MB");
                        return;
                    }

                    String filename = source.getFileName().toString();

                    out.writeUTF("FILE");
                    out.writeUTF(filename);
                    out.writeLong(length);

                    try (InputStream fileIn =
                                 Files.newInputStream(source)) {

                        byte[] buffer = new byte[BUFFER_SIZE];
                        int count;

                        while ((count = fileIn.read(buffer)) != -1) {
                            out.write(buffer, 0, count);
                        }
                    }

                    out.flush();

                    System.out.println(
                            "Server reply: " + in.readUTF());
                    break;

                case 3:
                    System.out.print("Enter first number: ");
                    double a = sc.nextDouble();

                    System.out.print("Enter operator (+, -, *, /): ");
                    String operator = sc.next();

                    System.out.print("Enter second number: ");
                    double b = sc.nextDouble();

                    out.writeUTF("CALC");
                    out.writeDouble(a);
                    out.writeUTF(operator);
                    out.writeDouble(b);

                    System.out.println(
                            "Server reply: " + in.readUTF());
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        }
    }

    // ---------------- MAIN ----------------

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Start Server");
        System.out.println("2. Start Client");
        System.out.print("Enter mode: ");

        int mode = sc.nextInt();

        if (mode == 1) {
            startServer();
        } else if (mode == 2) {
            startClient();
        } else {
            System.out.println("Invalid mode");
        }
    }
}
