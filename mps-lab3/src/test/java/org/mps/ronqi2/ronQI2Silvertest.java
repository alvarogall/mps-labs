package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.dispositivo.Dispositivo;
import org.mps.dispositivo.DispositivoSilver;

public class RonQI2SilverTest {

    
    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    
    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */

     @DisplayName("El método anyadirDispositivo asigna correctamente el dispositivo")
     @Test
     public void anyadirDispositivo_AsignaDispositivoCorrectamente() {
        // Arrange
        Dispositivo dispositivoMock = mock(Dispositivo.class);
        when(dispositivoMock.estaConectado()).thenReturn(true);
        RonQI2 ronQI2 = new RonQI2Silver();

        // Act
        ronQI2.anyadirDispositivo(dispositivoMock);

        // Assert
        assertTrue(ronQI2.estaConectado());
     }

     @DisplayName("Probar el método reconectar")
     @Nested
     public class ReconectarTest {
        RonQI2 ronQI2;
        DispositivoSilver dispositivoSilverMock;

        @DisplayName("Se inicializa ronQI2 para cada test")
        @BeforeEach
        public void startUp() {
            //Arrange
            ronQI2 = new RonQI2Silver();
            dispositivoSilverMock = mock(DispositivoSilver.class);
        }

        @DisplayName("El método reconectar devuelve false si el dispositivo está conectado")
        @Test
        public void reconectar_DispositivoConectado_ReturnsFalse() {
            //Arrange
            when(dispositivoSilverMock.estaConectado()).thenReturn(true);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            //Act
            boolean resultado = ronQI2.reconectar();

            //Assert
            assertFalse(resultado);
        }

        @DisplayName("El método reconectar devuelve false si el dispositivo está desconectado, no se conecta el sensor de presión y sí se conecta el sensor de sonido")
        @Test
        public void reconectar_DispositivoConectado_NoConectaSensorPresion_ConectaSensorSonido() {
            //Arrange
            when(dispositivoSilverMock.estaConectado()).thenReturn(false);
            when(dispositivoSilverMock.conectarSensorPresion()).thenReturn(false);
            when(dispositivoSilverMock.conectarSensorSonido()).thenReturn(true);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            //Act
            boolean resultado = ronQI2.reconectar();

            //Assert
            assertFalse(resultado);
        }

        @DisplayName("El método reconectar devuelve false si el dispositivo está desconectado, sí se conecta el sensor de presión y no se conecta el sensor de sonido")
        @Test
        public void reconectar_DispositivoConectado_ConectaSensorPresion_NoConectaSensorSonido() {
            //Arrange
            when(dispositivoSilverMock.estaConectado()).thenReturn(false);
            when(dispositivoSilverMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilverMock.conectarSensorSonido()).thenReturn(false);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            //Act
            boolean resultado = ronQI2.reconectar();

            //Assert
            assertFalse(resultado);
        }

        @DisplayName("El método reconectar devuelve false si el dispositivo está desconectado, no se conecta el sensor de presión y no se conecta el sensor de sonido")
        @Test
        public void reconectar_DispositivoConectado_NoConectaSensorPresion_NoConectaSensorSonido() {
            //Arrange
            when(dispositivoSilverMock.estaConectado()).thenReturn(false);
            when(dispositivoSilverMock.conectarSensorPresion()).thenReturn(false);
            when(dispositivoSilverMock.conectarSensorSonido()).thenReturn(false);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            //Act
            boolean resultado = ronQI2.reconectar();

            //Assert
            assertFalse(resultado);
        }

        @DisplayName("El método reconectar devuelve true si el dispositivo está desconectado, sí se conecta el sensor de presión y sí se conecta el sensor de sonido")
        @Test
        public void reconectar_DispositivoConectado_ConectaSensorPresion_ConectaSensorSonido() {
            //Arrange
            when(dispositivoSilverMock.estaConectado()).thenReturn(false);
            when(dispositivoSilverMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilverMock.conectarSensorSonido()).thenReturn(true);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            //Act
            boolean resultado = ronQI2.reconectar();

            //Assert
            assertTrue(resultado);
            verify(dispositivoSilverMock, times(1)).conectarSensorPresion();
            verify(dispositivoSilverMock, times(1)).conectarSensorSonido();
        }
     }
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     */
     
