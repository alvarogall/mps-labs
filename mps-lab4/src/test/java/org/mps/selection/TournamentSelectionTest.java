package org.mps.selection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;
import org.mps.crossover.TwoPointCrossover;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

 //YOOOOOOOO

public class TournamentSelectionTest {
    @DisplayName("Probar el constructor")
    @Nested
    public class TournamentSelectionConstructorTest {
        @DisplayName("El constructor creado con un tamaño de torneo positivo se crea correctamente")
        @Test
        public void TournamentSelection_TournamentSizePositivo_SeCreaCorrectamente() throws EvolutionaryAlgorithmException {
            // Arrange
            TournamentSelection tournamentSelection;
            int tournamentSize = 9;

            // Act
            tournamentSelection = new TournamentSelection(tournamentSize);

            // Assert
            assertNotNull(tournamentSelection);
        }

        @DisplayName("El constructor creado con un tamaño de torneo menor o igual a 0 lanza excepción")
        @Test
        public void TournamentSelection_TournamentSizeMenorIgualCero_LanzaExcepcion() throws EvolutionaryAlgorithmException {
            // Arrange
            int tournamentSize = 0;

            // Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new TournamentSelection(tournamentSize);
            });
        }
    }

    @DisplayName("Probar el método select")
    @Nested
    public class SelectTest {
        TournamentSelection tournamentSelection;

        @DisplayName("Se inicializa tournamentSelection para cada test")
        @BeforeEach
        public void startUp() throws EvolutionaryAlgorithmException {
            // Arrange
            tournamentSelection = new TournamentSelection(7);
        }

        @DisplayName("Seleccionar con array nulo lanza excepción")
        @Test
        public void select_NullPopulation_ThrowsException() {
            // Arrange
            int[] population = null;

            // Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                tournamentSelection.select(population);
            });
        }

        @DisplayName("Seleccionar con array vacío lanza excepción")
        @Test
        public void select_EmptyPopulation_ThrowsException() {
            // Arrange
            int[] population = new int[0];

            // Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                tournamentSelection.select(population);
            });
        }

        @DisplayName("Seleccionar con array con tamaño menor o igual al tamaño del torneo lanza excepción")
        @Test
        public void select_PopulationSmallerThanTournamentSize_ThrowsException() {
            // Arrange
            int[] population = {1, 2, 3, 4, 5, 6, 7};

            // Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                tournamentSelection.select(population);
            });
        }

        @DisplayName("Cruzar con población válida devuelve array del mismo tamaño")
        @Test
        public void select_ValidPopulation_ReturnsSameSizeArray() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] population = {5, 8, 12, 4, 7, 10, 6, 3, 9, 2};

            // Act
            int[] selected = tournamentSelection.select(population);

            // Assert
            assertEquals(population.length, selected.length);
        }
    }
}
