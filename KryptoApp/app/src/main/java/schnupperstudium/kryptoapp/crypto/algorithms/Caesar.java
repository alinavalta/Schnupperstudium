package schnupperstudium.kryptoapp.crypto.algorithms;

import java.util.Random;

import schnupperstudium.kryptoapp.crypto.KeyFormatException;

public class Caesar extends Algorithm {
    @Override
    public String getName() {
        //Name der in der Liste angezeigt werden soll. Muss unter allen Algorithmen und Angreifern eindeutig sein.
        return "Name XY";
    }

    /**
     * Verschlüsselt die Nachricht und gibt sie als String zurück
     * @param message Text im Nachrichtenfeld
     * @param key Text im Keyfeld
     * @return String der im Chiffratfeld angezeigt werden soll
     * @throws KeyFormatException Soll geworfen werden, wenn der Schlüssel nicht mit einem Buchstaben beginnt
     */
    @Override
    public String encrypt(String message, String key) throws KeyFormatException {
        return "Hier kommt das Chiffrat hin";
    }

    /**
     * Entschlüsselt das Ciffrat und gibt sie als String zurück
     * @param cipher Text im Chiffratfeld
     * @param key Text im Keyfeld
     * @return WString der im Nachrichten angezeigt werden soll
     * @throws KeyFormatException Soll geworfen werden, wenn der Schlüssel nicht mit einem Buchstaben beginnt
     */
    @Override
    public String decrypt(String cipher, String key) throws KeyFormatException {
        return "Hier kommt die Nachricht hin";
    }

    /**
     * Gibt ein zufälligen Schlüssel zurück
     * @return
     */
    @Override
    public String generateRandomKey() {
        return "zufälliger Schlüssel";
    }

    /**
     * Verschiebt jeden Buchstaben im String um eins (shiftString("abcd") gibt bcde zurück)
     * @param s
     * @return
     */
    private String shiftString(String s) {
        String result = "";
        //Variable i wird von 0 bis zum Länge von s - 1 hochgezählt
        for(int i = 0; i < s.length(); i++) {
            result += ""; // Den aktuellen Buchstaben um eins verschoben (Hinweis s.charAt(2) gibt den 3. Buchstaben zurück)
        }
        return result;
    }

    /**
     * Verschiebt jeden Buchstaben im String um eins rückwärts (shiftStringBackwards("bcde") gibt abcd zurück)
     * @param s
     * @return
     */
    private String shiftStringBackwards(String s) {
        return "";
    }

    /**
     * Verschiebt eine Buchstaben um eins (shiftChar('a') gibt b zurück shiftChar('z') gibt 'a' zurück )
     * @param c
     * @return
     */
    private char shiftChar(char c) {
        return c;
    }

    private char shiftCharBackwards(char c) {
        return c;
    }

    /**
     * Gibt an um wie viel verschoben werden soll getNumberFromKey('a') ist 1 getNumberFromKey('z') ist 26
     * @param key
     * @return
     * @throws KeyFormatException
     */
    private int getNumberFromKey(String key) throws KeyFormatException {

        throw new KeyFormatException("Kein gültiger Schlüssel"); //Wenn korrekter Schlüssel an gegeben wurde
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
