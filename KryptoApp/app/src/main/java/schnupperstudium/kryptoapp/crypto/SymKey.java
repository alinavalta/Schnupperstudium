package schnupperstudium.kryptoapp.crypto;

public abstract class SymKey implements Key {

    private String key;

    @Override
    public String getDecKey() {
        return key;
    }

    @Override
    public String getEncKey() {
        return key;
    }

    public void setKey (String key){
        this.key = key;
    }
}
