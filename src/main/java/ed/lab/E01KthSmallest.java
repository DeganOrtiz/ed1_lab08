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
/*
Mejor caso: O(n) se detiene cuando encuentra el n-esimo elemento
Peor: = O(n) debe recorrer todo.
Promedio: O(n) normalmente recorre casi todo el arbol.
Depende de la altura y el numero de nodos
2.
Mejor: O(log n ) caso de arbol balanceado
Peor: O( n) arbol degenerado tipo lista.
Promedio: O(log n) relativamente balanceado
Depende de la altura del arbol
3.
Usar arbol con tamaño del subarboles
Tiempo: O(log n)
Espacio O(n)
 */