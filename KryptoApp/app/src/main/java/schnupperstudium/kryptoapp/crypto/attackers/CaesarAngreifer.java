package schnupperstudium.kryptoapp.crypto.attackers;

import android.content.Context;
import android.util.Log;

import schnupperstudium.kryptoapp.crypto.algorithms.CaesarLoesung;
import schnupperstudium.kryptoapp.dictonary.Dictionary;

public class CaesarAngreifer  extends Attacker {

     private Dictionary dictionary;
     private String message;
     private String key;

    public CaesarAngreifer(Context context) {
        dictionary = new Dictionary(context);
    }

    @Override
    public String getName() {
        return "Cäsar Angreifer";
    }

    @Override
    public boolean tryDecrypt(String cipher) throws InterruptedException{
        CaesarLoesung caesarLoesung = new CaesarLoesung();
        char guessedKey = 'a';
        String result = "";
        while (guessedKey <= 'z' && !Thread.interrupted()) {
            result = caesarLoesung.decrypt(cipher, guessedKey +"");
            if(checkSolution(result)) {
                key = guessedKey + "";
                message = result;
                Log.d("Caesar", "Entschlüsselt");
                return true;
            }
            guessedKey++;
            Log.d("Caesar", "KEY. " + guessedKey);
        }

        return false;
    }

    @Override
    public boolean process(String message, String cipher) {
        if(message.length() < 1 || cipher.length() < 1) {
            return false;
        }
        key = ((cipher.charAt(0)- message.charAt(0)) + "");
        return true;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getKey() {
        return key;
    }

    private boolean checkSolution(String input) {
        int startIndex = -1;
        int endIndex;
        while((endIndex = input.indexOf(' ', startIndex +1)) != -1){
            Log.d("Caesar", "Testing: " + input.substring(startIndex +1, endIndex) + " Endindex: " + endIndex);
            if(dictionary.isInDictionary(input.substring(startIndex + 1, endIndex))){
                startIndex = endIndex;
                continue;
            }
            return false;
        }
        if(dictionary.isInDictionary(input.substring(startIndex +1))){
            Log.d("Caesar", "Testing: " + input.substring(startIndex +1, endIndex) + " Endindex: " + endIndex);
            return true;
        }
        return false;

    }

}
