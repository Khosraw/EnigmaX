import java.io.*;
import java.math.BigInteger;

public class Test {
    public static void main(String[] args) throws IOException {
        BigInteger key = new BigInteger("0");
        int prime = new BigInteger("395").intValue();

        StringBuilder finalMessage = new StringBuilder("(%&$(*%&748395jfsjiJF");
        StringBuilder reducedMessage = transpositionEncrypt(finalMessage, key.intValue());
        StringBuilder originalMessage = transpositionDecrypt(reducedMessage, key.intValue());

        System.out.println(finalMessage);
        System.out.println(reducedMessage);
        System.out.println(originalMessage);
    }

    public static StringBuilder transpositionEncrypt(StringBuilder message, int key) {
        key %= Math.PI*5;
        if (key == 0) {
            key = 2;
        }
        StringBuilder encryptedMessage = new StringBuilder();

        // Create a 2D character array with dimensions (key, message length / key)
        char[][] matrix = new char[key][(int) Math.ceil((double) message.length() / key)];
        int charIndex = 0;

        // Populate the matrix with the characters from the message
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (charIndex < message.length()) {
                    matrix[j][i] = message.charAt(charIndex++);
                } else {
                    matrix[j][i] = ' ';
                }
            }
        }

        // Read the encrypted message from the matrix row by row
        for (char[] chars : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                encryptedMessage.append(chars[j]);
            }
        }

        return encryptedMessage;
    }

    public static StringBuilder transpositionDecrypt(StringBuilder encryptedMessage, int key) {
        key %= Math.PI*5;
        StringBuilder decryptedMessage = new StringBuilder();
        if (key == 0) {
            key = 2;
        }

        // Create a 2D character array with dimensions (key, encrypted message length / key)
        char[][] matrix = new char[key][(int) Math.ceil((double) encryptedMessage.length() / key)];
        int charIndex = 0;

        // Populate the matrix with the characters from the encrypted message
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (charIndex < encryptedMessage.length()) {
                    matrix[i][j] = encryptedMessage.charAt(charIndex++);
                } else {
                    matrix[i][j] = ' ';
                }
            }
        }

        // Read the decrypted message from the matrix column by column
        for (int i = 0; i < matrix[0].length; i++) {
            for (char[] chars : matrix) {
                decryptedMessage.append(chars[i]);
            }
        }

        return decryptedMessage;
    }
}
