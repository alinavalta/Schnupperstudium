package schnupperstudium.kryptoapp.crypto.algorithms;

import schnupperstudium.kryptoapp.crypto.AlgorithmName;
import schnupperstudium.kryptoapp.crypto.KeyFormatException;

public abstract class Algorithm  implements AlgorithmName {
    public abstract String encrypt (String message, String key) throws KeyFormatException;
    public abstract String decrypt (String cipher, String key) throws KeyFormatException;
    public abstract String generateRandomKey ();

    @Override
    public String getSentKey(String key) throws KeyFormatException {
        return key;
    }
}
