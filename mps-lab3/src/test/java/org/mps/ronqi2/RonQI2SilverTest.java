package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mps.dispositivo.Dispositivo;
import org.mps.dispositivo.DispositivoSilver;

public class RonQI2SilverTest {
    @DisplayName("Probar el método inicializar")
    @Nested
    public class InicializarTest {
        RonQI2Silver ronQI2;
        DispositivoSilver dispositivoSilverMock;

        @DisplayName("Se inicializa ronQI2 para cada test")
        @BeforeEach
        public void startUp() {
            // Arrange
            ronQI2 = new RonQI2Silver();
            dispositivoSilverMock = mock(DispositivoSilver.class);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);
        }

        @DisplayName("Ambos sensores conectados y configurados")
        @Test
        public void inicializar_BothSensorsConnectedAndConfigured_ReturnsTrue() {
            // Arrange
            when(dispositivoSilverMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilverMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoSilverMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilverMock.configurarSensorSonido()).thenReturn(true);

            // Act
            boolean resultado = ronQI2.inicializar();

            // Assert
            assertTrue(resultado);
            verify(dispositivoSilverMock, times(1)).configurarSensorPresion();
            verify(dispositivoSilverMock, times(1)).configurarSensorSonido();
        }

        @Test
        @DisplayName("Sensor de presión conectado y sensor de sonido no conectado")
        public void inicializar_PressureConnectedButSoundNotConnected_ReturnsFalse() {
            // Arrange
            when(dispositivoSilverMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilverMock.conectarSensorSonido()).thenReturn(false);

            // Act
            boolean resultado = ronQI2.inicializar();

            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Sensor de sonido conectado y sensor de presión no conectado")
        public void inicializar_SoundConnectedButPressureNotConnected_ReturnsFalse() {
            // Arrange
            when(dispositivoSilverMock.conectarSensorPresion()).thenReturn(false);
            when(dispositivoSilverMock.conectarSensorSonido()).thenReturn(true);

            // Act
            boolean resultado = ronQI2.inicializar();

            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Ambos sensores conectados pero presión no configura")
        public void inicializar_BothConnectedButPressureNotConfigured_ReturnsFalse() {
            // Arrange
            when(dispositivoSilverMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilverMock.configurarSensorPresion()).thenReturn(false);
            when(dispositivoSilverMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilverMock.configurarSensorSonido()).thenReturn(true);

            // Act
            boolean resultado = ronQI2.inicializar();

            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Ambos sensores conectados pero sonido no configura")
        public void inicializar_BothConnectedButSoundNotConfigured_ReturnsFalse() {
            // Arrange
            when(dispositivoSilverMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilverMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoSilverMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilverMock.configurarSensorSonido()).thenReturn(false);

            // Act
            boolean resultado = ronQI2.inicializar();

            // Assert
            assertFalse(resultado);
        }        
    }
      
    @DisplayName("Probar el método obtenerNuevaLectura")
    @Nested
    public class ObtenerNuevaLecturaTest {
        RonQI2Silver ronQI2;
        DispositivoSilver dispositivoSilverMock;
    
        @DisplayName("Se inicializa ronQI2 para cada test")
        @BeforeEach
        public void startUp() {
            // Arrange
            ronQI2 = new RonQI2Silver();
            dispositivoSilverMock = mock(DispositivoSilver.class);
            ronQI2.anyadirDispositivo(dispositivoSilverMock);
        }

        @Test
        @DisplayName("Obtener lectura con o sin lectura previas")
        public void obtenerNuevaLectura_ConOSinLecturasPrevias_CreaLectura() {
            // Arrange
            when(dispositivoSilverMock.leerSensorPresion()).thenReturn(15.0f);
            when(dispositivoSilverMock.leerSensorSonido()).thenReturn(25.0f);

            // Act
            ronQI2.obtenerNuevaLectura();

            // Assert
            verify(dispositivoSilverMock, times(1)).leerSensorPresion();
            verify(dispositivoSilverMock, times(1)).leerSensorSonido();
            assertFalse(ronQI2.evaluarApneaSuenyo());
        }

        @Test
        @DisplayName("Obtener lectura con lecturas previas")
        public void obtenerNuevaLectura_ConLecturasPrevias_CreaLecturas() {
            // Arrange
            when(dispositivoSilverMock.leerSensorPresion()).thenReturn(10.0f);
            when(dispositivoSilverMock.leerSensorSonido()).thenReturn(20.0f);

            ronQI2.obtenerNuevaLectura();

            when(dispositivoSilverMock.leerSensorPresion()).thenReturn(30.0f);
            when(dispositivoSilverMock.leerSensorSonido()).thenReturn(40.0f);

            // Act
            ronQI2.obtenerNuevaLectura();

            // Assert
            assertFalse(ronQI2.evaluarApneaSuenyo());
        }

        @Test
        @DisplayName("Obtener lectura con tamaño lleno")
        public void obtenerNuevaLectura_TamañoLleno_EliminaUltimaLectura() {
            // Arrange
            when(dispositivoSilverMock.leerSensorPresion()).thenReturn(20.0f);
            when(dispositivoSilverMock.leerSensorSonido()).thenReturn(30.0f);
            
            for (int i = 0; i < 5; i++) {
                ronQI2.obtenerNuevaLectura();
            }

            when(dispositivoSilverMock.leerSensorPresion()).thenReturn(40.0f);
            when(dispositivoSilverMock.leerSensorSonido()).thenReturn(50.0f);

            // Act
            ronQI2.obtenerNuevaLectura();

            // Assert
            assertTrue(ronQI2.evaluarApneaSuenyo()); 
        }
    }

    @DisplayName("Probar el método anyadirDispositivo")
    @Nested
    public class AnyadirDispositivoTest {
        private RonQI2Silver ronQI2;
        private Dispositivo dispositivoMock;

        @BeforeEach
        public void setUp() {
            // Arrange
            ronQI2 = new RonQI2Silver();
            dispositivoMock = mock(Dispositivo.class);
        }

        @Test
        @DisplayName("Asignar dispositivo cuando no hay dispositivo previo")
        public void anyadirDispositivo_NoExistingDevice_AsignaNuevoDispositivo() {
            // Act
            ronQI2.anyadirDispositivo(dispositivoMock);
            
            // Assert
            assertNotNull(ronQI2.disp);
            assertEquals(dispositivoMock, ronQI2.disp);
        }

        @Test
        @DisplayName("Reemplazar dispositivo existente con uno nuevo")
        public void anyadirDispositivo_ExistingDevice_ReemplazaDispositivo() {
            // Arrange
            Dispositivo oldDeviceMock = mock(Dispositivo.class);
            ronQI2.anyadirDispositivo(oldDeviceMock);
            
            // Act
            ronQI2.anyadirDispositivo(dispositivoMock);
            
            // Assert
            assertEquals(dispositivoMock, ronQI2.disp);
            assertNotEquals(oldDeviceMock, ronQI2.disp);
        }
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
       public void reconectar_DispositivoConectadoNoConectaSensorPresionConectaSensorSonido_ReturnsFalse() {
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
       public void reconectar_DispositivoConectadoConectaSensorPresionNoConectaSensorSonido_ReturnsFalse() {
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
       public void reconectar_DispositivoConectadoNoConectaSensorPresionNoConectaSensorSonido_ReturnsFalse() {
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
       public void reconectar_DispositivoConectadoConectaSensorPresionConectaSensorSonido_ReturnsTrue() {
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
  
    @DisplayName("Probar el método evaluarApneaSuenyo")
    @Nested
    public class EvaluarApneaSuenyoTest {
        RonQI2Silver ronQI2;
        DispositivoSilver dispositivoMock;

        @DisplayName("Se inicializa ronQI2 para cada test")
        @BeforeEach
        public void startUp() {
            // Arrange
            ronQI2 = new RonQI2Silver();
            dispositivoMock = mock(DispositivoSilver.class);
            ronQI2.anyadirDispositivo(dispositivoMock);
        }

        @Test
        @DisplayName("Sin lecturas devuelve false")
        public void evaluarApneaSuenyo_SinLecturas_DevuelveFalse() {
            // Act
            boolean resultado = ronQI2.evaluarApneaSuenyo();

            // Assert
            assertFalse(resultado);
        }

        @ParameterizedTest
        @CsvSource({
            "25.0f, 35.0f, 25.0f, 35.0f, true",
            "0.0f, 0.0f, 80.0f, 80.0f, true",
            "80.0f, 10.0f, 15.0f, 15.0f, false",
            "10.0f, 10.0f, 15.0f, 80.0f, false",
            "10.0f, 10.0f, 15.0f, 15.0f, false",
            "0.0f, 0.0f, 0.0f, 0.0f, false",
            "20.0f, 30.0f, 20.0f, 30.0f, false"            
        })
        @DisplayName("Pruebas parametrizadas de apnea")
        public void testEvaluarApnea(float presion1, float sonido1, float presion2, float sonido2, boolean esperado) {
            // Arrange
            when(dispositivoMock.leerSensorPresion()).thenReturn(presion1).thenReturn(presion2);
            when(dispositivoMock.leerSensorSonido()).thenReturn(sonido1).thenReturn(sonido2);

            ronQI2.obtenerNuevaLectura();
            ronQI2.obtenerNuevaLectura();

            // Act         
            boolean resultado = ronQI2.evaluarApneaSuenyo();

            // Assert
            assertEquals(esperado, resultado);
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
           ronQI2.anyadirDispositivo(dispositivoSilverMock);
       }

       @DisplayName("El método estaConectado devuelve true si el dispositivo está conectado")
       @Test
       public void estaConectado_DispositivoConectado_DevuelveTrue() {
           // Arrange
           when(dispositivoSilverMock.estaConectado()).thenReturn(true);           

           // Act
           boolean conectado = ronQI2.estaConectado();

           // Assert
           assertTrue(conectado);
       }

       @DisplayName("El método estaConectado devuelve false si el dispositivo no está conectado")
       @Test
       public void estaConectado_DispositivoNoConectado_DevuelveFalse() {
           // Arrange
           when(dispositivoSilverMock.estaConectado()).thenReturn(false);

           // Act
           boolean conectado = ronQI2.estaConectado();

           // Assert
           assertFalse(conectado);
       }
    }
}