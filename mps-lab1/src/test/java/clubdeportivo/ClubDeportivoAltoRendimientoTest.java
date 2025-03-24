package clubdeportivo;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoAltoRendimientoTest {
    @DisplayName("Crear un club deportivo de alto rendimiento con nombre no null, máximo de personas por grupo mayor que 0 e incremento mayor que 0")
    @Test
    public void ClubDeportivoAltoRendimiento_NombreNoNullMaximoMayorCeroIncrementoMayorCero_ClubCreado() throws ClubException {
        // Arrange
        String nombre = "club1";
        int maximo = 25;
        double incremento = 23.47;

        // Act
        ClubDeportivoAltoRendimiento club1 = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);

        // Assert
        assertEquals("club1 --> [  ]", club1.toString());
    }

    @DisplayName("Crear un club deportivo de alto rendimiento con nombre null, máximo de personas por grupo mayor que 0 e incremento mayor que 0")
    @Test
    public void ClubDeportivoAltoRendimiento_NombreNullMaximoMayorCeroIncrementoMayorCero_LanzaExcepcion() {
        // Arrange
        String nombre = null;
        int maximo = 25;
        double incremento = 23.47;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        });
    }

    @DisplayName("Crear un club deportivo de alto rendimiento con nombre no null, máximo de personas por grupo menor o igual que 0 e incremento mayor que 0")
    @Test
    public void ClubDeportivoAltoRendimiento_NombreNoNullMaximoMenorIgualCeroIncrementoMayorCero_LanzaExcepcion() {
        // Arrange
        String nombre = "club1";
        int maximo = -1;
        double incremento = 23.47;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        });
    }

    @DisplayName("Crear un club deportivo de alto rendimiento con nombre no null, máximo de personas por grupo mayor que 0 e incremento menor o igual que 0")
    @Test
    public void ClubDeportivoAltoRendimiento_NombreNoNullMaximoMayorCeroIncrementoMenorIgualCero_LanzaExcepcion() {
        // Arrange
        String nombre = "club1";
        int maximo = 25;
        double incremento = -3.47;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        });
    }

    @DisplayName("Crear un club deportivo de alto rendimiento con nombre no null, número de grupos mayor que cero, máximo de personas por grupo mayor que 0 e incremento mayor que 0")
    @Test
    public void ClubDeportivoAltoRendimiento_NombreNoNullNgruposMayorCeroMaximoMayorCeroIncrementoMayorCero_ClubCreado() throws ClubException {
        // Arrange
        String nombre = "club1";
        int nGrupos = 20;
        int maximo = 25;
        double incremento = 23.47;

        // Act
        ClubDeportivoAltoRendimiento club1 = new ClubDeportivoAltoRendimiento(nombre, nGrupos, maximo, incremento);

        // Assert
        assertEquals("club1 --> [  ]", club1.toString());
    }

    @DisplayName("Crear un club deportivo de alto rendimiento con nombre null, número de grupos mayor que cero, máximo de personas por grupo mayor que 0 e incremento mayor que 0")
    @Test
    public void ClubDeportivoAltoRendimiento_NombreNullNgruposMayorCeroMaximoMayorCeroIncrementoMayorCero_LanzaExcepcion() {
        // Arrange
        String nombre = null;
        int nGrupos = 20;
        int maximo = 25;
        double incremento = 23.47;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento(nombre, nGrupos, maximo, incremento);
        });
    }

    @DisplayName("Crear un club deportivo de alto rendimiento con nombre no null, número de grupos menor o igual que cero, máximo de personas por grupo mayor que 0 e incremento mayor que 0")
    @Test
    public void ClubDeportivoAltoRendimiento_NombreNoNullNgruposMenorIgualCeroMaximoMayorCeroIncrementoMayorCero_LanzaExcepcion() {
        // Arrange
        String nombre = "club1";
        int nGrupos = -1;
        int maximo = 25;
        double incremento = 23.47;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento(nombre, nGrupos, maximo, incremento);
        });
    }

    @DisplayName("Crear un club deportivo de alto rendimiento con nombre no null, número de grupos mayor que cero, máximo de personas por grupo menor o igual que 0 e incremento mayor que 0")
    @Test
    public void ClubDeportivoAltoRendimiento_NombreNoNullNgruposMayorCeroMaximoMenorIgualCeroIncrementoMayorCero_LanzaExcepcion() {
        // Arrange
        String nombre = "club1";
        int nGrupos = 20;
        int maximo = -1;
        double incremento = 23.47;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento(nombre, nGrupos, maximo, incremento);
        });
    }

    @DisplayName("Crear un club deportivo de alto rendimiento con nombre no null, número de grupos mayor que cero, máximo de personas por grupo mayor que 0 e incremento menor o igual que 0")
    @Test
    public void ClubDeportivoAltoRendimiento_NombreNoNullNgruposMayorCeroMaximoMayorCeroIncrementoMenorIgualCero_LanzaExcepcion() {
        // Arrange
        String nombre = "club1";
        int nGrupos = 20;
        int maximo = 25;
        double incremento = -3.47;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento(nombre, nGrupos, maximo, incremento);
        });
    }

    @Test
    @DisplayName("Anyadir una actividad que no existe y hay espacio en límite de actividades")
    public void anyadirActividad_NoExisteActividadYHayEspacio_ActividadCreada() throws ClubException {
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Alto Rendimiento", 30, 10.0);
        String[] datos = {"codigo1", "actividad1", "20", "10", "50.0"};

        // Act
        club.anyadirActividad(datos);

        // Assert
        assertEquals("Club Alto Rendimiento --> [ (codigo1 - actividad1 - 50.0 euros - P:20 - M:10) ]", club.toString());
    }

    @Test
    @DisplayName("Anyadir una actividad que no existe y no hay espacio en límite de actividades")
    public void anyadirActividad_NoExisteActividadYNoHayEspacio_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Alto Rendimiento", 1, 30, 10.0);
        String[] datos1 = {"codigo1", "actividad1", "20", "10", "50.0"};
        String[] datos2 = {"codigo2", "actividad2", "20", "10", "50.0"};
        club.anyadirActividad(datos1);

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos2);
        });
    }

    @Test
    @DisplayName("Anyadir una actividad con plazas que exceden el límite")
    public void anyadirActividad_PlazasExcedenLimite_PlazasAjustadas() throws ClubException {
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Alto Rendimiento", 30, 10.0);
        String[] datos = {"codigo1", "actividad1", "40", "10", "50.0"};

        // Act
        club.anyadirActividad(datos);

        // Assert
        assertEquals("Club Alto Rendimiento --> [ (codigo1 - actividad1 - 50.0 euros - P:30 - M:10) ]", club.toString());
    }

    @Test
    @DisplayName("Anyadir una actividad que existe")
    public void anyadirActividad_ExisteActividad_ActualizaPlazas() throws ClubException {
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Alto Rendimiento", 30, 10.0);
        String[] datos1 = {"codigo1", "actividad1", "20", "10", "50.0"};
        String[] datos2 = {"codigo1", "actividad1", "25", "10", "50.0"};
        club.anyadirActividad(datos1);

        // Act
        club.anyadirActividad(datos2);

        // Assert
        assertEquals("Club Alto Rendimiento --> [ (codigo1 - actividad1 - 50.0 euros - P:25 - M:10) ]", club.toString());
    }

    @Test
    @DisplayName("Anyadir una actividad especificando datos incorrectos")
    public void anyadirActividad_DatosIncorrectos_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Alto Rendimiento", 30, 10.0);
        String[] datos = {"codigo1", "actividad1", "a", "b", "c"};

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("Anyadir una actividad no especificando suficientes datos")
    public void anyadirActividad_NoSuficientesDatos_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Alto Rendimiento", 30, 10.0);
        String[] datos = {"codigo1", "actividad1", "20"};

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @DisplayName("Obtener ingresos de un club deportivo de alto rendimiento que tiene grupos pero no tiene matriculados")
    @Test
    public void ingresos_HayGruposPeroNoHayMatriculados_DevuelveCero() throws ClubException {
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Alto Rendimiento", 30, 10.0);
        club.anyadirActividad(new String[]{"codigo1", "actividad1", "5", "0", "5.0"});
        club.anyadirActividad(new String[]{"codigo2", "actividad2", "5", "0", "5.0"});

        // Act
        double ingresos = club.ingresos();

        // Assert
        assertEquals(0, ingresos);   
    }

    @DisplayName("Obtener ingresos de un club deportivo de alto rendimiento que no tiene grupos")
    @Test
    public void ingresos_NoHayGrupos_DevuelveCero() throws ClubException {
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Alto Rendimiento", 30, 10.0);

        // Act
        double ingresos = club.ingresos();

        // Assert
        assertEquals(0, ingresos);
    }

    @DisplayName("Obtener ingresos de un club deportivo de alto rendimiento que tiene grupos y matriculados")
    @Test
    public void ingresos_HayGruposHayMatriculados_DevuelveIngresosConIncremento() throws ClubException {
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club Alto Rendimiento", 30, 10.0);
        club.anyadirActividad(new String[]{"codigo1", "actividad1", "5", "5", "5.0"});
        club.anyadirActividad(new String[]{"codigo2", "actividad2", "5", "5", "5.0"});
        double ingresosEsperados = (5 * 5.0 + 5 * 5.0) * 1.10;

        // Act
        double ingresos = club.ingresos();

        // Assert        
        assertEquals(ingresosEsperados, ingresos, 0.01);
    }    
}