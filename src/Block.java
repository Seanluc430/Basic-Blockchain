import java.util.Date;

public class Block {

    public String hash;
    public String prevHash;
    private String data;
    private long timeStamp;

    //Constructor
    public Block(String data, String prevHash){
        this.data = data;
        this.prevHash = prevHash;
        timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    //Calculate current hash
    public String calculateHash(){
        String calcHash = StringUtility.applySHA256(prevHash + Long.toString(timeStamp) + data);
        return calcHash;
    }
}

