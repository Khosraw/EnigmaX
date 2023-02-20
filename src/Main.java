import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // ask if the user wants to encrypt or decrypt a message
        Scanner sc = new Scanner(System.in);

        System.out.println("Would you like to encrypt or decrypt a message?");
        String answer = sc.nextLine();

        if (answer.equals("encrypt")) {
            System.out.println("Enter a message to encrypt:");
            String message = sc.nextLine();
            System.out.println("Enter a key:");
            int key = sc.nextInt();

            String encryptedMessage = encrypt(message, key);

            System.out.println("Your encrypted message is:");
            System.out.println(encryptedMessage);
        } else if (answer.equals("decrypt")) {
            System.out.println("Enter a message to decrypt:");
            String message = sc.nextLine();
            System.out.println("Enter a key:");
            int key = sc.nextInt();

            System.out.println("Your decrypted message is:");
            System.out.println(decrypt(message, key));
        } else {
            System.out.println("Please enter a valid answer.");
        }
    }

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

    public static ArrayList<String> shiftBinary(ArrayList<String> encryptedBinary) {
        ArrayList<String> shiftedBinary = new ArrayList<>();

        for (String binaryString : encryptedBinary) {
            StringBuilder shiftedBinaryString = new StringBuilder();

            for (int j = 0; j < binaryString.length(); j++) {
                if (j % 2 == 0) {
                    shiftedBinaryString.append(binaryString.charAt(j));
                } else {
                    shiftedBinaryString.insert(0, binaryString.charAt(j));
                }
            }

            shiftedBinary.add(shiftedBinaryString.toString());
        }

        return shiftedBinary;
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

    public static StringBuilder transpositionCipher(StringBuilder encryptedMessage, int key) {
        StringBuilder transposedMessage = new StringBuilder();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            int index = (i + key) % encryptedMessage.length();
            transposedMessage.append(encryptedMessage.charAt(index));
        }

        return transposedMessage;
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

    public static StringBuilder moduloReduction(StringBuilder finalMessage, BigInteger prime) {
        StringBuilder reducedMessage = new StringBuilder();

        for (int i = 0; i < finalMessage.length(); i++) {
            int ascii = finalMessage.charAt(i) % prime.intValue();
            char character = (char) ascii;
            reducedMessage.append(character);
        }

        return reducedMessage;
    }

    public static StringBuilder base64Conversion(StringBuilder reducedMessage) {
        StringBuilder base64Message = new StringBuilder();

        for (int i = 0; i < reducedMessage.length(); i++) {
            int ascii = reducedMessage.charAt(i);
            String base64String = Integer.toString(ascii, 64);
            base64Message.append(base64String);
        }

        return base64Message;
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

    public static String encrypt(String message, int key) throws Exception {
        // map each character to unique binary code
        ArrayList<String> binary = binaryMapper(message);

        // shuffle binary code sequence randomly based on the key using a permutation algorithm
        ArrayList<String> shuffledBinary = binaryShuffler(binary, key);

        // XOR encrypt the shuffled binary code sequence using the key
        ArrayList<String> encryptedBinary = xorEncryption(shuffledBinary);

        // bitwise shift the encrypted binary code sequence by a random number of bits
        ArrayList<String> shiftedBinary = shiftBinary(encryptedBinary);

        // use substitution cipher to map each binary code to a character
        StringBuilder encryptedMessage = subCipher(shiftedBinary);

        // substituted sequence is then reversed, so that the last symbol becomes the first, the second-last symbol becomes the second, and so on
        encryptedMessage.reverse();

        // transposition cipher is applied to the reversed sequence, rearranging the symbols in a predetermined way
        StringBuilder transposedMessage = transpositionCipher(encryptedMessage, key);

        // transposed sequence is then XOR encrypted again, but this time using a repeating key that is generated by repeating the original encryption key in a pattern
        StringBuilder finalMessage = keyXOREncryption(transposedMessage, key);

        // encrypted sequence is then reduced modulo a random prime number to produce a new sequence
        BigInteger prime = PrimeGenerator.generatePrimeWithKey(key);
        System.out.println("Your new decryption key is: " + prime + " (prime number)");

        StringBuilder reducedMessage = moduloReduction(finalMessage, prime);

        // new sequence is then converted to a different number base, such as base 64, using a custom conversion scheme
        StringBuilder base64Message = base64Conversion(reducedMessage);

        // sequence is then broken into blocks of a fixed length, and each block is substituted using a different substitution cipher
        StringBuilder blockMessage = blockConversion(base64Message, key);

        // substituted blocks are then encrypted using a keyless encryption algorithm, such as the one-time pad
        StringBuilder oneTimePadMessage = onePadEncryption(blockMessage);

        // sequence is encoded using a standard encoding scheme, such as UTF-8, to produce the encrypted message
        return oneTimePadMessage.toString();
    }

    public static StringBuilder reverseOnePadEncryption(StringBuilder oneTimePadMessage) {
        StringBuilder blockMessage = new StringBuilder();

        for (int i = 0; i < oneTimePadMessage.length(); i++) {
            int ascii = oneTimePadMessage.charAt(i) ^ 1;
            char character = (char) ascii;
            blockMessage.append(character);
        }

        return blockMessage;
    }

    public static StringBuilder reverseBlockConversion(StringBuilder blockMessage, int key) {
        // Reverse step 1: Initialize an empty StringBuilder object
        StringBuilder reversedBase64Message = new StringBuilder();

        // Reverse step 2: Iterate over the characters of blockMessage in reverse order,
        // applying the inverse operation to the characters to obtain the original base64 string
        for (int i = blockMessage.length() - 1; i >= 0; i--) {
            int index = (i + key) % blockMessage.length();
            char base64Char = blockMessage.charAt(index);
            reversedBase64Message.append(base64Char);
        }

        // Reverse step 4: Initialize an empty StringBuilder object
        StringBuilder originalBase64Message = new StringBuilder();

        // Reverse step 5: Reverse the reversedBase64Message object to obtain the original base64 string
        String base64String = reversedBase64Message.reverse().toString();

        // Reverse step 6: Iterate over the characters of the originalBase64Message object in reverse order,
        // applying the inverse operation to the characters to obtain the original sequence of characters
        for (int i = base64String.length() - 1; i >= 0; i--) {
            int ascii = Integer.parseInt(String.valueOf(base64String.charAt(i)), 64);
            char character = (char) ascii;
            originalBase64Message.append(character);
        }

        // Reverse step 8: Return the originalBlockMessage object
        return originalBase64Message;
    }

    public static String decrypt(String message, int key) {
        return "";
    }
}