package org.mps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.TwoPointCrossover;
import org.mps.mutation.GaussianMutation;
import org.mps.mutation.MutationOperator;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

public class EvolutionaryAlgorithmTest {
    @DisplayName("Probar el constructor EvolutionaryAlgorithm")
    @Nested
    public class EvolutionaryAlgorithmConstructorTest {
        @Test
        @DisplayName("Crear una instancia de algoritmo evolutivo con operador de selección nulo lanza excepción")
        public void EvolutionaryAlgorithm_NullSelectionOperator_ThrowsException() {
            // Arrange
            MutationOperator mutationOperator = new GaussianMutation();
            CrossoverOperator crossoverOperator = new TwoPointCrossover();

            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new EvolutionaryAlgorithm(null, mutationOperator, crossoverOperator);
            });
        }

        @Test
        @DisplayName("Crear una instancia de algoritmo evolutivo con operador de mutación nulo lanza excepción")
        public void EvolutionaryAlgorithm_NullMutationOperator_ThrowsException() throws EvolutionaryAlgorithmException {
            // Arrange
            SelectionOperator selectionOperator = new TournamentSelection(1);
            CrossoverOperator crossoverOperator = new TwoPointCrossover();

            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new EvolutionaryAlgorithm(selectionOperator, null, crossoverOperator);
            });
        }

        @Test
        @DisplayName("Crear una instancia de algoritmo evolutivo con operador de cruce nulo lanza excepción")
        public void EvolutionaryAlgorithm_NullCrossoverOperator_ThrowsException() throws EvolutionaryAlgorithmException {
            // Arrange
            SelectionOperator selectionOperator = new TournamentSelection(1);
            MutationOperator mutationOperator = new GaussianMutation();

            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new EvolutionaryAlgorithm(selectionOperator, mutationOperator, null);
            });
        }

        @Test
        @DisplayName("Crear una instancia de algoritmo evolutivo con argumentos válidos crea el objeto correctamente")
        public void EvolutionaryAlgorithm_ValidArguments_ObjectCreated() throws EvolutionaryAlgorithmException {
            // Arrange
            SelectionOperator selectionOperator = new TournamentSelection(1);
            MutationOperator mutationOperator = new GaussianMutation();
            CrossoverOperator crossoverOperator = new TwoPointCrossover();

            // Act
            EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);

            // Assert
            assertNotNull(algorithm);
        }
    }

    @DisplayName("Probar el método optimize")
    @Nested
    public class OptimizeTest {
        private EvolutionaryAlgorithm algorithm;

        @BeforeEach
        public void setUp() throws EvolutionaryAlgorithmException {
            SelectionOperator selectionOperator = new TournamentSelection(1);
            MutationOperator mutationOperator = new GaussianMutation();
            CrossoverOperator crossoverOperator = new TwoPointCrossover();
            algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
        }

        @Test
        @DisplayName("Optimizar población nula lanza excepción")
        public void optimize_NullPopulation_ThrowsException() {
            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                algorithm.optimize(null);
            });
        }

        @Test
        @DisplayName("Optimizar población vacía lanza excepción")
        public void optimize_EmptyPopulation_ThrowsException() {
            // Arrange
            int[][] emptyPopulation = new int[0][];

            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                algorithm.optimize(emptyPopulation);
            });
        }

        @Test
        @DisplayName("Optimizar población de un individuo devuelve misma población")
        public void optimize_OneIndividualPopulation_ReturnsSamePopulation() throws EvolutionaryAlgorithmException {
            // Arrange
            int[][] oneIndividualPopulation = {
                {1, 2, 3}
            };

            // Act
            int[][] optimizedPopulation = algorithm.optimize(oneIndividualPopulation);

            // Assert
            assertTrue(Arrays.equals(oneIndividualPopulation, optimizedPopulation));
        }

        @Test
        @DisplayName("Optimizar población de más de un individuo devuelve una población del mismo tamaño")
        public void optimize_MultipleIndividualPopulation_ReturnsSameSizePopulation() throws EvolutionaryAlgorithmException {
            // Arrange
            int[][] multipleIndividualPopulation = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12},
                {13, 14, 15}
            };

            // Act
            int[][] optimizedPopulation = algorithm.optimize(multipleIndividualPopulation);

            // Assert
            assertEquals(multipleIndividualPopulation.length, optimizedPopulation.length);
        }
    }    
}