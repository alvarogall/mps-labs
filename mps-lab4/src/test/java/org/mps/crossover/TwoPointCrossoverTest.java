package org.mps.crossover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;
import org.mps.mutation.GaussianMutation;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

public class TwoPointCrossoverTest {
    @DisplayName("Probar que el constructor se crea correctamente")
    @Test
    public void twoPointCrossover_FuncionaCorrectamente() {
        //Arrange
        TwoPointCrossover twoPointCrossover;

        //Act
        twoPointCrossover = new TwoPointCrossover();

        //Assert
        assertNotNull(twoPointCrossover);
    }

    @DisplayName("Probar el método crossover")
    @Nested
    public class CrossoverTest {
        TwoPointCrossover twoPointCrossover;

        @DisplayName("Se inicializa twoPointCrossover para cada test")
        @BeforeEach
        public void startUp() {
            //Arrange
            twoPointCrossover = new TwoPointCrossover();
        }

        @DisplayName("Cruzar con array padre 1 nulo lanza excepción")
        @Test
        public void crossover_NullParent1_ThrowsException() {
            // Arrange
            int[] parent1 = null;
            int[] parent2 = {1, 3, 4};
            
            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                twoPointCrossover.crossover(parent1, parent2);
            });
        }
        
        @DisplayName("Cruzar array padre 2 nulo lanza excepción")
        @Test
        public void crossover_NullParent2_ThrowsException() {
            // Arrange
            int[] parent1 = {1, 3, 4};
            int[] parent2 = null;
            
            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                twoPointCrossover.crossover(parent1, parent2);
            });
        }

        @DisplayName("Cruzar array padre 1 con longitud menor o igual que 1 lanza excepción")
        @Test
        public void crossover_Parent2LengthLessThanOrEqualTo1_ThrowsException() {
            // Arrange
            int[] parent1 = {2};
            int[] parent2 = {1, 3, 4};
            
            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                twoPointCrossover.crossover(parent1, parent2);
            });
        }
        
        @DisplayName("Cruzar array padre 1 con longitud diferente al array padre 2 lanza excepción")
        @Test
        public void crossover_Parent2LengthDifferentToParent2Length_ThrowsException() {
            // Arrange
            int[] parent1 = {2, 5, 3, 7};
            int[] parent2 = {1, 3, 4};
            
            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                twoPointCrossover.crossover(parent1, parent2);
            });
        }

        @DisplayName("Cruzar arrays válidos de longitud mínima (2) devuelve la misma longitud de offspring")
        @Test
        public void crossover_ValidParentsMininumLength_ReturnsSameLength() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] parent1 = {1, 2};
            int[] parent2 = {3, 4};

            // Act
            int[][] offspring = twoPointCrossover.crossover(parent1, parent2);

            // Assert
            assertEquals(parent1.length, offspring[0].length);
            assertEquals(parent2.length, offspring[1].length);
        }

        @DisplayName("Cruzar arrays válidos de longitud mayor a la mínima devuelve la misma longitud de offspring")
        @Test
        public void crossover_ValidParentsLongerLengthThanMinimum_ReturnsSameLength() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] parent1 = {1, 2, 3, 4, 5, 6};
            int[] parent2 = {7, 8, 9, 10, 11, 12};

            // Act
            int[][] offspring = twoPointCrossover.crossover(parent1, parent2);

            // Assert
            assertEquals(parent1.length, offspring[0].length);
            assertEquals(parent2.length, offspring[1].length);
        }

    }
}
