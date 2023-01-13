import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey recipient;
    public float value; //amount of coins
    public String parentTransId; //id od transaction output was created in

    //Constructor
    public TransactionOutput(PublicKey recipient, float value, String parentTransId){
        this.recipient = recipient;
        this.value = value;
        this.parentTransId = parentTransId;
        this.id = StringUtility.applySHA256(StringUtility.getStringFromKey(recipient)+
                Float.toString(value)+parentTransId);
    }

    //Check coin ownership
    public boolean isMine(PublicKey publickey){
        return (publickey == recipient);
    }
}
