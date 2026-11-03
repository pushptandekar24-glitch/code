package Exp6;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.concurrent.ThreadLocalRandom;

public class UDPFileSender {
    static final int PORT = 5000, CHUNK = 1200, TIMEOUT = 2000;
    static final int META = 1, DATA = 2, END = 3, ACK = 4;

    static byte[] packet(int type, int id, int sequence,
                         byte[] payload, int length) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);

        out.writeByte(type);
        out.writeInt(id);
        out.writeInt(sequence);
        out.writeShort(length);
        out.write(payload, 0, length);

        return bytes.toByteArray();
    }

    static void sendAndWait(DatagramSocket socket,
                            InetAddress receiver,
                            byte[] message, int id, int sequence)
                            throws IOException {

        DatagramPacket outgoing = new DatagramPacket(
                message, message.length, receiver, PORT);

        for (int attempt = 1; attempt <= 10; attempt++) {
            socket.send(outgoing);

            try {
                while (true) {
                    byte[] reply = new byte[100];
                    DatagramPacket incoming =
                            new DatagramPacket(reply, reply.length);

                    socket.receive(incoming);

                    if (!incoming.getAddress().equals(receiver)
                            || incoming.getPort() != PORT
                            || incoming.getLength() < 11) continue;

                    DataInputStream in = new DataInputStream(
                            new ByteArrayInputStream(reply, 0,
                                    incoming.getLength()));

                    int type = in.readUnsignedByte();
                    int acknowledgedId = in.readInt();
                    int acknowledgedSequence = in.readInt();

                    if (type == ACK && acknowledgedId == id
                            && acknowledgedSequence == sequence) {
                        return;
                    }
                }

            } catch (SocketTimeoutException e) {
                System.out.println("Retrying packet " + sequence
                        + " (attempt " + attempt + ")");
            }
        }

        throw new IOException(
                "No acknowledgement for packet " + sequence);
    }

    static void sendFile(DatagramSocket socket,
                         InetAddress receiver, Path file)
                         throws IOException {

        if (!Files.isRegularFile(file)) {
            throw new FileNotFoundException("Missing file: " + file);
        }

        int id = ThreadLocalRandom.current().nextInt();
        long size = Files.size(file);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream meta = new DataOutputStream(bytes);

        meta.writeUTF(file.getFileName().toString());
        meta.writeLong(size);

        byte[] metadata = bytes.toByteArray();

        System.out.println("Sending " + file + " (" + size + " bytes)");

        sendAndWait(socket, receiver,
                packet(META, id, -1, metadata, metadata.length),
                id, -1);

        int sequence = 0;

        try (InputStream input = Files.newInputStream(file)) {
            byte[] chunk = new byte[CHUNK];
            int count;

            while ((count = input.read(chunk)) != -1) {
                sendAndWait(socket, receiver,
                        packet(DATA, id, sequence, chunk, count),
                        id, sequence);
                sequence++;
            }
        }

        sendAndWait(socket, receiver,
                packet(END, id, sequence, new byte[0], 0),
                id, sequence);

        System.out.println("Finished " + file.getFileName());
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 5) {
            System.out.println("Usage: java UDPFileSender <receiver-IP> "
                    + "<script> <text> <audio> <video>");
            return;
        }

        InetAddress receiver = InetAddress.getByName(args[0]);

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT);

            for (int i = 1; i <= 4; i++) {
                sendFile(socket, receiver, Paths.get(args[i]));
            }
        }

        System.out.println("All four files sent.");
    }
}
