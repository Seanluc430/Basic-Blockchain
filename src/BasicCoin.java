import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.GsonBuilder;

public class BasicCoin {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); //unspent transactions
    public static int difficulty = 5;
    public static Wallet walA;
    public static Wallet walB;
    public static void main(String[] args) {
        //Using BouncyCastle as Security
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
         //Wallet Creation
        walA = new Wallet();
        walB = new Wallet();

        //Pub and Priv key test
        System.out.println("Public and Private keys:");
        System.out.println(StringUtility.getStringFromKey(walA.publicKey));
        System.out.println(StringUtility.getStringFromKey(walA.privateKey));

        //Testing transaction from A to B
        Transaction transaction = new Transaction(walA.publicKey, walB.publicKey, 5, null);
        transaction.genSignature(walA.privateKey);

        //Check if sig worked and verify from pub key
        System.out.println("Signature Verified");
        System.out.println(transaction.veryifySignature());
    }

    //Checking validity of chain
    public static Boolean isChainValid(){
        Block currBlock;
        Block prevBlock;

        //Hash check
        for(int i=1; i<blockchain.size(); i++){
            currBlock = blockchain.get(i);
            prevBlock = blockchain.get(i-1);

            //Current hash check
            if(!currBlock.hash.equals(currBlock.calculateHash())){
                System.out.println("Current Hashes not equal");
                return false;
            }

            //Previous hash check
            if(!prevBlock.hash.equals(currBlock.prevHash)){
                System.out.println("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }
}


