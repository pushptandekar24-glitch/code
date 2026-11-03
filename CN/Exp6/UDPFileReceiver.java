package Exp6;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class UDPFileReceiver {
    static final int PORT = 5000, MAX_PACKET = 1300;
    static final int META = 1, DATA = 2, END = 3, ACK = 4;

    static void ack(DatagramSocket socket, DatagramPacket received,
                    int id, int sequence) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);

        out.writeByte(ACK);
        out.writeInt(id);
        out.writeInt(sequence);
        out.writeShort(0);

        byte[] message = bytes.toByteArray();
        socket.send(new DatagramPacket(message, message.length,
                received.getAddress(), received.getPort()));
    }

    public static void main(String[] args) throws Exception {
        Path folder = Paths.get("ReceivedFiles");
        Files.createDirectories(folder);

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Listening on UDP port " + PORT);

            int completed = 0, activeId = -1, lastId = -1, next = 0;
            long expectedSize = 0, receivedBytes = 0;
            OutputStream fileOut = null;
            String name = "";

            long finishDeadline = 0;
            int lastEndSequence = -1;
            InetAddress lastAddress = null;
            int lastPort = -1;

            while (completed < 4 ||
                    System.nanoTime() < finishDeadline) {

                byte[] buffer = new byte[MAX_PACKET];
                DatagramPacket packet =
                        new DatagramPacket(buffer, buffer.length);

                try {
                    socket.receive(packet);
                } catch (SocketTimeoutException e) {
                    continue;
                }

                if (packet.getLength() < 11) continue;

                DataInputStream in = new DataInputStream(
                        new ByteArrayInputStream(buffer, 0,
                                packet.getLength()));

                int type = in.readUnsignedByte();
                int id = in.readInt();
                int sequence = in.readInt();
                int length = in.readUnsignedShort();

                if (length > packet.getLength() - 11) continue;

                if (completed == 4) {
                    if (type == END && id == lastId
                            && sequence == lastEndSequence
                            && length == 0
                            && packet.getAddress().equals(lastAddress)
                            && packet.getPort() == lastPort) {
                        ack(socket, packet, id, sequence);
                    }
                    continue;
                }

                if (type == META) {
                    if (id == activeId) {
                        ack(socket, packet, id, sequence);
                        continue;
                    }

                    if (fileOut != null) continue;

                    byte[] payload = new byte[length];
                    in.readFully(payload);

                    DataInputStream meta = new DataInputStream(
                            new ByteArrayInputStream(payload));

                    String supplied = meta.readUTF();
                    long size = meta.readLong();

                    if (size < 0) continue;

                    String safe = Paths.get(
                            supplied.replace('\\', '/'))
                            .getFileName().toString();

                    if (safe.isEmpty()) continue;

                    name = safe;
                    fileOut = Files.newOutputStream(
                            folder.resolve(name));

                    activeId = id;
                    expectedSize = size;
                    receivedBytes = 0;
                    next = 0;

                    System.out.println(
                            "Receiving " + name + " (" + size + " bytes)");

                    ack(socket, packet, id, sequence);

                } else if (type == DATA && id == activeId) {

                    if (sequence == next
                            && receivedBytes + length <= expectedSize) {

                        byte[] payload = new byte[length];
                        in.readFully(payload);

                        fileOut.write(payload);
                        receivedBytes += length;
                        next++;
                    }

                    if (sequence < next) {
                        ack(socket, packet, id, sequence);
                    }

                } else if (type == END) {

                    if (id == activeId && sequence == next
                            && receivedBytes == expectedSize) {

                        fileOut.close();
                        fileOut = null;

                        activeId = -1;
                        lastId = id;
                        lastEndSequence = sequence;
                        lastAddress = packet.getAddress();
                        lastPort = packet.getPort();

                        completed++;

                        if (completed == 4) {
                            finishDeadline = System.nanoTime()
                                    + 25_000_000_000L;
                            socket.setSoTimeout(1000);

                            System.out.println(
                                    "Final ACK recovery: waiting 25 seconds.");
                        }

                        System.out.println(
                                "Saved " + folder.resolve(name));

                        ack(socket, packet, id, sequence);

                    } else if (id == lastId
                            && sequence == lastEndSequence
                            && length == 0
                            && packet.getAddress().equals(lastAddress)
                            && packet.getPort() == lastPort) {

                        ack(socket, packet, id, sequence);
                    }
                }
            }

            System.out.println("All four files received.");
        }
    }
}
