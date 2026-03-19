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
/*
Insert:
Mejor caso: O(log n) el árbol está balanceado y se inserta sin muchas rotaciones.
Peor: O(log n) recorre la altura del árbol y rebalancea.
Promedio: O(log n) siempre mantiene altura logarítmica.
Depende de la altura del árbol (log n)

Delete:
Mejor caso: O(log n) elimina y rebalancea rápidamente.
Peor: O(log n) recorre el árbol y aplica rotaciones.
Promedio: O(log n) mantiene balance en cada operación.
Depende de la altura del árbol (log n)

Search:
Mejor caso: O(log n) elimina y rebalancea rápidamente.
Peor: O(log n) recorre el árbol y aplica rotaciones.
Promedio: O(log n) mantiene balance en cada operación.
Depende de la altura del árbol (log n)

height:
Mejor caso: O(1) retorna valor almacenado.
Peor: O(1) acceso directo.
Promedio: O(1) siempre constante.
No depende del tamaño del árbol

Size:
Mejor caso: O(1) retorna valor almacenado.
Peor: O(1) acceso directo.
Promedio: O(1) siempre constante.
No depende del tamaño del árbol

2. Comparación con bst normal
Mejor caso: O(log n) igual que AVL si está balanceado.
Peor: O(n) el árbol se desbalancea y se vuelve una lista.
Promedio: O(log n) si los datos están distribuidos.
Depende del orden de inserción y la forma del árbol

AVL mantiene O(log n) siempre porque se rebalancea automáticamente,
a diferencia del BST que puede degradarse a O(n)
 */