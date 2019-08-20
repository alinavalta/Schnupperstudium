package schnupperstudium.kryptoapp.crypto;

public abstract class Algorithm {
    public abstract String getName();
    public abstract String encrypt (String message, String key);
    public abstract String decrypt (String cipher, String key);
    public abstract String generateRandomKey ();
}
