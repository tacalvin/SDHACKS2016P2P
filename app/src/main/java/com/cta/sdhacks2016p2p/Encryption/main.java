import java.lang.*;
import java.math.*;
import java.util.ArrayList;
import java.util.Arrays;

public class main {

    public static void main(String[] args) {
        EncryptionClass outer = new EncryptionClass();

        EncryptionClass.KeyGeneration innerKey = outer.new KeyGeneration(30);

        ArrayList<BigInteger> privateKey = new ArrayList<BigInteger>();
        ArrayList<BigInteger> publicKey = new ArrayList<BigInteger>();

        innerKey.createKey(privateKey, publicKey);
        innerKey.printKeys();
    }
}
