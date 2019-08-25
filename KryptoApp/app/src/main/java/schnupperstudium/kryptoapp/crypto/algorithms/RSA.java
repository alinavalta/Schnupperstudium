package schnupperstudium.kryptoapp.crypto.algorithms;

import android.util.Log;

import java.math.BigInteger;
import java.security.Key;
import java.security.SecureRandom;
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
        BigInteger result[] = keyFromString(key);
        if(result.length == 2){
            return key;
        } else if (result.length == 4) {
            return result[0] + ";" + result[1];
        }
        throw new KeyFormatException("Schlüssel Fromat stimmt nicht");
    }

    @Override
    public String encrypt(String message, String key) throws KeyFormatException {
        BigInteger m = new BigInteger(message, Character.MAX_RADIX);
        BigInteger sk[] = keyFromString(key);
        BigInteger n = sk[0];
        BigInteger e = sk[1];
        return m.modPow(e,n).toString(Character.MAX_RADIX);
    }

    @Override
    public String decrypt(String cipher, String key) throws KeyFormatException {
        BigInteger c = new BigInteger(cipher, Character.MAX_RADIX);
        BigInteger pk[] = keyFromString(key);
        BigInteger n;
        BigInteger d;
        if(pk.length ==2) {
            n = pk[1];
            d = pk[0];
        } else if(pk.length == 4) {
            n = pk[3];
            d = pk[2];
        } else {
            throw new KeyFormatException("Key Fromat fehlerhaft");
        }
        return (c.modPow(d, n).toString(Character.MAX_RADIX));

    }

    @Override
    public String generateRandomKey() {
        Random rand = new SecureRandom();
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
        return e +";" + n + ";" + d + ";" +n;
    }

    public BigInteger[] keyFromString(String input) throws KeyFormatException{
        BigInteger result[] = new BigInteger[2];
        Log.d("RSA", "INpUT " + input);
        String strPattern= "(\\d+);(\\d+)";
        String patternSkPk = "(\\d+);(\\d+);(\\d+);(\\d+)";
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(input);
        Matcher matcher2 = Pattern.compile(patternSkPk).matcher(input);
        if(matcher.matches()) {
            Log.d("RSA", "Matches");
            try {
                result[0] = new BigInteger(matcher.group(1));
                result[1] = new BigInteger(matcher.group(2));
            } catch (NumberFormatException e) {
                throw new KeyFormatException("Schlüssel Fromat stimmt nicht: " + input + " Erwartet: " + strPattern);
            }
        }else if(matcher2.matches()) {
            Log.d("RSA", "Matches2 ");
            try {
                result = new BigInteger[4];
                result[0] = new BigInteger(matcher2.group(1));
                result[1] = new BigInteger(matcher2.group(2));
                result[2] = new BigInteger(matcher2.group(3));
                result[3] = new BigInteger(matcher2.group(4));
            } catch (NumberFormatException e) {
                throw new KeyFormatException("Schlüssel Fromat stimmt nicht: " + input + " Erwartet: " + patternSkPk);
            }
        } else {
            throw new KeyFormatException("Schlüssel Fromat stimmt nicht: " + input + " Erwartet: " + strPattern);
        }
        return result;

    }
}
