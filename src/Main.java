import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class Main {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 6;
    public static void main(String[] args) {
        blockchain.add(new Block("First block", "0"));
        System.out.println("Mining Block 1...");
        blockchain.get(0).blockMine(difficulty);

        blockchain.add(new Block("Second block", blockchain.get(blockchain.size()-1).hash));
        System.out.println("Mining Second Block: ");
        blockchain.get(1).blockMine(difficulty);

        blockchain.add(new Block("Third block", blockchain.get(blockchain.size()-1).hash));
        System.out.println("Mining Third Block: ");
        blockchain.get(2).blockMine(difficulty);

        System.out.println("\nBlockchain is valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
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

