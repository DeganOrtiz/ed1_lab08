package ed.lab;

import java.util.Comparator;

public class E02AVLTree<T> {

    private final Comparator<T> comparator;

    // Nodo AVL que extiende TreeNode
    private class AVLNode extends TreeNode<T> {
        int height;

        AVLNode(T value) {
            super(value);
            this.height = 1;
        }
    }

    private AVLNode root;
    private int size = 0;

    public E02AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    // ==================================
    public void insert(T value) {
        root = insert(root, value);
    }

    private AVLNode insert(AVLNode node, T value) {
        if (node == null) {
            size++;
            return new AVLNode(value);
        }

        int cmp = comparator.compare(value, node.value);

        if (cmp < 0)
            node.left = insert((AVLNode) node.left, value);
        else if (cmp > 0)
            node.right = insert((AVLNode) node.right, value);
        else
            return node; // no duplicados

        updateHeight(node);
        return rebalance(node);
    }

    // ==================================0
    public void delete(T value) {
        root = delete(root, value);
    }

    private AVLNode delete(AVLNode node, T value) {
        if (node == null) return null;

        int cmp = comparator.compare(value, node.value);

        if (cmp < 0) {
            node.left = delete((AVLNode) node.left, value);
        } else if (cmp > 0) {
            node.right = delete((AVLNode) node.right, value);
        } else {
            size--;

            // 1 hijo o ninguno
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? (AVLNode) node.left : (AVLNode) node.right;
            } else {
                // 2 hijos
                AVLNode temp = minValueNode((AVLNode) node.right);
                node.value = temp.value;
                node.right = delete((AVLNode) node.right, temp.value);
            }
        }

        if (node == null) return null;

        updateHeight(node);
        return rebalance(node);
    }

    private AVLNode minValueNode(AVLNode node) {
        while (node.left != null)
            node = (AVLNode) node.left;
        return node;
    }

    // ==================================
    public T search(T value) {
        AVLNode current = root;

        while (current != null) {
            int cmp = comparator.compare(value, current.value);

            if (cmp == 0) return current.value;
            else if (cmp < 0) current = (AVLNode) current.left;
            else current = (AVLNode) current.right;
        }

        return null;
    }

    // ==================================
    public int height() {
        return height(root);
    }

    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    // ==================================
    public int size() {
        return size;
    }

    // ==================================0

    private void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(
                height((AVLNode) node.left),
                height((AVLNode) node.right)
        );
    }

    private int getBalance(AVLNode node) {
        return node == null ? 0 :
                height((AVLNode) node.left) - height((AVLNode) node.right);
    }

    private AVLNode rebalance(AVLNode node) {
        int balance = getBalance(node);

        // LL
        if (balance > 1 && getBalance((AVLNode) node.left) >= 0)
            return rotateRight(node);

        // LR
        if (balance > 1 && getBalance((AVLNode) node.left) < 0) {
            node.left = rotateLeft((AVLNode) node.left);
            return rotateRight(node);
        }

        // RR
        if (balance < -1 && getBalance((AVLNode) node.right) <= 0)
            return rotateLeft(node);

        // RL
        if (balance < -1 && getBalance((AVLNode) node.right) > 0) {
            node.right = rotateRight((AVLNode) node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = (AVLNode) y.left;
        AVLNode T2 = (AVLNode) x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = (AVLNode) x.right;
        AVLNode T2 = (AVLNode) y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }
}