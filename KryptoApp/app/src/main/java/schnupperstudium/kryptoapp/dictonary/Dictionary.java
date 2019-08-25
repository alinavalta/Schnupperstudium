package schnupperstudium.kryptoapp.dictonary;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import schnupperstudium.kryptoapp.R;

public class Dictionary {
    private Context context;

    public Dictionary(Context context) {
        this.context = context;
    }

    public boolean isInDictionary(String input) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.german);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if(line.equalsIgnoreCase(input)) {
                    inputStream.close();
                    return true;
                }
                if(line.compareToIgnoreCase(input) == 1) {
                    inputStream.close();
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPrefix(String input){
        InputStream inputStream = context.getResources().openRawResource(R.raw.german);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if(line.toLowerCase().startsWith(input.toLowerCase())) {
                    return true;
                }
                if(line.compareToIgnoreCase(input) == 1) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
