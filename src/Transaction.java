import java.security.*;
import java.util.ArrayList;

public class Transaction {
    public String transactionID; //Hash of transaction
    public PublicKey sender;
    public PublicKey reciever;
    public float val;
    public byte[] signature; //Allowing only owner to send

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int transactionSeq = 0; //Count of how many generated transactions

    //Constructor
    public Transaction(PublicKey from, PublicKey to, float val, ArrayList<TransactionInput> inputs){
        sender = from;
        reciever = to;
        this.val = val;
        this.inputs = inputs;
    }

    //Transaction Hash Calculation for ID
    private String calcHash(){
        transactionSeq++;
        return StringUtility.applySHA256(StringUtility.getStringFromKey(sender) +
                StringUtility.getStringFromKey(reciever) + Float.toString(val) + transactionSeq);
    }

    //Signs all permanent data
    public void genSignature(PrivateKey privateKey){
        String data = StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(reciever) + Float.toString(val);
        signature = StringUtility.applyECDSASig(privateKey, data);
    }

    //Verify that it has not been tampered with
    public boolean veryifySignature(){
        String data = StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(reciever) + Float.toString(val);
        return StringUtility.verifyECDSASig(sender, data, signature);
    }
}

