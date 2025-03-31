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
import java.util.List;

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

        @DisplayName("Renderizar un árbol vacío")
        @Test
        public void render_EmptyBST_ReturnsEmptyString() {  
            //Arrange
            String esperado = "";
            
            //Act
            String resultado = tree.render();

            //Asert
            assertEquals(esperado, resultado);
        }

        @DisplayName("Renderizar un árbol de un solo nodo")
        @Test
        public void render_OneNodeBST_ReturnsStringOfValueOfBST() {  
            //Arrange
            tree.insert(10);
            String esperado = "10";
            
            //Act
            String resultado = tree.render();

            //Asert
            assertEquals(esperado, resultado);
        }

        @DisplayName("Renderizar un árbol con la rama izquierda vacía")
        @Test
        public void render_MultipleNodeEmptyLeftBranch_ReturnsBSTStringWitheLeftBranchEmpty() {  
            //Arrange
            tree.insert(10);
            tree.insert(15);
            String esperado = "10(,15)";
            
            //Act
            String resultado = tree.render();

            //Asert
            assertEquals(esperado, resultado);
        }

        @DisplayName("Renderizar un árbol con la rama derecha vacía")
        @Test
        public void render_MultipleNodeEmptyRightBranch_ReturnsBSTStringWitheRightBranchEmpty() {  
            //Arrange
            tree.insert(10);
            tree.insert(5);
            String esperado = "10(5,)";
            
            //Act
            String resultado = tree.render();

            //Asert
            assertEquals(esperado, resultado);
        }

        @DisplayName("Renderizar un árbol con la rama derecha e izquierda con nodos")
        @Test
        public void render_MultipleNodeNotEmptyRightLeftBranch_ReturnsBSTStringWitheRightBranchAndLeftBracnh() {  
            //Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            String esperado = "10(5,15)";
            
            //Act
            String resultado = tree.render();

            //Asert
            assertEquals(esperado, resultado);
        }
    }

    @DisplayName("Probando el constructor BinarySearchTree")
    @Nested
    public class BinarySearchTreeConstructorTest {
        @DisplayName("El constructor crea un árbol de búsqueda binaria vacío")
        @Test
        public void BinarySearchTree_init_CreatesEmptyBinarySearchTree() {
            //Arrange
            String expected = "";

            //Act
            BinarySearchTree<Integer> tree = new BinarySearchTree<>(comparator);

            //Asert
            assertEquals(expected, tree.render());
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

        @DisplayName("El metodo insert crea un nodo si el árbol está vacío")
        @Test
        public void insert_EmptyTree_CreatesNode() {
            //Arrange
            Integer value = 10;

            //Act
            tree.insert(value);

            //Assert
            assertEquals("10", tree.render());
        }

        @DisplayName("El metodo insert crea un nodo a la derecha si el árbol no está vacío, la rama de la derecha está vacía y el elemento es mayor a la raíz")
        @Test
        public void insert_NotEmptyTreeEmptyRightBranchNewValueBiggerRoot_CreatesNodeRight() {
            //Arrange
            tree.insert(10);
            Integer value = 15;

            //Act
            tree.insert(value);

            //Assert
            assertEquals("10(,15)", tree.render());
        }

        @DisplayName("El metodo insert crea un nodo a la izquierda si el árbol no está vacío, la rama de la izquierda está vacía y el elemento es menor a la raíz")
        @Test
        public void insert_NotEmptyTreeEmptyLeftBranchNewValueLowerRoot_CreatesNodeLeft() {
            //Arrange
            tree.insert(10);
            Integer value = 5;

            //Act
            tree.insert(value);

            //Assert
            assertEquals("10(5,)", tree.render());
        }

        @DisplayName("El metodo insert crea un nodo en la rama de la izquierda si el árbol no tiene vacía rama derecha e izquierda, y el valor a insertar es menor a la raíz")
        @Test
        public void insert_MultipleNodesTreeNotEmptyLeftRightBranchNewValueLowerRoot_CreatesNodeBranchLeft() {
            //Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            Integer value = 3;

            //Act
            tree.insert(value);

            //Assert
            assertEquals("10(5(3,),15)", tree.render());
        }

        @DisplayName("El metodo insert crea un nodo en la rama de la derecha si el árbol no tiene vacía rama derecha e izquierda, y el valor a insertar es mayor a la raíz")
        @Test
        public void insert_MultipleNodesTreeNotEmptyLeftRightBranchNewValueBiggerRoot_CreatesNodeBranchRight() {
            //Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            Integer value = 12;

            //Act
            tree.insert(value);

            //Assert
            assertEquals("10(5,15(12,))", tree.render());
        }
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
        public void removeBranch_MultipleNodesTreeElementNotPresent_ReturnsException() {
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

        @DisplayName("El metodo removeBranch borra correctamente la rama derecha cuando el valor coincide con el nodo derecho")
        @Test
        public void removeBranch_RightBranchValueMatches_RemovesRightBranch() {
            //Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(20);
            Integer value = 15;
            String expected = "10";

            //Act
            tree.removeBranch(value);

            //Assert
            assertEquals(expected, tree.render());
        }

        @DisplayName("El metodo removeBranch borra correctamente la rama izquierda cuando el valor coincide con el nodo izquierdo")
        @Test
        public void removeBranch_LeftBranchValueMatches_RemovesLeftBranch() {
            //Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(2);
            Integer value = 5;
            String expected = "10";

            //Act
            tree.removeBranch(value);

            //Assert
            assertEquals(expected, tree.render());
        }

        @DisplayName("El metodo removeBranch borra correctamente un nodo en un subárbol a más del segundo nivel")
        @Test
        public void removeBranch_DeepRightSubtree_RemovesCorrectNode() {
            //Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(20);
            tree.insert(25);
            Integer value = 20;
            String expected = "10(,15)";

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

    @DisplayName("Probando el metodo removeValue")
    @Nested
    public class RemoveValueTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void setUp() {
            //Arrange
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo removeValue lanza una excepción si el árbol está vacío")
        @Test
        public void removeValue_EmptyTree_ThrowsException() {
            //Act, Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.removeValue(10);
            });
        }

        @DisplayName("El metodo removeValue lanza una excepción si el elemento no está en el árbol")
        @Test
        public void removeValue_ElementNotInTree_ThrowsException() {
            //Arrange
            tree.insert(10);

            //Act, Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.removeValue(5);
            });
        }

        @DisplayName("El metodo removeValue elimina correctamente el único nodo del árbol")
        @Test
        public void removeValue_SingleNodeTree_RemovesNode() {
            //Arrange
            tree.insert(10);

            //Act
            tree.removeValue(10);

            //Assert
            assertEquals("", tree.render());
        }

        @DisplayName("El metodo removeValue elimina un nodo sin hijos en la rama izquierda en un árbol de dos niveles")
        @Test
        public void removeValue_TwoLevelTree_LeftLeafNode_RemovesNode() {
            //Arrange
            tree.insert(10);
            tree.insert(5);

            //Act
            tree.removeValue(5);

            //Assert
            assertEquals("10", tree.render());
        }

        @DisplayName("El metodo removeValue elimina un nodo sin hijos en la rama derecha en un árbol de dos niveles")
        @Test
        public void removeValue_TwoLevelTree_RightLeafNode_RemovesNode() {
            //Arrange
            tree.insert(10);
            tree.insert(15);

            //Act
            tree.removeValue(15);

            //Assert
            assertEquals("10", tree.render());
        }

        @DisplayName("El metodo removeValue elimina un nodo con hijos en la rama izquierda en un árbol de dos niveles")
        @Test
        public void removeValue_TwoLevelTree_LeftNodeWithChildren_RemovesNode() {
            //Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(3);

            //Act
            tree.removeValue(5);

            //Assert
            assertEquals("10(3,)", tree.render());
        }

        @DisplayName("El metodo removeValue elimina un nodo con hijos en la rama derecha en un árbol de dos niveles")
        @Test
        public void removeValue_TwoLevelTree_RightNodeWithChildren_RemovesNode() {
            //Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(20);

            //Act
            tree.removeValue(15);

            //Assert
            assertEquals("10(,20)", tree.render());
        }

        @DisplayName("El metodo removeValue elimina un nodo con hijos en la rama izquierda a más del segundo nivel")
        @Test
        public void removeValue_LeftBranchBeyondSecondLevel_WithChildren_RemovesNode() {
            //Arrange
            tree.insert(20);
            tree.insert(15);
            tree.insert(10);
            tree.insert(12);

            //Act
            tree.removeValue(15);

            //Assert
            assertEquals("20(10(,12),)", tree.render());
        }

        @DisplayName("El metodo removeValue elimina un nodo con hijos en la rama derecha a más del segundo nivel")
        @Test
        public void removeValue_RightBranchBeyondSecondLevel_WithChildren_RemovesNode() {
            //Arrange
            tree.insert(20);
            tree.insert(25);
            tree.insert(30);
            tree.insert(28);

            //Act
            tree.removeValue(25);

            //Assert
            assertEquals("20(,30(28,))", tree.render());
        }

        @DisplayName("El metodo removeValue elimina un nodo sin hijos en la rama izquierda a más del segundo nivel")
        @Test
        public void removeValue_LeftBranchBeyondSecondLevel_NoChildren_RemovesNode() {
            //Arrange
            tree.insert(20);
            tree.insert(15);
            tree.insert(10);

            //Act
            tree.removeValue(10);

            //Assert
            assertEquals("20(15,)", tree.render());
        }

        @DisplayName("El metodo removeValue elimina un nodo sin hijos en la rama derecha a más del segundo nivel")
        @Test
        public void removeValue_RightBranchBeyondSecondLevel_NoChildren_RemovesNode() {
            //Arrange
            tree.insert(20);
            tree.insert(25);
            tree.insert(30);

            //Act
            tree.removeValue(30);

            //Assert
            assertEquals("20(,25)", tree.render());
        }

        @DisplayName("El metodo removeValue elimina un nodo con dos hijos correctamente")
        @Test
        public void removeValue_NodeWithTwoChildren_RemovesNode() {
            //Arrange
            tree.insert(20);
            tree.insert(10);
            tree.insert(30);
            tree.insert(5);
            tree.insert(15);
            tree.insert(25);
            tree.insert(35);

            //Act
            tree.removeValue(10);

            //Assert
            assertEquals("20(15(5,),30(25,35))", tree.render());
        }

        @DisplayName("El metodo removeValue elimina correctamente la raíz de un árbol con hijos")
        @Test
        public void removeValue_RemoveInOrderSuccessor_LeavesSubtreeEmpty() {
            //Arrange
            tree.insert(20);
            tree.insert(10);
            tree.insert(30);
            tree.insert(25);

            //Act
            tree.removeValue(20);

            //Assert
            assertEquals("25(10,30)", tree.render());
        }
    }

    @DisplayName("Probando el metodo inOrder")
    @Nested
    public class InOrderTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void setUp() {
            tree = new BinarySearchTree<>(comparator);
        }

        @DisplayName("El metodo inOrder devuelve una lista vacía si el árbol está vacío")
        @Test
        public void inOrder_EmptyTree_ReturnsEmptyList() {
            //Act
            List<Integer> result = tree.inOrder();

            //Assert
            assertTrue(result.isEmpty());
        }

        @DisplayName("El metodo inOrder devuelve correctamente el único nodo del árbol")
        @Test
        public void inOrder_OneNodeTree_ReturnsSingleElementList() {
            //Arrange
            tree.insert(10);

            //Act
            List<Integer> result = tree.inOrder();

            //Assert
            assertEquals(List.of(10), result);
        }

        @DisplayName("El metodo inOrder devuelve correctamente los nodos para un árbol con solo subárbol derecho")
        @Test
        public void inOrder_RightSubtreeOnly_ReturnsOrderedList() {
            //Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(20);

            //Act
            List<Integer> result = tree.inOrder();

            //Assert
            assertEquals(List.of(10, 15, 20), result);
        }

        @DisplayName("El metodo inOrder devuelve correctamente los nodos para un árbol con solo subárbol izquierdo")
        @Test
        public void inOrder_LeftSubtreeOnly_ReturnsOrderedList() {
            //Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(2);

            //Act
            List<Integer> result = tree.inOrder();

            //Assert
            assertEquals(List.of(2, 5, 10), result);
        }

        @DisplayName("El metodo inOrder devuelve correctamente los nodos para un árbol con subárbol izquierdo y derecho")
        @Test
        public void inOrder_LeftAndRightSubtrees_ReturnsOrderedList() {
            //Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            tree.insert(2);
            tree.insert(7);
            tree.insert(12);
            tree.insert(20);

            //Act
            List<Integer> result = tree.inOrder();

            //Assert
            assertEquals(List.of(2, 5, 7, 10, 12, 15, 20), result);
        }
    }


    @DisplayName("Probando el metodo balance")
    @Nested
    public class BalanceTest {
        private BinarySearchTree<Integer> tree;

        @DisplayName("Se inicializa el BinarySearchTree con el comparator")
        @BeforeEach
        public void setUp() {
            tree = new BinarySearchTree<>(comparator);
        }
    }
}
