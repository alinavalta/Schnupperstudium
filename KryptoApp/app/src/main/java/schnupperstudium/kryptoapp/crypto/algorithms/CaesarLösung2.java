package schnupperstudium.kryptoapp.crypto.algorithms;

import java.util.Random;

import schnupperstudium.kryptoapp.crypto.KeyFormatException;

public class CaesarLösung2 extends Algorithm {
    @Override
    public String getName() {
        return "Cäsar";
    }

    @Override
    public String encrypt(String message, String key) throws KeyFormatException {
        int shift = getNumberFromKey(key);
        for(int i = 0; i < shift; i++) {
            message = shiftString(message);
        }
        return message;
    }

    @Override
    public String decrypt(String cipher, String key) throws KeyFormatException {
        int shift = getNumberFromKey(key);
        for(int i = 0; i < shift; i++) {
            cipher = shiftStringBackwards(cipher);
        }
        return cipher;
    }

    @Override
    public String generateRandomKey() {
        return (char)((getRandomNumber(1, 26) - 1) + 'a') + "";
    }

    private String shiftString(String s) {
        String result = "";
        for(int i = 0; i < s.length(); i++) {
            result += shiftChar(s.charAt(i));
        }
        return result;
    }

    private String shiftStringBackwards(String s) {
        String result = "";
        for(int i = 0; i < s.length(); i++) {
            result += shiftCharBackwards(s.charAt(i));
        }
        return result;
    }

    private char shiftChar(char c) {
        if(c >= 'a' && c <= 'z') { //Klein Buchstaben
            if(c  == 'z') {
                return 'a';
            }
            return (char) (c + 1);
        } else if(c >= 'A' && c <= 'Z') {// Groß Buchstaben
            if(c  == 'Z') {
                return 'A';
            }
            return (char) (c + 1);
        } else { // Sonderzeichen etc
            return c;
        }
    }

    private char shiftCharBackwards(char c) {
        if(c >= 'a' && c <= 'z') { //Klein Buchstaben
            if(c  == 'a') {
                return 'z';
            }
            return (char) (c - 1);
        } else if(c >= 'A' && c <= 'Z') {// Groß Buchstaben
            if(c  == 'A') {
                return 'Z';
            }
            return (char) (c - 1);
        } else { // Sonderzeichen etc
            return c;
        }
    }
    private int getNumberFromKey(String key) throws KeyFormatException {
        char c = key.charAt(0);
        if(c >= 'A' && c <= 'Z') {
            return  c - 64;
        } else if(c >= 'a' && c <= 'z') {
            return c - 96;
        }
        throw new KeyFormatException("kein gültiger Schlüssel");
    }


    /**
     * Gibt eine Zufällige Zahl zwischen min und max zurück
     * @param min minimaler Zahl die zurückgegeben wird
     * @param max Maximale Zahl die zurückgegeben wird
     * @return
     */
    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min + 1)) + min;
    }
}
