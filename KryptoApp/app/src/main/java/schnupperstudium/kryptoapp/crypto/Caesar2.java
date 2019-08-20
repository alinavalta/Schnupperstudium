package schnupperstudium.kryptoapp.crypto;

public class Caesar2 {

    int key;
    public Caesar2(int k) {
        key = k;
    }

    public String encrypt(String text) {
        String result = "";

        for(int i = 0; i < text.length(); i++) {
            char index;
            if(text.charAt(i) < 'Z') { //Großbuchstaben
                index = 'A';
            } else {
                index = 'a';
            }
            result+= (char)(((text.charAt(i) + key) - index) % 26 + index);

        }
        return result;
    }

    public String decrypt(String code) {
        String result = "";

        for(int i = 0; i < code.length(); i++) {
            char index;
            if(code.charAt(i) < 'Z') { //Großbuchstaben
                index = 'A';
            } else {
                index = 'a';
            }
            result+= ((code.charAt(i) - key) - index) % 26 + index;

        }
        return result;
    }
}
