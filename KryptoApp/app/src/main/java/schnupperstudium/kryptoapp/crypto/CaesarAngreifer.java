package schnupperstudium.kryptoapp.crypto;

import android.content.Context;
import android.util.Log;

import schnupperstudium.kryptoapp.dictonary.Dictornary;

public class CaesarAngreifer  extends Algorithm {

     private Dictornary dictornary;

    public CaesarAngreifer(Context context) {
        dictornary = new Dictornary(context);
    }

    @Override
    public String getName() {
        return "Cäsar Angreifer";
    }

    @Override
    public String encrypt(String message, String key) {
        return message;
    }

    @Override
    public String decrypt(String cipher, String key) {
        CaesarLoesung caesarLoesung = new CaesarLoesung();
        char guessedKey = 'a';
        String result = "";
        while (guessedKey <= 'z') {
            result = caesarLoesung.decrypt(cipher, guessedKey +"");
            if(checkSolution(result)) {
                return result;
            }
            guessedKey++;
            Log.d("Caesar", "KEY. " + guessedKey);
        }


        return null;
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

    @Override
    public String generateRandomKey() {
        return "";
    }
}
