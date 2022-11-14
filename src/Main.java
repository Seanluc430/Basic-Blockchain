public class Main {
    public static void main(String[] args) {
        Block genBlock = new Block("First block", "0");
        System.out.println("Hash for block 1: " + genBlock.hash);

        Block secondBlock = new Block("Second block", genBlock.hash);
        System.out.println("Hash for block 2: " + secondBlock.hash);

        Block thirdBlock = new Block("Third block", secondBlock.hash);
        System.out.println("Hash for block 3: " + thirdBlock.hash);

    }
}

