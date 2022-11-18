import java.security.*;
import java.security.spec.ECGenParameterSpec;


public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;

        //Constructor
    public Wallet(){
        generateKeyPair();
    }

    //Generates public and private keys
    public void generateKeyPair(){
        try{
            KeyPairGenerator genKey = KeyPairGenerator.getInstance("ECDSA","BC" );
            SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            //Generating key pair
            genKey.initialize(ecSpec, rand);
            KeyPair keyPair = genKey.generateKeyPair();

            //Setting public and private keys
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
