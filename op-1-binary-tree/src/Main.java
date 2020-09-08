import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random prng = new Random();


        BSTNode<Integer> a = new BSTNode<>(prng.nextInt(20));
        for(int i = 0; i < 10; i++){
            a.insert(prng.nextInt(20));
        }
        System.out.println(a.toString());
    }

}
