package schnupperstudium.kryptoapp.crypto.attackers;

import schnupperstudium.kryptoapp.crypto.AlgorithmName;
import schnupperstudium.kryptoapp.crypto.KeyFormatException;

public abstract class Attacker implements AlgorithmName {

    public abstract boolean tryDecrypt(String cipher) throws InterruptedException;
    public abstract boolean process(String message, String cipher);
    public abstract String getMessage();
    public abstract String getKey();

    @Override
    public String getSentKey(String key) throws KeyFormatException {
        return key;
    }
}
