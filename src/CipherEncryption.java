import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

public class CipherEncryption {
    public static ArrayList<String> binaryMapper(String message) {
        ArrayList<String> binary = new ArrayList<>();

        for (int i = 0; i < message.length(); i++) {
            int ascii = message.charAt(i);
            String binaryString = Integer.toBinaryString(ascii);
            binary.add(binaryString);
        }

        return binary;
    }

    public static ArrayList<String> binaryShuffler(ArrayList<String> binary, int key) {
        ArrayList<String> shuffledBinary = new ArrayList<>();

        for (int i = 0; i < binary.size(); i++) {
            int index = (i + key) % binary.size();
            shuffledBinary.add(binary.get(index));
        }

        return shuffledBinary;
    }

    public static ArrayList<String> xorEncryption(ArrayList<String> shuffledBinary) {
        ArrayList<String> encryptedBinary = new ArrayList<>();

        for (String binaryString : shuffledBinary) {
            StringBuilder encryptedBinaryBuilder = new StringBuilder();

            for (int j = 0; j < binaryString.length(); j++) {
                if (binaryString.charAt(j) == '0') {
                    encryptedBinaryBuilder.append("1");
                } else {
                    encryptedBinaryBuilder.append("0");
                }
            }

            encryptedBinary.add(encryptedBinaryBuilder.toString());
        }

        return encryptedBinary;
    }

    public static ArrayList<String> shiftBinary(ArrayList<String> binaryStrings, int shiftBy) {
        ArrayList<String> shiftedStrings = new ArrayList<>();
        for (String binaryString : binaryStrings) {
            StringBuilder sb = new StringBuilder(binaryString);
            for (int i = 0; i < shiftBy; i++) {
                char firstChar = sb.charAt(0);
                sb.deleteCharAt(0);
                sb.append(firstChar);
            }
            shiftedStrings.add(sb.toString());
        }
        return shiftedStrings;
    }

    public static StringBuilder subCipher(ArrayList<String> shiftedBinary) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (String binaryString : shiftedBinary) {
            int ascii = Integer.parseInt(binaryString, 2);
            char character = (char) ascii;
            encryptedMessage.append(character);
        }

        return encryptedMessage;
    }

    public static StringBuilder transpositionCipher(StringBuilder message, int key) {
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

    public static StringBuilder keyXOREncryption(StringBuilder transposedMessage, int key) {
        StringBuilder finalMessage = new StringBuilder();

        for (int i = 0; i < transposedMessage.length(); i++) {
            int index = i % key;
            int ascii = transposedMessage.charAt(i) ^ key;
            char character = (char) ascii;
            finalMessage.append(character);
        }

        return finalMessage;
    }

    // Encrypt a message by applying modulo reduction
    public static StringBuilder moduloReduction(StringBuilder message, int key) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            int code = (int) c + key;
            int encryptedCode = code % 256; // reduce modulo 256
            char encryptedChar = (char) encryptedCode;
            ciphertext.append(encryptedChar);
        }
        return ciphertext;
    }

    public static StringBuilder base64Conversion(StringBuilder input) {
        byte[] bytes = input.toString().getBytes();
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new StringBuilder(new String(encodedBytes));
    }

    public static StringBuilder blockConversion(StringBuilder base64Message, int key) {
        StringBuilder blockMessage = new StringBuilder();

        for (int i = 0; i < base64Message.length(); i++) {
            int index = (i + key) % base64Message.length();
            blockMessage.append(base64Message.charAt(index));
        }

        return blockMessage;
    }

    public static StringBuilder onePadEncryption(StringBuilder blockMessage) {
        StringBuilder oneTimePadMessage = new StringBuilder();

        for (int i = 0; i < blockMessage.length(); i++) {
            int ascii = blockMessage.charAt(i) ^ 1;
            char character = (char) ascii;
            oneTimePadMessage.append(character);
        }

        return oneTimePadMessage;
    }
}
