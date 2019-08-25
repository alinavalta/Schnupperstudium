package schnupperstudium.kryptoapp.crypto.algorithms;

import android.util.Log;

import java.math.BigInteger;
import java.security.Key;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import schnupperstudium.kryptoapp.crypto.KeyFormatException;

public class RSA extends Algorithm {

    private static final int BIT_LENTH = 2048;

    @Override
    public String getName() {
        return "RSA";
    }

    @Override
    public String getSentKey(String key) throws KeyFormatException {
        int result[] = keyFromString(key);
        if(result.length == 2){
            return key;
        } else if (result.length == 4) {
            return result[2] + ";" + result[3];
        }
        throw new KeyFormatException("Schlüssel Fromat stimmt nicht");
    }

    @Override
    public String encrypt(String message, String key) throws KeyFormatException {
        BigInteger m = new BigInteger(message, Character.MAX_RADIX);
        int sk[] = keyFromString(key);
        BigInteger n = BigInteger.valueOf(sk[0]);
        BigInteger e = BigInteger.valueOf(sk[1]);
        return m.modPow(e,n).toString();
    }

    @Override
    public String decrypt(String cipher, String key) throws KeyFormatException {
        BigInteger c = new BigInteger(cipher, Character.MAX_RADIX);
        int pk[] = keyFromString(key);
        BigInteger n;
        BigInteger d;
        if(pk.length == 4) {
            n = BigInteger.valueOf(pk[2]);
            d = BigInteger.valueOf(pk[3]);
        } else{
            n = BigInteger.valueOf(pk[0]);
            d = BigInteger.valueOf(pk[1]);
        }
        return c.modPow(d, n).toString();

    }

    @Override
    public String generateRandomKey() {
        Random rand = new Random();
        BigInteger p = BigInteger.probablePrime(BIT_LENTH /2,rand);
        BigInteger q = BigInteger.probablePrime(BIT_LENTH/2, rand);

        BigInteger n = q.multiply(p);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e;
        do {
            e = new BigInteger(phi.bitLength(), rand);
        } while (e.compareTo(BigInteger.ONE) <= 0 ||
            e.compareTo(phi) >= 0 ||! e.gcd(phi).equals(BigInteger.ONE));

        BigInteger d = e.modInverse(phi);
        return "e;n,d;n";
    }

    public int[] keyFromString(String input) throws KeyFormatException{
        int result[] = new int[2];
        Log.d("RSA", "INpUT " + input);
        String strPattern= "(\\d+);(\\d+)";
        String patternSkPk = "(\\d+);(\\d+);(\\d+);(\\d+)";
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(input);
        Matcher matcher2 = Pattern.compile(patternSkPk).matcher(input);
        if(matcher.matches()) {
            Log.d("RSA", "Matches");
            try {
                result[0] = Integer.parseInt(matcher.group(1));
                result[1] = Integer.parseInt(matcher.group(2));
            } catch (NumberFormatException e) {
                throw new KeyFormatException("Schlüssel Fromat stimmt nicht: " + input + " Erwartet: " + strPattern);
            }
        }else if(matcher2.matches()) {
            Log.d("RSA", "Matches2 ");
            try {
                result = new int[4];
                result[0] = Integer.parseInt(matcher2.group(1));
                result[1] = Integer.parseInt(matcher2.group(2));
                result[2] = Integer.parseInt(matcher2.group(3));
                result[3] = Integer.parseInt(matcher2.group(4));
            } catch (NumberFormatException e) {
                throw new KeyFormatException("Schlüssel Fromat stimmt nicht: " + input + " Erwartet: " + patternSkPk);
            }
        } else {
            throw new KeyFormatException("Schlüssel Fromat stimmt nicht: " + input + " Erwartet: " + strPattern);
        }
        return result;

    }
}
