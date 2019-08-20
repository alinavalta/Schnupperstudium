package schnupperstudium.kryptoapp.crypto;

public abstract class AsymKey implements Key {

    private String sk;
    private String pk;

    @Override
    public String getDecKey() {
        return sk;
    }

    @Override
    public String getEncKey() {
        return pk;
    }

    public void setSecretKey (String key){
        this.sk = key;
    }
    public void setPublicKey (String key) {this.pk = key;}
}
