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
    public class EvaluarApneaSuenyoTest {
        private RonQI2 ronquidos;

        @DisplayName("Se inicializa RonQI2Silver")
        @BeforeEach
        public void startUp() {
            // Arrange
            ronquidos = new RonQI2Silver();
        }



    }
}