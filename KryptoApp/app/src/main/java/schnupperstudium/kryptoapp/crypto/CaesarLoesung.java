package schnupperstudium.kryptoapp.crypto;

import android.util.Log;

import java.util.Random;

public class CaesarLoesung extends  Algorithm {
    @Override
    public String getName() {
        return "Cäsar Lösung";
    }

    @Override
    public String encrypt(String message, String key) {
        return shiftString(message, getShiftFormKey(key));
    }

    @Override
    public String decrypt(String cipher, String key) {
        return shiftString(cipher, (-1)*getShiftFormKey(key));
    }

    @Override
    public String generateRandomKey() {
        return getCharFromInt(getRandomNumber(1, 26), true) + "";
    }

    private int getShiftFormKey(String key) {
        return getNumberFromChar(key.charAt(0));
    }

    private String shiftString(String s, int shift) {
        String result = "";
        for(int i = 0; i < s.length(); i++){
            result+= shiftChar(s.charAt(i), shift);
        }
        return result;
    }

    private char shiftChar(char c, int shift) {
        if(isLowerCase(c)) { //Klein Buchstaben
            return getCharFromInt(getNumberFromChar(c) + shift, true);
        } else if(isUpperCase(c)) {// Groß Buchstaben
            return getCharFromInt(getNumberFromChar(c) + shift, false);
        } else { // Sonderzeichen etc
            return c;
        }
    }

    private boolean isUpperCase (char c) {
        if(c >= 65 && c <= 90) {
            return true;
        }
        return false;
    }

    private boolean isLowerCase (char c) {
        if(c >= 97 && c <= 122) {
            return true;
        }
        return false;
    }

    private char getCharFromInt(int i, boolean lowerCase){
        if(lowerCase) {
            return (char) ((((i - 1) % 26 + 26) % 26) + 97);
        } else {
            return  (char) ((((i - 1) % 26 + 26) % 26)+ 65);
        }
    }

    private int getNumberFromChar(char c) {
        if(isUpperCase(c)) {
            return  c - 64;
        } else if(isLowerCase(c)) {
            return c - 96;
        }
        return  0;
    }


    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min + 1)) + min;
    }
}
