package org.mps.boundedqueue;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Answers.values;

import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

/**
 * Para no modificar la implementación se interpreta que getLast devuelve el
 * índice de la siguiente posición libre, no del último elemento como se define
 * en la interfaz
 */
public class ArrayBoundedQueueTest {
    @DisplayName("Probar el constructor")
    @Nested
    public class ArrayBoundedQueueConstructorTest {
        @Test
        @DisplayName("El constructor creado con una capacidad positiva se crea correctamente")
        public void ArrayBoundedQueue_PositiveCapacity_CreatedCorrectly() {
            // Arrange
            ArrayBoundedQueue<Integer> arrayBoundedQueue;
            int capacity = 9;

            // Act
            arrayBoundedQueue = new ArrayBoundedQueue<>(capacity);

            // Assert
            assertThat(arrayBoundedQueue)
                .isNotNull();
            assertThat(arrayBoundedQueue.isEmpty())
                .isTrue();
            assertThat(arrayBoundedQueue.getFirst())
                .isEqualTo(0);
            assertThat(arrayBoundedQueue.getLast())
                .isEqualTo(0);
            assertThat(arrayBoundedQueue.size())
                .isEqualTo(0);
        }

        @Test
        @DisplayName("El constructor creado con una capacidad menor o igual a 0 lanza excepción")
        public void ArrayBoundedQueue_CapacityLessThanOrEqualTo_ThrowsException() {
            // Arrange
            int capacity = 0;

            // Act, Assert
            assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                new ArrayBoundedQueue<>(capacity);
            })
            .withMessage("ArrayBoundedException: capacity must be positive");
        }
    }

    @DisplayName("Probar el método put")
    @Nested
    public class putTest {
        ArrayBoundedQueue<Integer> arrayBoundedQueue;

        @DisplayName("Se inicializa arrayBoundedQueue con una capacidad concreta para cada test")
        @BeforeEach
        public void startUp() {
            // Arrange
            arrayBoundedQueue = new ArrayBoundedQueue<>(7);
        }

        @Test
        @DisplayName("Hacer un put con una cola acotada llena lanza excepción")
        public void put_FullBoundedQueue_ThrowsException() {
            // Arrange
            arrayBoundedQueue.put(13);
            arrayBoundedQueue.put(23);
            arrayBoundedQueue.put(3);
            arrayBoundedQueue.put(31);
            arrayBoundedQueue.put(22);
            arrayBoundedQueue.put(0);
            arrayBoundedQueue.put(7);
            Integer value = 27;

            // Act, Assert
            assertThatExceptionOfType(FullBoundedQueueException.class)
            .isThrownBy(() -> {
                arrayBoundedQueue.put(value);
            })
            .withMessage("put: full bounded queue");
        }
        
        @Test
        @DisplayName("Hacer un put con un elemento null lanza excepción")
        public void put_NullElement_ThrowsException() {
            // Arrange
            arrayBoundedQueue.put(13);
            arrayBoundedQueue.put(23);
            arrayBoundedQueue.put(3);
            Integer value = null;

            // Act, Assert
            assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                arrayBoundedQueue.put(value);
            })
            .withMessage("put: element cannot be null");
        }

        @Test
        @DisplayName("Hacer un put con una cola acotada vacía funciona correctamente")
        public void put_EmptyBoundedQueue_WorksCorrectly() {
            // Arrange
            Integer value = 27;

            // Act
            arrayBoundedQueue.put(value);

            // Assert
            assertThat(arrayBoundedQueue.getLast())
                .isEqualTo(1);
            assertThat(arrayBoundedQueue.size())
                .isEqualTo(1);
            assertThat(arrayBoundedQueue.isEmpty())
                .isFalse();
        }
        
        @Test
        @DisplayName("Hacer un put con una cola acotada con elementos funciona correctamente")
        public void put_BoundedQueueWithElements_WorksCorrectly() {
            // Arrange
            arrayBoundedQueue.put(13);
            arrayBoundedQueue.put(23);
            arrayBoundedQueue.put(3);
            arrayBoundedQueue.put(31);
            Integer value = 27;

            // Act
            arrayBoundedQueue.put(value);

            // Assert
            assertThat(arrayBoundedQueue.getLast())
                .isEqualTo(5);
            assertThat(arrayBoundedQueue.size())
                .isEqualTo(5);
        }
        
        @Test
        @DisplayName("Hacer un put con una cola acotada con número de elementos (capacidad-1) pasa a cola llena y funciona correctamente")
        public void put_BoundedQueueWithCapacityMinus1Elements_WorksCorrectly() {
            // Arrange
            arrayBoundedQueue.put(13);
            arrayBoundedQueue.put(23);
            arrayBoundedQueue.put(3);
            arrayBoundedQueue.put(31);
            arrayBoundedQueue.put(22);
            arrayBoundedQueue.put(0);
            Integer value = 27;

            // Act
            arrayBoundedQueue.put(value);

            // Assert
            assertThat(arrayBoundedQueue.getLast())
                .isEqualTo(arrayBoundedQueue.getFirst()); // == 0
            assertThat(arrayBoundedQueue.size())
                .isEqualTo(7);
            assertThat(arrayBoundedQueue.isFull())
                .isTrue();
        }
        
        @Test
        @DisplayName("Hacer un put con una cola acotada llena después de hacer un get añade un elemento al principio del buffer")
        public void put_FullBoundedQueueAfterGet_WorksCorrectly() {
            // Arrange
            arrayBoundedQueue.put(13);
            arrayBoundedQueue.put(23);
            arrayBoundedQueue.put(3);
            arrayBoundedQueue.put(31);
            arrayBoundedQueue.put(22);
            arrayBoundedQueue.put(0);
            arrayBoundedQueue.put(7);
            arrayBoundedQueue.get();
            Integer value = 27;

            // Act
            arrayBoundedQueue.put(value);

            // Assert
            assertThat(arrayBoundedQueue.getLast())
                .isEqualTo(arrayBoundedQueue.getFirst()); // == 1
            assertThat(arrayBoundedQueue.size())
                .isEqualTo(7);
            assertThat(arrayBoundedQueue.isFull())
                .isTrue();
        }
    }
    
    @DisplayName("Probar el método get")
    @Nested
    public class getTest {
        ArrayBoundedQueue<Integer> arrayBoundedQueue;

        @DisplayName("Se inicializa arrayBoundedQueue con una capacidad concreta para cada test")
        @BeforeEach
        public void startUp() {
            // Arrange
            arrayBoundedQueue = new ArrayBoundedQueue<>(7);
        }

        @Test
        @DisplayName("Hacer un get con una cola acotada vacía lanza excepción")
        public void get_EmptyBoundedQueue_ThrowsException() {
            // Act, Assert
            assertThatExceptionOfType(EmptyBoundedQueueException.class)
            .isThrownBy(() -> {
                arrayBoundedQueue.get();
            })
            .withMessage("get: empty bounded queue");
        }

        @Test
        @DisplayName("Hacer un get con una cola acotada con elementos funciona correctamente")
        public void get_BoundedQueueWithElements_WorksCorrectly() {
            // Arrange
            arrayBoundedQueue.put(13);
            arrayBoundedQueue.put(23);
            arrayBoundedQueue.put(3);
            arrayBoundedQueue.put(31);

            // Act
            Integer value = arrayBoundedQueue.get();

            // Assert
            assertThat(value)
                .isEqualTo(13);
            assertThat(arrayBoundedQueue.getFirst())
                .isEqualTo(1);
            assertThat(arrayBoundedQueue.size())
                .isEqualTo(3);
        }

        @Test
        @DisplayName("Hacer un get con una cola acotada llena funciona correctamente")
        public void get_FullBoundedQueue_WorksCorrectly() {
            // Arrange
            arrayBoundedQueue.put(13);
            arrayBoundedQueue.put(23);
            arrayBoundedQueue.put(3);
            arrayBoundedQueue.put(31);
            arrayBoundedQueue.put(22);
            arrayBoundedQueue.put(0);
            arrayBoundedQueue.put(7);

            // Act
            Integer value = arrayBoundedQueue.get();

            // Assert
            assertThat(value)
                .isEqualTo(13);
            assertThat(arrayBoundedQueue.getFirst())
                .isEqualTo(1);
            assertThat(arrayBoundedQueue.size())
                .isEqualTo(6);
            assertThat(arrayBoundedQueue.isFull())
                .isFalse();
        }

        @Test
        @DisplayName("Hacer un get con una cola acotada vacía después de hacer un put funciona correctamente")
        public void get_EmptyBoundedQueueAfterPut_WorksCorrectly() {
            // Arrange
            arrayBoundedQueue.put(7);
            
            // Act
            Integer value = arrayBoundedQueue.get();

            // Assert
            assertThat(value)
                .isEqualTo(7);
            assertThat(arrayBoundedQueue.getFirst())
                .isEqualTo(arrayBoundedQueue.getLast()); // == 0
            assertThat(arrayBoundedQueue.size())
                .isEqualTo(0);
            assertThat(arrayBoundedQueue.isEmpty())
                .isTrue();
        }

       

        @Test
        @DisplayName("Hacer un get, con una cola acotada llena después de hacer un get y seguidamente unos puts, devuelve el elemento del principio del buffer")
        public void get_FullBoundedQueueAfterGetAndPuts_WorksCorrectly() {
            // Arrange
            arrayBoundedQueue.put(13);
            arrayBoundedQueue.put(23);
            arrayBoundedQueue.put(3);
            arrayBoundedQueue.put(31);
            arrayBoundedQueue.put(22);
            arrayBoundedQueue.put(0);
            arrayBoundedQueue.put(7);
            arrayBoundedQueue.get();
            arrayBoundedQueue.get();
            arrayBoundedQueue.get();
            arrayBoundedQueue.get();
            arrayBoundedQueue.get();
            arrayBoundedQueue.get();
            arrayBoundedQueue.get();
            arrayBoundedQueue.put(15);
            arrayBoundedQueue.put(33);
            arrayBoundedQueue.put(8);

            // Act
            Integer value = arrayBoundedQueue.get();

            // Assert
            assertThat(value)
                .isEqualTo(15);
            assertThat(arrayBoundedQueue.getLast())
                .isEqualTo(3);
            assertThat(arrayBoundedQueue.getFirst())
                .isEqualTo(1);
            assertThat(arrayBoundedQueue.size())
                .isEqualTo(2);
            assertThat(arrayBoundedQueue.isFull())
                .isFalse();
        }
    }

    @DisplayName("Probar función isFull")
    @Nested
    public class isFullTest {
        private ArrayBoundedQueue<Integer> cola;

        @BeforeEach
        public void setUp() {
            // Arrange
            cola = new ArrayBoundedQueue<>(5);
        }

        @Test
        @DisplayName("Comprobar si una cola vacía está llena")
        public void isFull_ColaVacia_DevuelveFalse() {
            // Act
            boolean resultado = cola.isFull();

            // Assert
            assertThat(resultado)
                .isFalse();
        }

        @Test
        @DisplayName("Comprobar si una cola con elementos no llena está llena")
        public void isFull_ColaConElementosNoLlena_DevuelveFalse() {
            // Arrange
            cola.put(1);
            cola.put(2);

            // Act
            boolean resultado = cola.isFull();

            // Assert
            assertThat(resultado)
                .isFalse();
        }

        @Test
        @DisplayName("Comprobar si una cola llena está llena")
        public void isFull_ColaLlena_DevuelveTrue() {
            // Arrange
            cola.put(1);
            cola.put(2);
            cola.put(3);
            cola.put(4);
            cola.put(5);

            // Act
            boolean resultado = cola.isFull();

            // Assert
            assertThat(resultado)
                .isTrue();
        }
    }

    @DisplayName("Probar función isEmpty")
    @Nested
    public class isEmptyTest {
        private ArrayBoundedQueue<Integer> cola;

        @BeforeEach
        public void setUp() {
            // Arrange
            cola = new ArrayBoundedQueue<>(5);
        }

        @Test
        @DisplayName("Comprobar si una cola vacía está vacía")
        public void isEmpty_ColaVacia_DevuelveTrue() {
            // Act
            boolean resultado = cola.isEmpty();

            // Assert
            assertThat(resultado)
                .isTrue();
        }

        @Test
        @DisplayName("Comprobar si una cola con elementos no está vacía")
        public void isEmpty_ColaConElementos_DevuelveFalse() {
            // Arrange
            cola.put(1);

            // Act
            boolean resultado = cola.isEmpty();

            // Assert
            assertThat(resultado)
                .isFalse();
        }
    }

    @DisplayName("Probar función size")
    @Nested
    public class sizeTest {
        private ArrayBoundedQueue<Integer> cola;

        @BeforeEach
        public void setUp() {
            // Arrange
            cola = new ArrayBoundedQueue<>(5);
        }

        @Test
        @DisplayName("Obtener el tamaño de una cola vacía")
        public void size_ColaVacia_DevuelveCero() {
            // Act
            int resultado = cola.size();

            // Assert
            assertThat(resultado)
                .isEqualTo(0);
        }

        @Test
        @DisplayName("Obtener el tamaño de una cola con elemntos")
        public void size_ColaConElementos_DevuelveTamañoCola() {
            // Arrange
            cola.put(1);
            cola.put(2);
            cola.put(3);

            // Act
            int resultado = cola.size();

            // Assert
            assertThat(resultado)
                .isEqualTo(3);
        }
    }

    @DisplayName("Probar función getFirst")
    @Nested
    public class getFirstTest {
        @Test
        @DisplayName("Obtener el índice del primer elemento de una cola vacía")
        public void getFirst_ColaVacia_DevuelveIndiceIgualAlSiguienteLibre() {
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(1);

            // Act
            int resultado = cola.getFirst();

            // Assert
            assertThat(resultado)
                .isEqualTo(cola.getLast());
        }

        @Test
        @DisplayName("Obtener el índice del primer elemento de una cola con elementos no llena")
        public void getFirst_ColaConElementosNoLlena_DevuelveIndicePrimerElemento() {
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(2);
            cola.put(1);
            cola.put(1);
            cola.get();

            // Act
            int resultado = cola.getFirst();

            // Assert
            assertThat(resultado)
                .isEqualTo(1);
        }

        @DisplayName("Obtener el índice del primer elemento de una cola llena")
        @Test
        public void getFirst_ColaLlena_DevuelveIndiceIgualAlSiguienteLibre() {
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(2);
            cola.put(1);
            cola.put(1);

            // Act
            int resultado = cola.getFirst();

            // Assert
            assertThat(resultado)
                .isEqualTo(cola.getLast());
        }
    }

    @DisplayName("Probar función getLast")
    @Nested
    public class getLastTest {
        @Test
        @DisplayName("Obtener el índice de la siguiente posición libre de una cola vacía")
        public void getLast_ColaVacia_DevuelveIndiceIgualAlIndiceDelPrimero() {
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(1);

            // Act
            int resultado = cola.getLast();

            // Assert
            assertThat(resultado)
                .isEqualTo(cola.getFirst());
        }

        @Test
        @DisplayName("Obtener el índice de la siguiente posición libre de una cola con elementos no llena")
        public void getLast_ColaConElementosNoLlena_DevuelveIndiceSiguienteLibre() {
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(2);
            cola.put(1);

            // Act
            int resultado = cola.getLast();

            // Assert
            assertThat(resultado)
                .isEqualTo(1);
        }

        @Test
        @DisplayName("Obtener el índice de la siguiente posición libre de una cola llena")
        public void getLast_ColaLlena_DevuelveIndiceIgualAlIndiceDelPrimero() {
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(2);
            cola.put(1);
            cola.put(1);
            
            // Act
            int resultado = cola.getLast();

            // Assert
            assertThat(resultado)
                .isEqualTo(cola.getFirst());
        }
    }

    @DisplayName("Probar función iterator")
    @Nested
    public class iteratorTest {
        private ArrayBoundedQueue<Integer> cola;

        @BeforeEach
        public void setUp() {
            // Arrange
            cola = new ArrayBoundedQueue<>(5);
        }

        @Test
        @DisplayName("Obtener el iterador de una cola vacía")
        public void iterator_ColaVacia_DevuelveIteradorVacio() {
            // Act
            Iterator<Integer> iterador = cola.iterator();

            // Assert
            assertThat(iterador.hasNext())
                .isFalse();
        }

        @Test
        @DisplayName("Obtener el iterador de una cola con elementos")
        public void iterator_ColaConElementos_DevuelveIteradorConElementos() {
            // Arrange
            cola.put(1);
            cola.put(2);
            cola.put(3);

            // Act
            Iterator<Integer> iterador = cola.iterator();

            // Assert
            assertThat(iterador.hasNext())
                .isTrue();
        }
    }
}