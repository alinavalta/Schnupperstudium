package schnupperstudium.kryptoapp.crypto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RSA extends Algorithm {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String encrypt(String message, String key) {
        return null;
    }

    @Override
    public String decrypt(String cipher, String key) {
        return null;
    }

    @Override
    public String generateRandomKey() {
        return null;
    }

    public int[] keyFromString(String input) throws KeyFormatException{
        int result[] = new int[2];
        String strPattern= "((\\d+);(\\d+))";
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) {
            try {
                result[0] = Integer.parseInt(matcher.group(1));
                result[1] = Integer.parseInt(matcher.group(2));
            } catch (NumberFormatException e) {
                throw new KeyFormatException("Schlüssel Fromat stimmt nicht: " + input + " Erwartet: " + strPattern);
            }

        }
        return result;

    }
}
