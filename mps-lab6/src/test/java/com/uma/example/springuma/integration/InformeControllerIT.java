package com.uma.example.springuma.integration;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.*;

public class InformeControllerIT extends AbstractIntegration {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Creación y eliminación de informes de imágenes.

    @Test
    @DisplayName("Crear un informe y comprobar que se crea correctamente")
	void crearInforme_SeCreaCorrectamente() throws Exception {
        Informe informe = new Informe();
        informe.setContenido("contenido");
        informe.setPrediccion("prediccion");

        // crea el informe
        this.mockMvc.perform(post("/informe")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(informe)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());
        
        // obtiene el informe y comprueba que se ha creado correctamente
		this.mockMvc.perform(get("/informe/1"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.prediccion").exists()) // comprueba que el tipo resultante es igual al informe creado
        .andExpect(jsonPath("$.contenido").value(informe.getContenido()));
    }

    @Test
    @DisplayName("Eliminar un informe y comprobar que se ha eliminado correctamente")
    public void eliminarInforme_SeEliminaCorrectamente() throws Exception {
        Informe informe = new Informe();
        informe.setId(1);
        informe.setContenido("contenido");
        informe.setPrediccion("prediccion");

        // crea el informe
        this.mockMvc.perform(post("/informe")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(informe)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());

        // elimina el informe
        this.mockMvc.perform(delete("/informe/" + informe.getId()))
        .andExpect(status().isNoContent());
        
        // comprueba que se ha eliminado correctamente
        this.mockMvc.perform(get("/informe/" + informe.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Eliminar un informe que no existe")
    public void eliminarInforme_InformeNoExistente_DevuelveError() throws Exception {
        this.mockMvc.perform(delete("/medico/99999"))
            .andExpect(status().is5xxServerError());
    }
}
