package schnupperstudium.kryptoapp.crypto.attackers;

import schnupperstudium.kryptoapp.crypto.AlgorithmName;

public abstract class Attacker implements AlgorithmName {

    public abstract boolean tryDecrypt(String cipher);
    public abstract boolean process(String message, String cipher);
    public abstract String getMessage();
    public abstract String getKey();
}
