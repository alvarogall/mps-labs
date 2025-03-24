package clubdeportivo;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoTest {
    @DisplayName("Crear un club deportivo con número de grupos predefinido y nombre null")
    @Test
    public void ClubDeportivo_NombreNullTamPredefinido_LanzaExcepcion() {
        // Arrange
        String nombre = null;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivo(nombre);
        });
    }

    @DisplayName("Crear un club deportivo con número de grupos mayor que cero y nombre null")
    @Test
    public void ClubDeportivo_NombreNullNgruposMayorCero_LanzaExcepcion() {
        // Arrange
        String nombre = null;
        int n = 20;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivo(nombre, n);
        });
    }
    
    @DisplayName("Crear un club deportivo con número de grupos mayor que cero")
    @Test
    public void ClubDeportivo_NgruposMayorCero_ClubDeportivoCreado() throws ClubException {
        // Arrange
        String nombre = "club1";
        int n = 20;

        // Act
        ClubDeportivo club1 = new ClubDeportivo(nombre, n);
        
        // Assert
        assertEquals("club1 --> [  ]", club1.toString());
    }

    @DisplayName("Crear un club deportivo con número de grupos predefinido")
    @Test
    public void ClubDeportivo_initTam_ClubDeportivoCreado() throws ClubException {
        // Arrange
        String nombre = "club1";

        // Act
        ClubDeportivo club1 = new ClubDeportivo(nombre);
        
        // Assert
        assertEquals("club1 --> [  ]", club1.toString());
    }

    @DisplayName("Crear un club deportivo con número de grupos menor que cero")
    @Test
    public void ClubDeportivo_NgruposMenorCero_ClubDeportivoNoCreadoExcepcion() {
        // Arrange
        String nombre = "club1";
        int n = -1;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new ClubDeportivo(nombre, n);
        });
    }   

    @DisplayName("Anyadir una actividad que no existe y hay espacio en límite de actividades")
    @Test
    public void anyadirActividad_NoExisteActividadYHayEspacio_ActividadCreada() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        String[] datos1 = {"codigo1","actividad1","5","5","5.0"};

        // Act
        club1.anyadirActividad(datos1);
        
        // Assert
        assertEquals("club1 --> [ (codigo1 - actividad1 - 5.0 euros - P:5 - M:5) ]", club1.toString());
    }

    @DisplayName("Anyadir una actividad que no existe y no hay espacio en límite de actividades")
    @Test
    public void anyadirActividad_NoExisteActividadYNoHayEspacio_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1", 1);
        String[] datos1 = {"codigo1","actividad1","5","5","5.0"};
        String[] datos2 = {"codigo2","actividad2","5","5","5.0"};
        club1.anyadirActividad(datos1);      
        
        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club1.anyadirActividad(datos2);
        });
    }
    
    @DisplayName("Anyadir una actividad que existe")
    @Test
    public void anyadirActividad_ExisteActividad_ActualizaPlazas() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        String[] datos1_1 = {"codigo1","actividad1","5","5","5.0"};
        String[] datos1_2 = {"codigo1","actividad1","10","5","5.0"};
        club1.anyadirActividad(datos1_1);  

        // Act
        club1.anyadirActividad(datos1_2);
        
        // Assert
        assertEquals("club1 --> [ (codigo1 - actividad1 - 5.0 euros - P:10 - M:5) ]", club1.toString());
    }

    @DisplayName("Anyadir una actividad especificando datos incorrectos")
    @Test
    public void anyadirActividad_DatosIncorrectos_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1", 1);
        String[] datos1 = {"codigo1","actividad1","a","a","a"};
        
        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club1.anyadirActividad(datos1);
        });
    }

    @DisplayName("Anyadir una actividad no especificando suficientes datos")
    @Test
    public void anyadirActividad_NoSuficientesDatos_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1", 1);
        String[] datos1 = {"codigo1","actividad1","a"};
        
        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club1.anyadirActividad(datos1);
        });
    }

    @DisplayName("Anyadir una actividad mediante grupo que no existe y hay espacio en límite de actividades")
    @Test
    public void anyadirActividad_NoExisteGrupoYHayEspacio_ActividadCreada() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 5, 5.0);

        // Act
        club1.anyadirActividad(grupo1);
        
        // Assert
        assertEquals("club1 --> [ (codigo1 - actividad1 - 5.0 euros - P:5 - M:5) ]", club1.toString());
    }

    @DisplayName("Anyadir una actividad que no existe y no hay espacio en límite de actividades")
    @Test
    public void anyadirActividad_NoExisteGrupoYNoHayEspacio_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1", 1);
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 5, 5.0);
        Grupo grupo2 = new Grupo("codigo2", "actividad2", 5, 5, 5.0);
        club1.anyadirActividad(grupo1);      
        
        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club1.anyadirActividad(grupo2);
        });
    }
   
    @DisplayName("Anyadir una actividad que existe")
    @Test
    public void anyadirActividad_ExisteGrupo_ActualizaPlazas() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1_1 = new Grupo("codigo1", "actividad1", 5, 5, 5.0);
        Grupo grupo1_2 = new Grupo("codigo1", "actividad1", 10, 5, 5.0);
        club1.anyadirActividad(grupo1_1);  

        // Act
        club1.anyadirActividad(grupo1_2);
        
        // Assert
        assertEquals("club1 --> [ (codigo1 - actividad1 - 5.0 euros - P:10 - M:5) ]", club1.toString());
    }

    @DisplayName("Anyadir una actividad especificando datos incorrectos")
    @Test
    public void anyadirActividad_GrupoNulo_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = null;
        
        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club1.anyadirActividad(grupo1);
        });
    }

    @DisplayName("Obtener plazas libres de una actividad que no existe")
    @Test
    public void plazasLibres_NoExisteActividad_DevuelveCero() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        
        // Act
        int plazasLibres = club1.plazasLibres("actividad1");

        // Assert
        assertEquals(0, plazasLibres);
    }

    @DisplayName("Obtener plazas libres de una actividad que existe")
    @Test
    public void plazasLibres_ExisteActividad_DevuelveNumeroPlazas() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 3, 5.0);
        club1.anyadirActividad(grupo1); 

        // Act
        int plazasLibres = club1.plazasLibres("actividad1");

        // Assert
        assertEquals(2, plazasLibres);
    }

    @DisplayName("Matricular personas en una actividad que no existe")
    @Test
    public void matricular_NoExisteActividad_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        
        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club1.matricular("actividad1", 10);
        });
    }

    @DisplayName("Matricular personas en una actividad que existe, pero no se tienen plazas para todas")
    @Test
    public void matricular_ExisteActividadNoTienePlazas_LanzaExcepcion() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 5, 5.0);
        club1.anyadirActividad(grupo1);
        
        // Act, Assert
        assertThrows(ClubException.class, () -> {
            club1.matricular("actividad1", 10);
        });
    }

    @DisplayName("Matricular personas en una actividad que existe, pero las personas a matricular es cero o negativo")
    @Test
    public void matricular_ExisteActividadPersonasAMatricularCeroONegativo_NoMatricula() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 0, 5.0);
        club1.anyadirActividad(grupo1);

        // Act
        club1.matricular("actividad1", 0);
        
        // Assert
        assertEquals("club1 --> [ (codigo1 - actividad1 - 5.0 euros - P:5 - M:0) ]", club1.toString());
    }

    @DisplayName("Matricular personas en una actividad que existe y sobran plazas")
    @Test
    public void matricular_ExisteActividadSobranPlazas_PersonasMatriculadas() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 0, 5.0);
        Grupo grupo2 = new Grupo("codigo2", "actividad2", 5, 2, 5.0);
        Grupo grupo3 = new Grupo("codigo3", "actividad2", 5, 0, 5.0);
        Grupo grupo4 = new Grupo("codigo4", "actividad2", 10, 0, 5.0);
        club1.anyadirActividad(grupo1);
        club1.anyadirActividad(grupo2);
        club1.anyadirActividad(grupo3);
        club1.anyadirActividad(grupo4);
        
        // Act
        club1.matricular("actividad2", 13);

        // Assert
        assertEquals("club1 --> [ (codigo1 - actividad1 - 5.0 euros - P:5 - M:0), (codigo2 - actividad2 - 5.0 euros - P:5 - M:5), (codigo3 - actividad2 - 5.0 euros - P:5 - M:5), (codigo4 - actividad2 - 5.0 euros - P:10 - M:5) ]", club1.toString());
    }

    @DisplayName("Matricular personas en una actividad que existe y no sobran plazas")
    @Test
    public void matricular_ExisteActividadNoSobranPlazas_PersonasMatriculadas() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 0, 5.0);
        Grupo grupo2 = new Grupo("codigo2", "actividad2", 5, 2, 5.0);
        Grupo grupo3 = new Grupo("codigo3", "actividad2", 5, 0, 5.0);
        Grupo grupo4 = new Grupo("codigo4", "actividad2", 5, 0, 5.0);
        club1.anyadirActividad(grupo1);
        club1.anyadirActividad(grupo2);
        club1.anyadirActividad(grupo3);
        club1.anyadirActividad(grupo4);
        
        // Act
        club1.matricular("actividad2", 13);

        // Assert
        assertEquals("club1 --> [ (codigo1 - actividad1 - 5.0 euros - P:5 - M:0), (codigo2 - actividad2 - 5.0 euros - P:5 - M:5), (codigo3 - actividad2 - 5.0 euros - P:5 - M:5), (codigo4 - actividad2 - 5.0 euros - P:5 - M:5) ]", club1.toString());
    }

    @DisplayName("Obtener ingresos de un club deportivo que tiene grupos pero no tiene matriculados")
    @Test
    public void ingresos_HayGruposPeroNoHayMatriculados_DevuelveCero() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 0, 5.0);
        Grupo grupo2 = new Grupo("codigo2", "actividad2", 5, 0, 5.0);
        club1.anyadirActividad(grupo1);
        club1.anyadirActividad(grupo2);

        // Act
        double ingresos = club1.ingresos();

        // Assert
        assertEquals(0, ingresos);   
    }

    @DisplayName("Obtener ingresos de un club deportivo que no tiene grupos")
    @Test
    public void ingresos_NoHayGrupos_DevuelveCero() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");

        // Act
        double ingresos = club1.ingresos();

        // Assert
        assertEquals(0, ingresos);
    }

    @DisplayName("Obtener ingresos de un club deportivo que tiene grupos y matriculados")
    @Test
    public void ingresos_HayGruposHayMatriculados_DevuelveIngresosAsociadosALosMatriculados() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 5, 5.0);
        Grupo grupo2 = new Grupo("codigo2", "actividad2", 5, 5, 5.0);
        club1.anyadirActividad(grupo1);
        club1.anyadirActividad(grupo2);

        // Act
        double ingresos = club1.ingresos();

        // Assert
        assertEquals(50, ingresos);  
    }

    @DisplayName("Obtener el String asociado a un club deportivo sin grupos")
    @Test
    public void toString_NoHayGrupos_StringSinGrupos() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");

        // Act
        String resultado = club1.toString();

        // Assert
        assertEquals("club1 --> [  ]", resultado);
    }

    @DisplayName("Obtener el String asociado a un club deportivo con grupos")
    @Test
    public void toString_HayGrupos_StringConGrupos() throws ClubException {
        // Arrange
        ClubDeportivo club1 = new ClubDeportivo("club1");
        Grupo grupo1 = new Grupo("codigo1", "actividad1", 5, 5, 5.0);
        Grupo grupo2 = new Grupo("codigo2", "actividad2", 5, 5, 5.0);
        club1.anyadirActividad(grupo1);
        club1.anyadirActividad(grupo2);

        // Act
        String resultado = club1.toString();

        // Assert
        assertEquals("club1 --> [ (codigo1 - actividad1 - 5.0 euros - P:5 - M:5), (codigo2 - actividad2 - 5.0 euros - P:5 - M:5) ]", resultado);
    }
}