     @DisplayName("Probar el método obtenerNuevaLectura")
     @Nested
     public class ObtenerNuevaLecturaTest {
        RonQI2 ronQI2;
        DispositivoSilver dispositivoSilverMock;

        @DisplayName("Se inicializa ronQI2 para cada test")
        @BeforeEach
        public void startUp() {
            //Arrange
            ronQI2 = new RonQI2Silver();
            dispositivoSilverMock = mock(DispositivoSilver.class);
        }

        @DisplayName("El método obtenerNuevaLectura lee una vez los sensores si se le llama una vez")
        @Test
        public void obtenerNuevaLectura_UnaLecturaSensores_LLamaUnaVezACadaLeerSensor() {
            //Arrange
            when(dispositivoSilverMock.leerSensorPresion()).thenReturn(15.0f);
            when(dispositivoSilverMock.leerSensorSonido()).thenReturn(10.0f);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            //Act
            ronQI2.obtenerNuevaLectura();

            //Assert
            verify(dispositivoSilverMock, times(1)).leerSensorPresion();
            verify(dispositivoSilverMock, times(1)).leerSensorSonido();
        }
        
        @DisplayName("El método obtenerNuevaLectura lee los sensores cinco veces si se le llama cinco veces")
        @Test
        public void obtenerNuevaLectura_5LecturasSensores_LLama5VecesACadaLeerSensor() {
            //Arrange
            when(dispositivoSilverMock.leerSensorPresion()).thenReturn(15.0f);
            when(dispositivoSilverMock.leerSensorSonido()).thenReturn(10.0f);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            //Act
            for(int i = 0; i < 5; ++i) ronQI2.obtenerNuevaLectura();

            //Assert
            verify(dispositivoSilverMock, times(5)).leerSensorPresion();
            verify(dispositivoSilverMock, times(5)).leerSensorSonido();
        }
        
        @DisplayName("El método obtenerNuevaLectura llama más veces al método (7) que el número de lecturas que se pueden almacenar (5) por lo que lee 7 veces los dos sensores")
        @Test
        public void obtenerNuevaLectura_LLamaMasVecesNumeroLecturasMax_Llama7VecesACadaLeerSensor() {
            //Arrange
            when(dispositivoSilverMock.leerSensorPresion()).thenReturn(15.0f);
            when(dispositivoSilverMock.leerSensorSonido()).thenReturn(10.0f);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            //Act
            for(int i = 0; i < 7; ++i) ronQI2.obtenerNuevaLectura();

            //Assert
            verify(dispositivoSilverMock, times(7)).leerSensorPresion();
            verify(dispositivoSilverMock, times(7)).leerSensorSonido();
        }
     }

     @DisplayName("Probar el método estaConectado")
     @Nested
     public class EstaConectadoTest {
        RonQI2 ronQI2;
        DispositivoSilver dispositivoSilverMock;

        @DisplayName("Se inicializa ronQI2 para cada test")
        @BeforeEach
        public void startUp() {
            //Arrange
            ronQI2 = new RonQI2Silver();
            dispositivoSilverMock = mock(DispositivoSilver.class);
        }

        @DisplayName("El método estaConectado devuelve true si el dispositivo está conectado")
        @Test
        public void estaConectado_DispositivoNoConectado_DevuelveTrue() {
            // Arrange
            when(dispositivoSilverMock.estaConectado()).thenReturn(true);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            // Act
            boolean conectado = ronQI2.estaConectado();

            // Assert
            assertTrue(conectado);
        }

        @DisplayName("El método estaConectado devuelve true si el dispositivo no está conectado")
        @Test
        public void estaConectado_DispositivoNoConectado_DevuelveFalse() {
            // Arrange
            when(dispositivoSilverMock.estaConectado()).thenReturn(false);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);

            // Act
            boolean conectado = ronQI2.estaConectado();

            // Assert
            assertFalse(conectado);
        }
     }

     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
