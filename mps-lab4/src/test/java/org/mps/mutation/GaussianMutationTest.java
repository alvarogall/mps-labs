package org.mps.mutation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

public class GaussianMutationTest {
    @DisplayName("Probar el constructor GaussianMutation")
    @Nested
    public class GaussianMutationConstructorTest {
        @Test
        @DisplayName("Crear una instancia de mutación gaussiana sin parámetros inicializa ratio y desviación a cero")
        public void gaussianMutation_DefaultConstructor_CreatedInstance() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] individuos = {1, 2, 3};

            // Act
            GaussianMutation mutation = new GaussianMutation();

            // Assert
            assertTrue(Arrays.equals(individuos, mutation.mutate(individuos)));
        }

        @Test
        @DisplayName("Crear una instancia de mutación gaussiana con parámetros inicializa ratio y desviación a esos parámetros")
        public void gaussianMutation_ParametersConstructorZeroRatio_CreatedInstance() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] individuos = {1, 2, 3};
            double mutationRate = 0.0;
            double standardDeviation = 0.7;

            // Act
            GaussianMutation mutation = new GaussianMutation(mutationRate, standardDeviation);

            // Assert
            assertTrue(Arrays.equals(individuos, mutation.mutate(individuos)));
        }
    }

    @DisplayName("Probar el método mutate")
    @Nested
    public class MutateTest {
        @Test
        @DisplayName("Mutar array nulo lanza excepción")
        public void mutate_NullIndividual_ThrowsException() {
            // Arrange
            GaussianMutation mutation = new GaussianMutation();
            
            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                mutation.mutate(null);
            });
        }

        @Test
        @DisplayName("Mutar array vacío lanza excepción")
        public void mutate_EmptyIndividual_ThrowsException() {
            // Arrange
            GaussianMutation mutation = new GaussianMutation();
            int[] empty = {};
            
            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                mutation.mutate(empty);
            });
        }

        @Test
        @DisplayName("Mutar con standardDeviation o mutationRate distinto de cero devuelve el mismo número de individuos mutados o no")
        public void mutate_ValidIndividual_ReturnsSameLength() throws EvolutionaryAlgorithmException {
            // Arrange
            GaussianMutation mutation = new GaussianMutation(1.0, 0.5);
            int[] individual = {1, 2, 3};
            
            // Act
            int[] result = mutation.mutate(individual);
            
            // Assert
            assertEquals(individual.length, result.length);
        }

        @Test
        @DisplayName("Mutar con mutationRate igual a cero no muta")
        public void mutate_ZeroMutationRate_ReturnsSameValues() throws EvolutionaryAlgorithmException {
            // Arrange
            GaussianMutation mutation = new GaussianMutation(0.0, 0.5);
            int[] individual = {1, 2, 3};
            
            // Act
            int[] result = mutation.mutate(individual);
            
            // Assert
            assertTrue(Arrays.equals(individual, result));
        }

        @Test
        @DisplayName("Mutar con standardDeviation igual a cero no muta")
        public void mutate_ZeroStandardDeviation_ReturnsSameValues() throws EvolutionaryAlgorithmException {
            // Arrange
            GaussianMutation mutation = new GaussianMutation(1.0, 0.0);
            int[] individual = {1, 2, 3};
            
            // Act
            int[] result = mutation.mutate(individual);
            
            // Assert
            assertTrue(Arrays.equals(individual, result));
        }
    }
}