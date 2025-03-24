package clubdeportivo;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GrupoTest {
    @DisplayName("Crear un grupo con numero de plazas y tarifa mayor o igual que 0, matriculados mayor que 0 y matriculados menor que el numero de plazas")
    @Test
    public void Grupo_NPlazasYTarifaMayorIgualCeroMatriculadosMayorCeroMatriculadosMenorNPlazas_GrupoCreado() throws ClubException {
        // Arrange
        String codigo = "476B";
        String actividad = "Calistenia";
        int nPlazas = 15;
        int matriculados = 12;
        double tarifa = 27.3;

        // Act
        Grupo calistenia = new Grupo(codigo, actividad, nPlazas, matriculados, tarifa);

        // Assert
        assertEquals("(476B - Calistenia - 27.3 euros - P:15 - M:12)", calistenia.toString());
    }

    @DisplayName("Crear un grupo con numero de plazas menor o igual que 0")
    @Test
    public void Grupo_NPlazasMenorIgualCero_LanzaExcepcion() throws ClubException {
        // Arrange
        String codigo = "476B";
        String actividad = "Calistenia";
        int nPlazas = -1;
        int matriculados = 12;
        double tarifa = 27.3;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new Grupo(codigo, actividad, nPlazas, matriculados, tarifa);
        });
    }

    @DisplayName("Crear un grupo con tarifa menor o igual que 0")
    @Test
    public void Grupo_TarifaMenorIgualCero_LanzaExcepcion() throws ClubException {
        // Arrange
        String codigo = "476B";
        String actividad = "Calistenia";
        int nPlazas = 15;
        int matriculados = 12;
        double tarifa = -1;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new Grupo(codigo, actividad, nPlazas, matriculados, tarifa);
        });
    }

    @DisplayName("Crear un grupo con matriculados menor que 0")
    @Test
    public void Grupo_MatriculadosMenorCero_LanzaExcepcion() throws ClubException {
        // Arrange
        String codigo = "476B";
        String actividad = "Calistenia";
        int nPlazas = 15;
        int matriculados = -1;
        double tarifa = 27.3;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new Grupo(codigo, actividad, nPlazas, matriculados, tarifa);        
        });
    }

    @DisplayName("Crear un grupo con numero matriculados mayor que el numero de plazas")
    @Test
    public void Grupo_MatriculadosMayorNPlazas_LanzaExcepcion() throws ClubException {
        // Arrange
        String codigo = "476B";
        String actividad = "Calistenia";
        int nPlazas = 15;
        int matriculados = 17;
        double tarifa = 27.3;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new Grupo(codigo, actividad, nPlazas, matriculados, tarifa);
        });
    }

    @DisplayName("Crear un grupo con numero de plazas mayor que 0 y tarifa mayor que 0, matriculados mayor o igual que 0, matriculados menor que el numero de plazas, codigo null y actividad no null")
    @Test
    public void crearGrupo_CodigoNull_LanzaExcepcion() throws ClubException {
        // Arrange
        String codigo = null;
        String actividad = "Calistenia";
        int nPlazas = 15;
        int matriculados = 12;
        double tarifa = 27.3;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new Grupo(codigo, actividad, nPlazas, matriculados, tarifa);
        });
    }

    @DisplayName("Crear un grupo con numero de plazas mayor que 0 y tarifa mayor que 0, matriculados mayor o igual que 0 y matriculados menor que el numero de plazas, codigo no null y actividad null")
    @Test
    public void crearGrupo_ActividadNull_LanzaExcepcion() throws ClubException {
        // Arrange
        String codigo = "476B";
        String actividad = null;
        int nPlazas = 15;
        int matriculados = 12;
        double tarifa = 27.3;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            new Grupo(codigo, actividad, nPlazas, matriculados, tarifa);
        });
    }

    @Test
    @DisplayName("Obtener el codigo del grupo")
    public void getCodigo_GrupoCreado_DevuelveCodigoGrupo() throws ClubException{
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);

        // Act
        String codigo = calistenia.getCodigo();
        
        // Assert
        assertEquals("476B", codigo);
    }

    @Test
    @DisplayName("Obtener la actividad del grupo")
    public void getActividad_GrupoCreado_DevuelveActividadGrupo() throws ClubException{
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);

        // Act 
        String actividad = calistenia.getActividad();

        // Assert
        assertEquals("Calistenia", actividad);
    }

    @Test
    @DisplayName("Obtener el numero de plazas del grupo")
    public void getPlazas_GrupoCreado_DevuelveNPlazas() throws ClubException{
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);

        // Act
        int plazas = calistenia.getPlazas();

        // Assert
        assertEquals(15, plazas);
    }

    @Test
    @DisplayName("Obtener el numero de matriculados del grupo")
    public void getMatriculados_GrupoCreado_DevuelveNMatriculadosGrupo() throws ClubException{
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);

        // Act
        int matriculados = calistenia.getMatriculados();

        // Assert
        assertEquals(7, matriculados);
    }

    @Test
    @DisplayName("Obtener la tarifa del grupo")
    public void getTarifa_GrupoCreado_DevuelveTarifaGrupo() throws ClubException{
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);

        // Act
        double tarifa = calistenia.getTarifa();

        // Assert
        assertEquals(27.3, tarifa);
    }

    @Test
    @DisplayName("Obtener el numero de plazas libres de un grupo que tiene plazas libres")
    public void plazasLibres_GrupoConPlazasLibres_DevuelvePlazasLibres() throws ClubException{
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);

        // Act
        int plazasLibres = calistenia.plazasLibres();

        // Assert
        assertEquals(8, plazasLibres);
    }

    @Test
    @DisplayName("Obtener el numero de plazas libres de un grupo que no tiene plazas libres")
    public void plazasLibres_GrupoSinPlazasLibres_DevuelveCero() throws ClubException{
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 15, 27.3);
    
        // Act
        int plazasLibres = calistenia.plazasLibres();

        // Assert
        assertEquals(0, plazasLibres);
    }

    @DisplayName("Actualizar las plazas de un grupo a un numero mayor que el de matriculados")
    @Test
    public void actualizarPlazas_NPlazasMayorMatriculados_ActualizaPlazas() throws ClubException {
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);
        int nPLazas = 13;

        // Act
        calistenia.actualizarPlazas(nPLazas);

        // Assert
        assertEquals("(476B - Calistenia - 27.3 euros - P:13 - M:7)", calistenia.toString());
    }

    @DisplayName("Actualizar las plazas de un grupo a un numero igual que el de matriculados")
    @Test
    public void actualizarPlazas_NPlazasIgualMatriculados_ActualizaPlazas() throws ClubException {
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);
        int nPLazas = 7;

        // Act
        calistenia.actualizarPlazas(nPLazas);

        // Assert
        assertEquals("(476B - Calistenia - 27.3 euros - P:7 - M:7)", calistenia.toString());
    }
    
    @DisplayName("Actualizar las plazas de un grupo a un numero menor que el de matriculados y mayor que cero")
    @Test
    public void actualizarPlazas_NPlazasMenorMatriculadosMayorCero_LanzaExcepcion() throws ClubException {
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);
        int nPLazas = 5;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            calistenia.actualizarPlazas(nPLazas);
        });
    }

    @DisplayName("Actualizar las plazas de un grupo a un numero menor que cero")
    @Test
    public void actualizarPlazas_NPlazasMenorCero_LanzaExcepcion() throws ClubException {
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);
        int nPLazas = -1;

        // Act, Assert
        assertThrows(ClubException.class, () -> {
            calistenia.actualizarPlazas(nPLazas);
        });
    }

    @DisplayName("Matricular un numero de personas mayor que 0 y menor o igual que el numero de plazas libres")
    @Test
    public void matricular_NMayorCeroNTotalMenorIgualPlazasLibres_PersonasMatriculadas() throws ClubException {
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);
        int nMatricular = 5;

        // Act
        calistenia.matricular(nMatricular);

        // Assert
        assertEquals("(476B - Calistenia - 27.3 euros - P:15 - M:12)", calistenia.toString());
    }
    
    @DisplayName("Matricular a un numero de personas menor o igual que 0")
    @Test
    public void matricular_NMenorIgualCero_LanzaExcepcion() throws ClubException {
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);
        int nMatricular = -1;

        // Act, Assert
        assertThrows(ClubException.class, () -> { 
            calistenia.matricular(nMatricular);
        });
    }

    @DisplayName("Matricular a un numero de personas mayor que 0 y mayor que el numero de plazas libres")
    @Test
    public void matricular_NMayorCeroMayorPlazasLibres_LanzaExcepcion() throws ClubException {
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);
        int nMatricular = 10;

        // Act, Assert
        assertThrows(ClubException.class, () -> { 
            calistenia.matricular(nMatricular);
        });
    }

    @DisplayName("Obtener el String asociado a un grupo")
    @Test
    public void toString_ObjetoGrupo_StringAsociadoAObjetoGrupo() throws ClubException {
        // Arrange
        Grupo calistenia = new Grupo("476B", "Calistenia", 15, 7, 27.3);

        // Act
        String resultado = calistenia.toString();

        // Assert
        assertEquals("(476B - Calistenia - 27.3 euros - P:15 - M:7)", resultado);
    }

    @DisplayName("Dos grupos con el mismo codigo y misma actividad son iguales")
    @Test
    public void equals_GruposMismoCodigoMismaActividad_DevuelveTrue() throws ClubException {
        // Arrange
        Grupo calistenia1 = new Grupo("476B", "Calistenia", 17, 10, 23.1);
        Grupo calistenia2 = new Grupo("476B", "Calistenia", 15, 7, 27.3);

        // Act
        boolean sonIguales = calistenia1.equals(calistenia2);

        // Assert
        assertTrue(sonIguales);
    }

    @DisplayName("Comprobar que equals devuelve true si el grupo es el mismo")
    @Test
    public void equals_ObjetoGrupoConsigoMismo_DevuelveTrue() throws ClubException {
        //Arrange
        Grupo calistenia1 = new Grupo("476B", "Calistenia", 17, 10, 23.1);

        //Act
        boolean esIgual = calistenia1.equals(calistenia1);

        //Assert
        assertTrue(esIgual);
    }

    @DisplayName("Dos grupos con el distinto codigo y distinta actividad no son iguales")
    @Test
    public void equals_GruposDistintoCodigoDistintaActividad_DevuelveFalse() throws ClubException {
        // Arrange
        Grupo calistenia1 = new Grupo("476B", "Calistenia", 17, 10, 23.1);
        Grupo calistenia2 = new Grupo("675C", "CrossFit", 15, 7, 27.3);

        // Act
        boolean sonIguales = calistenia1.equals(calistenia2);

        // Assert
        assertFalse(sonIguales);
    }

    @DisplayName("Dos grupos con el mismo codigo y distinta actividad no son iguales")
    @Test
    public void equals_GruposMismoCodigoDistintaActividad_DevuelveFalse() throws ClubException {
        // Arrange
        Grupo calistenia1 = new Grupo("476B", "Calistenia", 17, 10, 23.1);
        Grupo calistenia2 = new Grupo("476B", "CrossFit", 15, 7, 27.3);

        // Act
        boolean sonIguales = calistenia1.equals(calistenia2);

        // Assert
        assertFalse(sonIguales);
    }

    @DisplayName("Dos grupos con el distinto codigo y misma actividad no son iguales")
    @Test
    public void equals_GruposDistintoCodigoMismaActividad_DevuelveFalse() throws ClubException {
        // Arrange
        Grupo calistenia1 = new Grupo("476B", "Calistenia", 17, 10, 23.1);
        Grupo calistenia2 = new Grupo("675C", "Calistenia", 15, 7, 27.3);

        // Act
        boolean sonIguales = calistenia1.equals(calistenia2);

        // Assert
        assertFalse(sonIguales);
    }

    @DisplayName("Comprobar que equals devuelve false si un objeto es null")
    @Test
    public void equals_ObjetoNull_DevuelveFalse() throws ClubException {
        // Arrange
        Grupo calistenia1 = new Grupo("476B", "Calistenia", 17, 10, 23.1);

        // Act
        boolean esIgual = calistenia1.equals(null);
        
        // Assert
        assertFalse(esIgual);
    }
    
    @DisplayName("Comprobar que equals devuelve false si un objeto no es grupo")
    @Test
    public void equals_ObjetoNoGrupo_DevuelveFalse() throws ClubException {
        // Arrange
        Grupo calistenia1 = new Grupo("476B", "Calistenia", 17, 10, 23.1);
        Object noGrupo = new Object();

        // Act
        boolean esIgual = calistenia1.equals(noGrupo);

        // Assert
        assertFalse(esIgual);
    }

    @DisplayName("Comprobar que hashCode devuelve el mismo valor para grupos iguales")
    @Test
    public void hashCode_GruposIguales_MismoHascode() throws ClubException {
        // Arrange
        Grupo calistenia1 = new Grupo("476B", "Calistenia", 17, 10, 23.1);
        Grupo calistenia2 = new Grupo("476B", "Calistenia", 15, 7, 27.3);

        // Act
        int hashCode1 = calistenia1.hashCode();
        int hashCode2 = calistenia2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @DisplayName("Comprobar que hashCode devuelve el mismo valor para grupos distintos")
    @Test
    public void hashCode_GruposDistintos_DistintoHascode() throws ClubException {
        // Arrange
        Grupo calistenia1 = new Grupo("476B", "Calistenia", 17, 10, 23.1);
        Grupo calistenia2 = new Grupo("675C", "CrossFit", 15, 7, 27.3);

        // Act
        int hashCode1 = calistenia1.hashCode();
        int hashCode2 = calistenia2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }
}
