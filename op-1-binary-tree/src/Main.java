import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random prng = new Random();


        BSTNode<Integer> a = new BSTNode<>(10);
        for(int i = 0; i < 10; i++){
            a.insert(prng.nextInt(20));
        }

        System.out.println(BSTTreePainter.toString(a));
        System.out.println(a.find(12));
    }

}
