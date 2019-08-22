package schnupperstudium.kryptoapp.crypto.attackers;

import android.content.Context;
import android.util.Log;

import schnupperstudium.kryptoapp.crypto.algorithms.CaesarLoesung;
import schnupperstudium.kryptoapp.dictonary.Dictornary;

public class CaesarAngreifer  extends Attacker {

     private Dictornary dictornary;
     private String message;
     private String key;

    public CaesarAngreifer(Context context) {
        dictornary = new Dictornary(context);
    }

    @Override
    public String getName() {
        return "Cäsar Angreifer";
    }

    @Override
    public boolean tryDecrypt(String cipher) {
        CaesarLoesung caesarLoesung = new CaesarLoesung();
        char guessedKey = 'a';
        String result = "";
        while (guessedKey <= 'z') {
            result = caesarLoesung.decrypt(cipher, guessedKey +"");
            if(checkSolution(result)) {
                key = guessedKey + "";
                message = result;
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
            if(dictornary.isInDictornary(input.substring(startIndex + 1, endIndex))){
                startIndex = endIndex;
                continue;
            }
            return false;
        }
        if(dictornary.isInDictornary(input.substring(startIndex +1))){
            return true;
        }
        return false;

    }

}
