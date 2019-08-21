package schnupperstudium.kryptoapp.crypto;

public class KeyFormatException extends Exception {


    private String message = "Format des Schlüssels stimmt nicht.";

    public KeyFormatException() {
        super();
    }

    public KeyFormatException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
