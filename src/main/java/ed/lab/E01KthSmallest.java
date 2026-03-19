package ed.lab;

public class E01KthSmallest {

    private int contador = 0;
    private int resultado = 0;

    public int kthSmallest(TreeNode<Integer> root, int k) {
        inorder(root, k);
        return resultado;
    }

    private void inorder(TreeNode<Integer> nodo, int k) {
        if (nodo == null) return;

        // izquierda
        inorder(nodo.left, k);

        // visitar nodo
        contador++;
        if (contador == k) {
            resultado = nodo.value;
            return;
        }

        // derecha
        inorder(nodo.right, k);
    }
}