package schnupperstudium.kryptoapp.crypto;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import schnupperstudium.kryptoapp.crypto.algorithms.Algorithm;
import schnupperstudium.kryptoapp.crypto.attackers.Attacker;

public class SelectAlgorithm {

    private Algorithm current;
    private Attacker currentAttacker;
    private boolean currentIsAlgorithm = true;
    private Map<String, Algorithm> algorithmMap;
    private Map<String, Attacker> attackerMap;
    private  Algorithm getCurrent;

    /**
     * Initialisiert alle Algorithmen und fügt sie in die Liste ein
     * @param context Referenz auf die MainActivity wird für Angreifer benötigt, die auf das Dictionary zurgeifen wollen
     */
    public SelectAlgorithm(Context context){
        algorithmMap = new HashMap<>(); //Hier werden alle Algorithmeninstanzen gespeichert
        attackerMap = new HashMap<>(); //Hier werden alle Angreiferinstanzen gespeichert

        //Hier für jeden Algorithmus:
        //AlgorithmusXY xy = new AlgorithmusXY();
        //algorithmMap.put(xy.getName(), xy);

        //Hier für jeden Angreifer:
        //AngreiferXY xy = new AngreiferXY();
        //attackerMap.put(xy.getName(), xy);
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

    public AlgorithmName getAlgorithmName () throws IllegalArgumentException {
        if(isAlgorithm() && current != null) {
            return current;
        } else if(!isAlgorithm() && currentAttacker != null) {
            return currentAttacker;
        }
        throw new IllegalArgumentException("Es wurde noch kein Algorithmus/Angreifer gewählt");
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
