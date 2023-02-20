import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CustomRandomNumberGenerator {
    private final SecureRandom secureRandom;
    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    public CustomRandomNumberGenerator(byte[] key) throws Exception {
        secureRandom = new SecureRandom(key);
        byte[] aesKey = new byte[16];
        System.arraycopy(key, 0, aesKey, 0, Math.min(key.length, 16));
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey, "AES");
        encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
    }

    public byte nextByte() {
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        byte[] encryptedBytes = encryptCipher.update(randomBytes);
        return encryptedBytes[0];
    }

    public byte[] nextBytes(int numBytes) {
        byte[] bytes = new byte[numBytes];
        for (int i = 0; i < numBytes; i++) {
            bytes[i] = nextByte();
        }
        return bytes;
    }

    public byte[] reverseBytes(byte[] encryptedBytes) throws Exception {
        return decryptCipher.update(encryptedBytes);
    }

    public int nextInt() {
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        byte[] encryptedBytes = encryptCipher.update(randomBytes);
        int result = 0;

        for (int i = 0; i < 16; i++) {
            result = (result << 8) + (encryptedBytes[i] & 0xff);
        }

        return result;
    }

    public int[] nextInts(int numInts) {
        int[] ints = new int[numInts];

        for (int i = 0; i < numInts; i++) {
            ints[i] = nextInt();
        }

        return ints;
    }

    public long nextLong() {
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        byte[] encryptedBytes = encryptCipher.update(randomBytes);
        long result = 0;

        for (int i = 0; i < 16; i++) {
            result = (result << 8) + (encryptedBytes[i] & 0xff);
        }

        return result;
    }

    public long[] nextLongs(int numLongs) {
        long[] longs = new long[numLongs];

        for (int i = 0; i < numLongs; i++) {
            longs[i] = nextLong();
        }

        return longs;
    }
}