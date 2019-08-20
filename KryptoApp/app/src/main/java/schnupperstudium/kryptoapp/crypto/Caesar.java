package schnupperstudium.kryptoapp.crypto;

public class Caesar extends Algorithm {
    @Override
    public String getName() {
        return "Cäsar";
    }

    @Override
    public String encrypt(String message, String key) {
        String result ="";
        for(int i = 0; i < message.length(); i++){
            result += (char)(message.charAt(i)+key.charAt(0));
        }
        return result;
    }

    @Override
    public String decrypt(String cipher, String key) {
        String result ="";
        for(int i = 0; i < cipher.length(); i++){
            result += (char)(cipher.charAt(i)-key.charAt(0));
        }
        return result;
    }

    @Override
    public String generateRandomKey() {
        return "a";
    }
}
