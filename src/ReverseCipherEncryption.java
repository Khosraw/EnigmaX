import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ReverseCipherEncryption {
    private long key;
    private BigInteger primeKey;

    public ReverseCipherEncryption(long key, BigInteger primeKey) throws Exception {
        this.key = key;
        this.primeKey = primeKey;
    }

    public long getKey() {
        return key;
    }

    public BigInteger getPrimeKey() {
        return primeKey;
    }

    public static StringBuilder reverseOnePadEncryption(StringBuilder encryptedMessage) {
        StringBuilder originalMessage = new StringBuilder();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            int ascii = encryptedMessage.charAt(i) ^ 1;
            char character = (char) ascii;
            originalMessage.append(character);
        }

        return originalMessage;
    }

    public static StringBuilder reverseBlockConversion(StringBuilder blockMessage, int key) {
        StringBuilder base64Message = new StringBuilder();
        int len = blockMessage.length();
        int[] indices = new int[len];
        for (int i = 0; i < len; i++) {
            indices[(i + key) % len] = i;
        }
        for (int i = 0; i < len; i++) {
            base64Message.append(blockMessage.charAt(indices[i]));
        }
        return base64Message;
    }

    public static String reverseBase64Conversion(String base64String) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        return new String(decodedBytes);
    }

    // Decrypt a ciphertext by reversing modulo reduction
    public static StringBuilder reverseModuloReduction(StringBuilder ciphertext, int key) {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            int code = (int) c - key;
            int decryptedCode = (code + 256) % 256; // reverse reduction modulo 256
            char decryptedChar = (char) decryptedCode;
            message.append(decryptedChar);
        }
        return message;
    }

    public static StringBuilder reverseKeyXOREncryption(StringBuilder finalMessage, int key) {
        StringBuilder transposedMessage = new StringBuilder();

        for (int i = 0; i < finalMessage.length(); i++) {
            int ascii = finalMessage.charAt(i) ^ key;
            char character = (char) ascii;
            transposedMessage.append(character);
        }

        return transposedMessage;
    }

    public static StringBuilder reverseTranspositionCipher(StringBuilder encryptedMessage, int key) {
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

    public static ArrayList<String> reverseSubCipher(StringBuilder encryptedMessage) {
        ArrayList<String> shiftedBinary = new ArrayList<>();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            int ascii = encryptedMessage.charAt(i);
            String binaryString = Integer.toBinaryString(ascii);
            shiftedBinary.add(binaryString);
        }

        // pad to 7 bits if necessary
        for (int i = 0; i < shiftedBinary.size(); i++) {
            String binaryString = shiftedBinary.get(i);
            if (binaryString.length() < 7) {
                StringBuilder sb = new StringBuilder(binaryString);
                while (sb.length() < 7) {
                    sb.insert(0, "0");
                }
                shiftedBinary.set(i, sb.toString());
            }
        }

        return shiftedBinary;
    }

    public static ArrayList<String> reverseShiftBinary(ArrayList<String> shiftedStrings, int shiftBy) {
        ArrayList<String> originalStrings = new ArrayList<>();
        for (String shiftedString : shiftedStrings) {
            StringBuilder sb = new StringBuilder(shiftedString);
            for (int i = 0; i < shiftBy; i++) {
                char lastChar = sb.charAt(sb.length()-1);
                sb.deleteCharAt(sb.length()-1);
                sb.insert(0, lastChar);
            }
            originalStrings.add(sb.toString());
        }
        return originalStrings;
    }

    public static ArrayList<String> reverseXorEncryption(ArrayList<String> encryptedBinary) {
        ArrayList<String> shuffledBinary = new ArrayList<>();

        for (String binaryString : encryptedBinary) {
            StringBuilder shuffledBinaryBuilder = new StringBuilder();

            for (int j = 0; j < binaryString.length(); j++) {
                if (binaryString.charAt(j) == '0') {
                    shuffledBinaryBuilder.append("1");
                } else {
                    shuffledBinaryBuilder.append("0");
                }
            }

            shuffledBinary.add(shuffledBinaryBuilder.toString());
        }

        return shuffledBinary;
    }

//    public static ArrayList<String> reverseBinaryShuffler(ArrayList<String> shuffledBinary, int key) {
//        ArrayList<String> binary = new ArrayList<>();
//
//        for (int i = 0; i < shuffledBinary.size(); i++) {
//            int index = (i - key) % shuffledBinary.size();
//            if (index < 0) {
//                index += shuffledBinary.size();
//            }
//            binary.add(index, shuffledBinary.get(i));
//        }
//
//        return binary;
//    }

    public static ArrayList<String> reverseBinaryShuffler(ArrayList<String> shuffledBinary, int key) {
        ArrayList<String> binary = new ArrayList<>(Collections.nCopies(shuffledBinary.size(), null));

        for (int i = 0; i < shuffledBinary.size(); i++) {
            int index = (i - key) % shuffledBinary.size();
            if (index < 0) {
                index += shuffledBinary.size();
            }
            binary.set(index, shuffledBinary.get(i));
        }

        return binary;
    }

    public static String binaryToString(ArrayList<String> binary) {
        StringBuilder message = new StringBuilder();

        for (String binaryString : binary) {
            int ascii = Integer.parseInt(binaryString, 2);
            char character = (char) ascii;
            message.append(character);
        }

        return message.toString();
    }
}
