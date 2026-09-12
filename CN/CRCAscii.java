import java.util.Scanner;

public class CRCAscii {

    static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();

        for (int i = 1; i < b.length(); i++) {
            result.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }

        return result.toString();
    }

    static String zeros(int n) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            result.append('0');
        }

        return result.toString();
    }

    static String mod2div(String dividend, String divisor) {

        int pick = divisor.length();

        String temp = dividend.substring(0, pick);

        while (pick < dividend.length()) {

            if (temp.charAt(0) == '1') {
                temp = xor(divisor, temp) + dividend.charAt(pick);
            } else {
                temp = xor(zeros(pick), temp) + dividend.charAt(pick);
            }

            pick++;
        }

        if (temp.charAt(0) == '1') {
            temp = xor(divisor, temp);
        } else {
            temp = xor(zeros(pick), temp);
        }

        return temp;
    }

    static String asciiToBinary(String text) {

        StringBuilder binary = new StringBuilder();

        for (char c : text.toCharArray()) {

            binary.append(
                    String.format("%8s", Integer.toBinaryString(c))
                            .replace(' ', '0'));
        }

        return binary.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ASCII text: ");
        String text = sc.nextLine();

        System.out.print("Enter Generator Polynomial (binary): ");
        String divisor = sc.nextLine();

        String data = asciiToBinary(text);

        System.out.println("Binary ASCII Data : " + data);
        String appended = data + zeros(divisor.length() - 1);

        String remainder = mod2div(appended, divisor);

        String codeword = data + remainder;

        System.out.println("CRC Remainder     : " + remainder);
        System.out.println("Transmitted Code  : " + codeword);

        char[] received = codeword.toCharArray();

        if (received.length > 5) {
            received[5] = (received[5] == '0') ? '1' : '0';
        }

        String receivedCode = new String(received);

        System.out.println("Received Code     : " + receivedCode);

        String check = mod2div(receivedCode, divisor);

        if (check.contains("1")) {
            System.out.println("Error Detected by CRC");
            System.out.println(
                    "Correction: Retransmission required (CRC detects but does not correct).");
        } else {
            System.out.println("No Error Detected");
        }

        sc.close();
    }
}