/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

import java.util.Comparator;

import org.mps.tree.BinarySearchTree;
import org.mps.tree.BinarySearchTreeException;

public class BinarySearchTreeMain {
    public static void main(String[] args) {
        // Crear un comparador para enteros
        Comparator<Integer> intComparator = Integer::compare;

        // Crear un árbol binario de búsqueda
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(intComparator);

        try {
            // Probar insert
            bst.insert(5);
            bst.insert(3);
            bst.insert(7);
            bst.insert(1);
            bst.insert(9);

            // Probar render
            System.out.println("Árbol: " + bst.render());

            // Probar isLeaf
            System.out.println("¿Es hoja?: " + bst.isLeaf());

            // Probar contains
            System.out.println("¿Contiene 3?: " + bst.contains(3));
            System.out.println("¿Contiene 6?: " + bst.contains(6));

            // Probar minimum y maximum
            System.out.println("Mínimo: " + bst.minimum());
            System.out.println("Máximo: " + bst.maximum());

            // Probar size y depth
            System.out.println("Tamaño: " + bst.size());
            System.out.println("Profundidad: " + bst.depth());

            // Probar removeBranch
            bst.removeBranch(3);
            System.out.println("Árbol después de remover 3: " + bst.render());

            // Probar excepciones
            BinarySearchTree<Integer> emptyBst = new BinarySearchTree<>(intComparator);
            try {
                emptyBst.isLeaf();
            } catch (BinarySearchTreeException e) {
                System.out.println("Excepción capturada: " + e.getMessage());
            }

            try {
                bst.insert(5); // Intentar insertar un elemento que ya existe
            } catch (BinarySearchTreeException e) {
                System.out.println("Excepción capturada: " + e.getMessage());
            }

            try {
                bst.removeBranch(10); // Intentar remover un elemento que no existe
            } catch (BinarySearchTreeException e) {
                System.out.println("Excepción capturada: " + e.getMessage());
            }

            // Probar inOrder
            bst = new BinarySearchTree<>(intComparator);
            bst.insert(5);
            bst.insert(3);
            bst.insert(7);
            bst.insert(1);
            bst.insert(9);
            
            System.out.println("Recorrido inOrder: " + bst.inOrder());

            // Probar removeValue
            bst = new BinarySearchTree<>(intComparator);
            bst.insert(5);
            bst.insert(3);
            bst.insert(7);
            bst.insert(1);

            System.out.println(bst.render());

            bst.removeValue(5);
            System.out.println("Eliminado el 5: " + bst.render());

            bst.removeValue(3);
            System.out.println("Eliminado el 3: " + bst.render());

            bst.removeValue(7);
            System.out.println("Eliminado el 7: " + bst.render());

            // Probar balance
            bst = new BinarySearchTree<>(intComparator);
            bst.insert(10);
            bst.insert(9);
            bst.insert(8);
            bst.insert(6);
            bst.insert(5);
            bst.insert(4);
            bst.insert(3);

            System.out.println(bst.render());
            bst.balance();
            System.out.println(bst.render());

            bst = new BinarySearchTree<>(intComparator);
            bst.insert(9);
            bst.insert(10);
            bst.insert(8);
            bst.balance();
            System.out.println(bst.render());

        } catch (Exception e) {
            System.out.println("Se produjo una excepción inesperada: " + e.getMessage());
        }
    }
}