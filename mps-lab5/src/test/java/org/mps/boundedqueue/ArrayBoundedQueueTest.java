package org.mps.boundedqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
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

        //@DisplayName("")
        //@Test

    }


}
