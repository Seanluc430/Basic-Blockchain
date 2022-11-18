import java.util.Date;

public class Block {

    public String hash;
    public String prevHash;
    private String data;
    private long timeStamp;
    private int nonce;

    //Constructor
    public Block(String data, String prevHash){
        this.data = data;
        this.prevHash = prevHash;
        timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    //Calculate current hash
    public String calculateHash(){
        String calcHash = StringUtility.applySHA256(prevHash + Long.toString(timeStamp)
                + Integer.toString(nonce) + data);
        return calcHash;
    }

    //Mining function
    public void blockMine(int difficulty){
        String wantedHash = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring(0, difficulty).equals(wantedHash)){
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Successfully Mined: " + hash);
    }
}

