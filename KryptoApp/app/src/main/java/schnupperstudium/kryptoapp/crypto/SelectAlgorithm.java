package schnupperstudium.kryptoapp.crypto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import schnupperstudium.kryptoapp.crypto.Algorithm;
import schnupperstudium.kryptoapp.crypto.Caesar;

public class SelectAlgorithm {

    private  Algorithm current;
    private Map<String, Algorithm> algorithmMap;
    private  Algorithm getCurrent;

    public SelectAlgorithm(){
        algorithmMap = new HashMap<>();
        Caesar caesar = new Caesar();
        algorithmMap.put(caesar.getName(), caesar);
    }

    public Algorithm getAlgorithm() throws IllegalArgumentException {
        if(current == null) {
            throw new IllegalArgumentException("Es wurden noch kein Algorithmus ausgewählt");
        }
        return current;
    }

    public void selectAlgorithm (String name) throws IllegalArgumentException {
        if(!algorithmMap.containsKey(name)) {
            throw new IllegalArgumentException("Kein Algorithmus mit diesem Namen vorhanden");
        }
        current = algorithmMap.get(name);
    }

    public ArrayList<String> getAlgorithms(){
        ArrayList<String> result = new ArrayList<>();
        for(String s : algorithmMap.keySet()){
            result.add(s);
        }
        return result;
    }
}
