public class TransactionInput {
    public String transOutId; //references TransactionOutput transaction ID
    public TransactionOutput UTXO; //unspent transaction output

    //Constructor
    public TransactionInput(String transOutId){
        this.transOutId = transOutId;
    }
}
