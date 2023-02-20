import java.math.BigInteger;

public class PrimeGenerator {

    public static BigInteger generatePrimeWithKey(long key) throws Exception {
        CustomRandomNumberGenerator customRandomNumberGenerator = new CustomRandomNumberGenerator(new byte[]{(byte) key});
        byte randomRadix = (byte) (Math.abs(customRandomNumberGenerator.nextByte()) % 35);
        BigInteger primeCandidate = new BigInteger(String.valueOf(1010), randomRadix);
        while (!primeCandidate.isProbablePrime(100)) {
            primeCandidate = new BigInteger(String.valueOf(1010), randomRadix);

            primeCandidate = primeCandidate.nextProbablePrime();
        }
        return primeCandidate;
    }

//    public static long reversePrimeWithKey(long key) throws Exception {
//        CustomRandomNumberGenerator customRandomNumberGenerator = new CustomRandomNumberGenerator(new byte[]{(byte) key});
//        byte[] random = customRandomNumberGenerator.reverse(new byte[]{(byte) key});
//        BigInteger primeCandidate = BigInteger.ZERO;
//        while (!primeCandidate.equals(prime)) {
//            key++;
//            primeCandidate = generatePrimeWithKey(key);
//        }
//        return key;
//    }
}