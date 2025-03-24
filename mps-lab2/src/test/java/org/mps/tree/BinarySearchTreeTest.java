package org.mps.tree;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest {
    private static Comparator<Integer> comparator;

    @DisplayName("Se inicializa el Comparator en orden natural")
    @BeforeAll
    public static void setUp() {
        comparator = Comparator.naturalOrder();
    }

    @DisplayName("Probando el metodo render")
    @Nested
    public class RenderTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void startUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }
    }


    @DisplayName("Probando el constructor BinarySearchTree")
    @Nested
    public class BinarySearchTreeConstructorTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void startUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }
    }

    @DisplayName("Probando el metodo insert")
    @Nested
    public class InsertTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void startUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo insert lanza una excepción si el valor ya existe")
        @Test
        public void insert_EmptyTree_ThrowsException() {
            //Arrange
            Integer value = 5;
            tree.insert(value);

            //Act, Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.insert(value);
            });
        }
/*
        @DisplayName("El metodo insert crea un nodo si el árbol está vacío")
        @Test
        public void insert_EmptyTree_CreatesNode() {
            //Arrange
            Integer value = 5;

            //Act
            tree.insert(10);

            //Assert
            assertEquals("5()", tree.render());
        }*/
    }

    @DisplayName("Probando el metodo isLeaf")
    @Nested
    public class IsLeafTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void startUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo isLeaf lanza una excepción si el arbol esta vacio")
        @Test
        public void isLeaf_EmptyTree_ThrowsException() {
            //Act, Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.isLeaf();
            });
        }

        @DisplayName("El metodo isLeaf devuelve true para un arbol con un único nodo")
        @Test
        public void isLeaf_OneNodeTree_ReturnsTrue() {
            //Arrange
            tree.insert(10);

            //Act
            boolean resultado = tree.isLeaf();

            //Assert
            assertTrue(resultado);
        }

        @DisplayName("El metodo isLeaf devuelve false para un arbol con rama derecha pero no izquierda")
        @Test
        public void isLeaf_MultipleNodesTreeOnlyRightBranch_ReturnsFalse() {
            //Arrange
            tree.insert(10);
            tree.insert(15);

            //Act
            boolean resultado = tree.isLeaf();

            //Assert
            assertFalse(resultado);
        }

        @DisplayName("El metodo isLeaf devuelve false para un arbol con rama izquierda pero no derecha")
        @Test
        public void isLeaf_MultipleNodesTreeOnlyLeftBranch_ReturnsFalse() {
            //Arrange
            tree.insert(10);
            tree.insert(5);

            //Act
            boolean resultado = tree.isLeaf();

            //Assert
            assertFalse(resultado);
        }

        @DisplayName("El metodo isLeaf devuelve false para un arbol con rama izquierda y derecha")
        @Test
        public void isLeaf_MultipleNodesTreeRightAndLeftBranch_ReturnsFalse() {
            //Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);

            //Act
            boolean resultado = tree.isLeaf();

            //Assert
            assertFalse(resultado);
        }
    }

    @DisplayName("Probando el metodo contains")
    @Nested
    public class ContainsTree {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void startUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo contains lanza una excepción si el arbol esta vacio")
        @Test
        public void contains_EmptyTree_ThrowsException() {
            //Arrange
            Integer value = 10;

            //Act, Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.contains(value);
            });
        }

        @DisplayName("El metodo contains devuelve true para un arbol con un nodo que contiene el valor dado")
        @Test
        public void contains_OneNodeTreeContainsValue_ReturnsTrue() {
            //Arrange
            Integer value = 10;
            tree.insert(value);

            //Act
            boolean resultado = tree.contains(value);

            //Assert
            assertTrue(resultado);
        }

        @DisplayName("El metodo contains devuelve false para un arbol con un nodo que no contiene el valor dado esperado a la derecha")
        @Test
        public void contains_OneNodeTreeNotContainsValueExpectedRight_ReturnsFalse() {
            //Arrange
            Integer value = 15;
            tree.insert(10);

            //Act
            boolean resultado = tree.contains(value);

            //Assert
            assertFalse(resultado);
        }

        @DisplayName("El metodo contains devuelve false para un arbol con un nodo que no contiene el valor dado esperado a la izquierda")
        @Test
        public void contains_OneNodeTreeNotContainsValueExpectedLeft_ReturnsFalse() {
            //Arrange
            Integer value = 5;
            tree.insert(10);

            //Act
            boolean resultado = tree.contains(value);

            //Assert
            assertFalse(resultado);
        }

        @DisplayName("El metodo contains devuelve true para un arbol con multiples nodos que contiene el valor dado en la rama de la derecha")
        @Test
        public void contains_MultipleNodesTreeContainsValueBranchRight_ReturnsTrue() {
            //Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(5);
            Integer value = 15;

            //Act
            boolean resultado = tree.contains(value);

            //Assert
            assertTrue(resultado);
        }

        @DisplayName("El metodo contains devuelve true para un arbol con multiples nodos que contiene el valor dado en la rama de la izquierda")
        @Test
        public void contains_MultipleNodesTreeContainsValueBranchLeft_ReturnsTrue() {
            //Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(5);
            Integer value = 5;

            //Act
            boolean resultado = tree.contains(value);

            //Assert
            assertTrue(resultado);
        }

        @DisplayName("El metodo contains devuelve true para un arbol con multiples nodos que no contiene el valor dado esperado la rama de la derecha")
        @Test
        public void contains_MultipleNodesTreeNotContainsValueExpectedBranchRight_ReturnsFalse() {
            //Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(5);
            Integer value = 14;

            //Act
            boolean resultado = tree.contains(value);

            //Assert
            assertFalse(resultado);
        }

        @DisplayName("El metodo contains devuelve true para un arbol con multiples nodos que no contiene el valor dado esperado la rama de la izquierda")
        @Test
        public void contains_MultipleNodesTreeNotContainsValueExpectedBranchLeft_ReturnsFalse() {
            //Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(5);
            Integer value = 4;

            //Act
            boolean resultado = tree.contains(value);

            //Assert
            assertFalse(resultado);
        }
    }
   
    @DisplayName("Probando el metodo minimum")
    @Nested
    public class MinimunTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void startUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo minimum lanza una excepción si el arbol esta vacio")
        @Test
        public void minimum_EmptyTree_ReturnsException() {
            //Act, Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.minimum();
            });
        }

        @DisplayName("El metodo minimum devuelve el valor minimo (unico valor) para un arbol con un nodo")
        @Test
        public void minimum_OneNodeTree_ReturnsMinimumValue() {
            //Arrange
            tree.insert(7);
            Integer expected = 7;

            //Act
            Integer minimum = tree.minimum();

            //Assert
            assertEquals(expected, minimum);
        }

        @DisplayName("El metodo minimum devuelve el valor minimo para un arbol con multiples nodos")
        @Test
        public void minimum_MultipleNodesTree_ReturnsMinimumValue() {
            //Arrange
            tree.insert(10);
            tree.insert(13);
            tree.insert(17);
            tree.insert(4);
            tree.insert(-12);
            tree.insert(-7);
            Integer expected = -12;

            //Act
            Integer minimum = tree.minimum();

            //Assert
            assertEquals(expected, minimum);
        }
    }

    @DisplayName("Probando el metodo maximum")
    @Nested
    public class MaximunTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void startUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo maximum lanza una excepción si el arbol esta vacio")
        @Test
        public void maximum_EmptyTree_ReturnsException() {
            //Act, Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.maximum();
            });
        }

        @DisplayName("El metodo maximum devuelve el valor maximo (unico valor) para un arbol con un nodo")
        @Test
        public void maximum_OneNodeTree_ReturnsMaximumValue() {
            //Arrange
            tree.insert(7);
            Integer expected = 7;

            //Act
            Integer maximum = tree.maximum();

            //Assert
            assertEquals(expected, maximum);
        }

        @DisplayName("El metodo maximum devuelve el valor maximo para un arbol con multiples nodos")
        @Test
        public void maximum_MultipleNodesTree_ReturnsMaximumValue() {
            //Arrange
            tree.insert(10);
            tree.insert(13);
            tree.insert(17);
            tree.insert(4);
            tree.insert(-12);
            tree.insert(-7);
            Integer expected = 17;

            //Act
            Integer maximum = tree.maximum();

            //Assert
            assertEquals(expected, maximum);
        }
    }

    @DisplayName("Probando el metodo removeBranch")
    @Nested
    public class RemoveBranchTest {
        private BinarySearchTree<Integer> tree;
        
        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void setUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo removeBranch lanza una excepción si el arbol esta vacio")
        @Test
        public void removeBranchTest_EmptyTree_ReturnsException() {
            //Arrange
            Integer value = 7;

            //Act, Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.removeBranch(value);
            });
        }

        @DisplayName("El metodo removeBranch lanza una excepción si el arbol no contiene el elemento")
        @Test
        public void removeBranch_MultipleNodesTree_ElementNotPresent_ReturnsException() {
            //Arrange
            tree.insert(10);
            tree.insert(13);
            tree.insert(17);
            tree.insert(4);
            tree.insert(-12);
            tree.insert(-7);
            Integer value = 11;

            //Act, Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.removeBranch(value);
            });
        }

        @DisplayName("El metodo removeBranch borra la rama correspondiente para un arbol con un nodo")
        @Test
        public void removeBranch_OneNodeTree_RemovesCorrectBranch() {
            //Arrange
            tree.insert(7);
            Integer value = 7;
            String expected = "";

            //Act
            tree.removeBranch(value);

            //Assert
            assertEquals(expected, tree.render());
        }

        @DisplayName("El metodo removeBranch borra la rama correspondiente para un arbol con multiples nodo")
        @Test
        public void removeBranch_MultipleNodesTree_RemovesCorrectBranch() {
            //Arrange
            tree.insert(10);
            tree.insert(13);
            tree.insert(17);
            tree.insert(4);
            tree.insert(9);
            tree.insert(8);
            tree.insert(6);
            tree.insert(-12);
            tree.insert(-7);
            Integer value = 9;
            String expected = "10(4(-12(,-7),),13(,17))";

            //Act
            tree.removeBranch(value);

            //Assert
            assertEquals(expected, tree.render());
        }
    }

    @DisplayName("Probando el metodo size")
    @Nested
    public class SizeTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void setUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo size devuelve cero para un arbol vacio")
        @Test
        public void size_EmptyTree_ReturnsZero() {
            //Arrange
            Integer expected = 0;

            //Act
            Integer size = tree.size();

            //Assert 
            assertEquals(expected, size);
        }

        @DisplayName("El metodo size devuelve uno para un arbol con un nodo")
        @Test
        public void size_OneNodeTree_ReturnsOne() {
            //Arrange
            tree.insert(7);
            Integer expected = 1;

            //Act
            Integer size = tree.size();

            //Assert
            assertEquals(expected, size);
        }

        @DisplayName("El metodo size devuelve el tamaño correcto para un arbol con multiples nodos")
        @Test
        public void size_MultipleNodesTree_ReturnsCorrectSize() {
            //Arrange
            tree.insert(10);
            tree.insert(13);
            tree.insert(17);
            tree.insert(4);
            tree.insert(-12);
            tree.insert(-7);
            Integer expected = 6;

            //Act
            Integer size = tree.size();

            //Assert
            assertEquals(expected, size);
        }
    }

    @DisplayName("Probando el metodo depth")
    @Nested
    public class DepthTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void setUp() {
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo depth devuelve cero para un arbol vacio")
        @Test
        public void depth_EmptyTree_ReturnsZero() {
            //Arrange
            Integer expected = 0;

            //Act
            Integer depth = tree.depth();

            //Assert 
            assertEquals(expected, depth);
        }

        @DisplayName("El metodo depth devuelve uno para una arbol con un nodo")
        @Test
        public void depth_OneNodeTree_ReturnsOne() {
            //Arrange
            tree.insert(7);
            Integer expected = 1;

            //Act
            Integer depth= tree.depth();

            //Assert
            assertEquals(expected, depth);
        }

        @DisplayName("El metodo depth devuelve la profundidad correcta para un arbol con multiples nodos")
        @Test
        public void depth_MultipleNodesTree_ReturnsCorrectDepth() {
            //Arrange
            tree.insert(10);
            tree.insert(13);
            tree.insert(17);
            tree.insert(4);
            tree.insert(-12);
            tree.insert(-7);
            Integer expected = 4;

            //Act
            Integer depth = tree.depth();

            //Assert
            assertEquals(expected, depth);
        }
    }
}
