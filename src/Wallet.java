import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

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

    //Returns the balances and stores User owned UTXOs
    public float getBalance(){
        float total = 0;
        for(Map.Entry<String, TransactionOutput> item: BasicCoin.UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            if(UTXO.isMine(publicKey)){
                UTXOs.put(UTXO.id, UTXO);
                total += UTXO.value;
            }
        }
        return total;
    }

    //Generates a new transaction from the wallet
    public Transaction sendFunds(PublicKey _recipient, float value){
        if(getBalance() < value){//not enough funds
            System.out.println("#Not enough funds to send transactions. Transaction rejected");
            return null;
        }
        //list of inputs
        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
        float total = 0;
        for(Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if(total > value) break;
        }
        Transaction newTransaction = new Transaction(publicKey, _recipient, value, inputs);
        newTransaction.genSignature(privateKey);

        for(TransactionInput input: inputs){
            UTXOs.remove(input.transOutId);
        }
        return newTransaction;
    }
}
