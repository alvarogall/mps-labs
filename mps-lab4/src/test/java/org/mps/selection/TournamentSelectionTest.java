package org.mps.selection;

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
            //Arrange
            TournamentSelection tournamentSelection;
            int tournamentSize = 9;

            //Act
            tournamentSelection = new TournamentSelection(tournamentSize);

            //Assert
            assertNotNull(tournamentSelection);
        }

        @DisplayName("El constructor creado con un tamaño de torneo menor o igual a 0 lanza excepción")
        @Test
        public void TournamentSelection_TournamentSizeMenorIgualCero_LanzaExcepcion() throws EvolutionaryAlgorithmException {
            // Arrange
            int tournamentSize = 0;

            //Act, Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new TournamentSelection(tournamentSize);
            });
        }
    }

    @DisplayName("Probar el método crossover")
    @Nested
    public class CrossoverTest {
        TwoPointCrossover twoPointCrossover;

        @DisplayName("Se inicializa twoPointCrossover para cada test")
        @BeforeEach
        public void startUp() {
            twoPointCrossover = new TwoPointCrossover();
        }

        

    }
}
