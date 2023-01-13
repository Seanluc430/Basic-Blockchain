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

    //If new transaction could be created, returns true
    public boolean procTransaction(){
        if(veryifySignature() == false){
            System.out.println("#Transaction Signature filed to verify");
            return false;
        }
        //gather transaction inputs
        for(TransactionInput i : inputs){
            i.UTXO = BasicCoin.UTXOs.get(i.transOutId);
        }
        //check if transactions is valid
        if(getInputsValue() < BasicCoin.minimumTransaction){
            System.out.println("#Transaction Inputs too small: " + getInputsValue());
            return false;
        }
        //generate transaction outputs
        float leftOver = getInpustsValue(); //left over change from inputs
        transactionID = calcHash();
        outputs.add(new TransactionOutput(this.reciever, value, transactionID)); //sending value
        outputs.add(new TransactionOutput(this.sender, leftOver, transactionID));//change goes back to sender
        //add outputs to unspent list
        for(TransactionOutput o : outputs){
            BasicCoin.UTXO.put(o.id, o);
        }
        //remove inputs for UTXO when spent
        for(TransactionInput i : inputs){
            if(i.UTXO == null) continue; //skip if not found
            BasicCoin.UTXOs.remove(i.UTXO.id);
        }
        return true;
    }

    //Returns the sum of the input UTXO values
    public float getInputsValue(){
        float total = 0;
        for(TransactionInput i : inputs){
            if(i.UTXO == null) continue; //skip if not found
            total += i.UTXO.value;
        }
        return total;
    }

    //returns the some of outputs
    public float getOutputsValue(){
        float total = 0;
        for(TransactionOutput o : outputs){
            total += o.value;
        }
        return total;
    }
}

