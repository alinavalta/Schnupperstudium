package schnupperstudium.kryptoapp.crypto;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import schnupperstudium.kryptoapp.crypto.algorithms.Algorithm;
import schnupperstudium.kryptoapp.crypto.algorithms.Caesar;
import schnupperstudium.kryptoapp.crypto.attackers.Attacker;
import schnupperstudium.kryptoapp.crypto.attackers.CaesarAngreifer;
import schnupperstudium.kryptoapp.crypto.algorithms.CaesarLoesung;
import schnupperstudium.kryptoapp.crypto.algorithms.RSA;

public class SelectAlgorithm {

    private Algorithm current;
    private Attacker currentAttacker;
    private boolean currentIsAlgorithm = true;
    private Map<String, Algorithm> algorithmMap;
    private Map<String, Attacker> attackerMap;
    private  Algorithm getCurrent;

    public SelectAlgorithm(Context context){
        algorithmMap = new HashMap<>();
        attackerMap = new HashMap<>();
        Caesar caesar = new Caesar();
        algorithmMap.put(caesar.getName(), caesar);

        CaesarLoesung caesarLoesung = new CaesarLoesung();
        algorithmMap.put(caesarLoesung.getName(), caesarLoesung);

        CaesarAngreifer caesarAngreifer = new CaesarAngreifer(context);
        attackerMap.put(caesarAngreifer.getName(), caesarAngreifer);

        RSA rsa = new RSA();
        algorithmMap.put(rsa.getName(), rsa);
    }

    public Algorithm getAlgorithm() throws IllegalArgumentException {
        if(current == null) {
            throw new IllegalArgumentException("Es wurden noch kein Algorithmus ausgewählt");
        }
        return current;
    }

    public Attacker getAttacker() throws IllegalArgumentException {
        if(currentAttacker == null) {
            throw  new IllegalArgumentException("Es wurde noch kein Angreifer ausgewählt");
        }
        return currentAttacker;
    }

    public boolean isAlgorithm() {
        return currentIsAlgorithm;
    }

    public void selectAlgorithm (String name) throws IllegalArgumentException {
        if(!algorithmMap.containsKey(name)) {
            if(!attackerMap.containsKey(name)) {
                throw new IllegalArgumentException("Kein Algorithmus/Angreifer mit diesem Namen vorhanden");
            }
            currentAttacker = attackerMap.get(name);
            currentIsAlgorithm = false;
            return;
        }
        current = algorithmMap.get(name);
        currentIsAlgorithm = true;
    }

    public ArrayList<String> getNameList(){
        ArrayList<String> result = new ArrayList<>();
        for(String s : algorithmMap.keySet()){
            result.add(s);
        }
        for(String s: attackerMap.keySet()) {
            result.add(s);
        }
        return result;
    }
}
