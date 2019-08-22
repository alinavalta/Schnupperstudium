package schnupperstudium.kryptoapp.crypto.algorithms;

import schnupperstudium.kryptoapp.crypto.KeyFormatException;

public abstract class Algorithm {
    public abstract String getName();
    public abstract String encrypt (String message, String key) throws KeyFormatException;
    public abstract String decrypt (String cipher, String key) throws KeyFormatException;
    public abstract String generateRandomKey ();
}
