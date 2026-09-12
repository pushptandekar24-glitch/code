import java.util.Scanner;

public class SlidingWindow {

    // Go-Back-N Protocol
    static void goBackN(int totalFrames, int windowSize, Scanner sc) {
        System.out.println("\n--- Go-Back-N Protocol---");

        int i = 0;

        while (i < totalFrames) {
            System.out.print("\nSending frames: ");

            for (int j = i; j < i + windowSize && j < totalFrames; j++) {
                System.out.print(j + " ");
            }

            System.out.print("\nEnter the frame number that was lost "
                    + "(-1 if no frame is lost): ");

            int lostFrame = sc.nextInt();

            if (lostFrame == -1) {
                System.out.println("All frames acknowledged.");
                i = i + windowSize;
            } else {
                System.out.println("Frame " + lostFrame + " lost!");
                System.out.println(
                        "Go-Back-N: Retransmitting frame "
                                + lostFrame + " and all subsequent frames.");

                i = lostFrame;
            }
        }

        System.out.println("\nAll frames transmitted successfully.");
    }

    // Selective Repeat Protocol
    static void selectiveRepeat(int totalFrames, int windowSize, Scanner sc) {
        System.out.println("\n--- Selective Repeat Protocol---");

        boolean[] acknowledged = new boolean[totalFrames];
        int i = 0;

        while (i < totalFrames) {
            System.out.print("\nCurrent window: ");

            for (int j = i; j < i + windowSize && j < totalFrames; j++) {
                if (!acknowledged[j]) {
                    System.out.print(j + " ");
                }
            }

            System.out.print("\nEnter the frame number that was lost "
                    + "(-1 if no frame is lost): ");

            int lostFrame = sc.nextInt();

            if (lostFrame == -1) {

                for (int j = i; j < i + windowSize && j < totalFrames; j++) {
                    acknowledged[j] = true;
                }

                System.out.println("All frames in the window acknowledged.");

            } else {

                System.out.println("Frame " + lostFrame + " lost!");

                // Acknowledge all other frames
                for (int j = i; j < i + windowSize && j < totalFrames; j++) {
                    if (j != lostFrame) {
                        acknowledged[j] = true;
                    }
                }

                System.out.println(
                        "Selective Repeat: Retransmitting only frame "
                                + lostFrame);

                acknowledged[lostFrame] = true;
            }

            // Move window forward
            while (i < totalFrames && acknowledged[i]) {
                i++;
            }
        }

        System.out.println("\nAll frames transmitted successfully.");
    }

    // Main method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("      Sliding Window Protocol");
        System.out.println("=================================");

        System.out.println("1. Go-Back-N");
        System.out.println("2. Selective Repeat");

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        System.out.print("Enter total number of frames: ");
        int totalFrames = sc.nextInt();

        System.out.print("Enter window size: ");
        int windowSize = sc.nextInt();

        if (choice == 1) {
            goBackN(totalFrames, windowSize, sc);
        } else if (choice == 2) {
            selectiveRepeat(totalFrames, windowSize, sc);
        } else {
            System.out.println("Invalid choice!");
        }

        sc.close();
    }
}