import java.util.ArrayList;
import java.util.List;

/**
 * @author Aurimas Šakalys 20185388
 */
public class BSTNode<T extends Comparable<T>>  {

    public BSTNode(T value){
        this.value = value;
    }

    public void insert(T value) {
        this.insert(new BSTNode<>(value));
    }

    public void insert(BSTNode<T> node) {
        int comparison = this.value.compareTo(node.value);
        if(comparison == 0) return;

        if(comparison < 0) insertLeft(node);
        if(comparison > 0) insertRight(node);

        this.height = this.calculateHeight();
    }

    public BSTNode<T> find(T value) {
        int comparison = this.value.compareTo(value);

        if(comparison == 0) return this;
        if(hasLeft() && comparison < 0) return getLeft().find(value);
        if(hasRight() && comparison > 0) return getRight().find(value);
        return null;
    }

    public BSTNode<T> getLeft() {
        return this.left;
    }

    public boolean hasLeft() {
        return this.left != null;
    }

    public BSTNode<T> getRight() {
        return this.right;
    }

    public boolean hasRight() {
        return this.right != null;
    }

    public boolean hasChildren() {
        return this.left != null || this.right != null;
    }

    public T getValue() {
        return this.value;
    }

    public int getHeight() {
        return this.height;
    }


    @Override
    public String toString() {
        return "BSTNode{" +
                "value=" + value +
                ", left=" + (hasLeft() ? getLeft().value : 'X') +
                ", right=" + (hasRight() ? getRight().value : 'X') +
                '}';
    }

    private void insertLeft(BSTNode<T> node) {
        if(this.left == null) {
            this.left = node;
        }
        else this.left.insert(node);
    }

    private void insertRight(BSTNode<T> node) {
        if(this.right == null) {
            this.right = node;
        }
        else this.right.insert(node);
    }

    private int calculateHeight() {
        int leftHeight = this.left != null ? this.left.calculateHeight() : 0;
        int rightHeight = this.right != null ? this.right.calculateHeight() : 0;
        int significantHeight = Math.max(leftHeight, rightHeight);
        return significantHeight + 1;
    }

    private BSTNode<T> left;
    private BSTNode<T> right;

    private int height = 0;

    private final T value;
}
