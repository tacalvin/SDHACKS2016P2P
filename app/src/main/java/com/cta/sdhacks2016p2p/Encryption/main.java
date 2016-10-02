import java.lang.*;

public class main {

    public static void main(String[] args) {
        EncryptionClass outer = new EncryptionClass();

        EncryptionClass.KeyGeneration innerKey = outer.new KeyGeneration(10);

    }
}
