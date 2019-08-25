package schnupperstudium.kryptoapp.crypto;

public interface AlgorithmName {
    public String getName();
    public String getSentKey(String key) throws KeyFormatException;
}
