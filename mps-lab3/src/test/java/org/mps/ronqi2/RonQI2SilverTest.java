package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.dispositivo.Dispositivo;
import org.mps.dispositivo.DispositivoSilver;

public class RonQI2SilverTest {
    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */





    @DisplayName("Probar el método inicializar")
    @Nested
    public class InicializarTest {
        private RonQI2 ronquidos;

        @DisplayName("Se inicializa RonQI2Silver")
        @BeforeEach
        public void startUp() {
            // Arrange
            ronquidos = new RonQI2Silver();
        }

        @DisplayName("Ambos sensores conectados y configurados")
        @Test
        public void inicializar_BothSensorsConnectedAndConfigured_ReturnsTrue() {
            // Arrange
            Dispositivo dispositivo = mock(DispositivoSilver.class);
            ronquidos.anyadirDispositivo(dispositivo);

            when(dispositivo.conectarSensorPresion()).thenReturn(true);
            when(dispositivo.configurarSensorPresion()).thenReturn(true);
            when(dispositivo.conectarSensorSonido()).thenReturn(true);
            when(dispositivo.configurarSensorSonido()).thenReturn(true);

            // Act
            boolean resultado = ronquidos.inicializar();

            // Assert
            assertTrue(resultado);
            verify(dispositivo, times(1)).configurarSensorPresion();
            verify(dispositivo, times(1)).configurarSensorSonido();
        }

        @Test
        @DisplayName("Sensor de presión conectado y sensor de sonido no conectado")
        public void inicializar_PressureConnectedButSoundNotConnected_ReturnsFalse() {
            // Arrange
            Dispositivo dispositivo = mock(DispositivoSilver.class);
            ronquidos.anyadirDispositivo(dispositivo);

            when(dispositivo.conectarSensorPresion()).thenReturn(true);
            when(dispositivo.conectarSensorSonido()).thenReturn(false);

            // Act
            boolean resultado = ronquidos.inicializar();

            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Sensor de sonido conectado y sensor de presión no conectado")
        public void inicializar_SoundConnectedButPressureNotConnected_ReturnsFalse() {
            // Arrange
            Dispositivo dispositivo = mock(DispositivoSilver.class);
            ronquidos.anyadirDispositivo(dispositivo);

            when(dispositivo.conectarSensorPresion()).thenReturn(false);
            when(dispositivo.conectarSensorSonido()).thenReturn(true);

            // Act
            boolean resultado = ronquidos.inicializar();

            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Ambos sensores conectados pero presión no configura")
        public void inicializar_BothConnectedButPressureNotConfigured_ReturnsFalse() {
            // Arrange
            Dispositivo dispositivo = mock(DispositivoSilver.class);
            ronquidos.anyadirDispositivo(dispositivo);

            when(dispositivo.conectarSensorPresion()).thenReturn(true);
            when(dispositivo.configurarSensorPresion()).thenReturn(false);
            when(dispositivo.conectarSensorSonido()).thenReturn(true);
            when(dispositivo.configurarSensorSonido()).thenReturn(true);

            // Act
            boolean resultado = ronquidos.inicializar();

            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Ambos sensores conectados pero sonido no configura")
        public void inicializar_BothConnectedButSoundNotConfigured_ReturnsFalse() {
            // Arrange
            Dispositivo dispositivo = mock(DispositivoSilver.class);
            ronquidos.anyadirDispositivo(dispositivo);

            when(dispositivo.conectarSensorPresion()).thenReturn(true);
            when(dispositivo.configurarSensorPresion()).thenReturn(true);
            when(dispositivo.conectarSensorSonido()).thenReturn(true);
            when(dispositivo.configurarSensorSonido()).thenReturn(false);

            // Act
            boolean resultado = ronquidos.inicializar();

            // Assert
            assertFalse(resultado);
        }        
    }

    @DisplayName("Probar el método evaluarApneaSuenyo")
    @Nested
    public class evaluarApneaSuenyo {
        private RonQI2 ronquidos;

        @DisplayName("Se inicializa RonQI2Silver")
        @BeforeEach
        public void startUp() {
            // Arrange
            ronquidos = new RonQI2Silver();
        }



    }
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     * /
     
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */

}