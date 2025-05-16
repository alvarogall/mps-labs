package com.uma.example.springuma.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;

@DisplayName("Integración MedicoController")
public class MedicoControllerIT extends AbstractIntegration {
    private static final String MEDICO_ENDPOINT = "/medico";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Medico createMedico(long id, String dni, String nombre, String especialidad) {
        Medico medico = new Medico(dni, nombre, especialidad);
        medico.setId(id);
        return medico;
    }

    @Test
    @DisplayName("Guardar un médico y comprobar que se ha guardado")
    public void saveMedico_NoExistingMedico_SavesMedicoCorrectly() throws Exception {
        Medico medico = createMedico(1L, "dni", "nombre", "especialidad");

        // Guardar médico
        this.mockMvc.perform(post(MEDICO_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());

        // Comprobar que se ha guardado
        this.mockMvc.perform(get(MEDICO_ENDPOINT + "/" + medico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.dni").value("dni"))
            .andExpect(jsonPath("$.nombre").value("nombre"))
            .andExpect(jsonPath("$.especialidad").value("especialidad"));
    }

    @Test
    @DisplayName("Actualizar un médico y comprobar que se ha actualizado")
    public void updateMedico_ExistingMedico_UpdatesMedicoCorrectly() throws Exception {
        Medico medico = createMedico(1L, "dni", "nombre", "especialidad");

        // Guardar médico
        this.mockMvc.perform(post(MEDICO_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated());

        Medico medicoActualizado = createMedico(1L, "dniActualizado", "nombreActualizado", "especialidadActualizada");

        // Actualizar médico
        this.mockMvc.perform(put(MEDICO_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medicoActualizado)))
            .andExpect(status().is2xxSuccessful());

        // Comprobar que se ha actualizado
        this.mockMvc.perform(get(MEDICO_ENDPOINT + "/" + medicoActualizado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.dni").value("dniActualizado"))
            .andExpect(jsonPath("$.nombre").value("nombreActualizado"))
            .andExpect(jsonPath("$.especialidad").value("especialidadActualizada"));
    }

    @Test
    @DisplayName("Obtener un médico")
    public void getMedico_ExistingMedico_GetsMedicoCorrectly() throws Exception {
        Medico medico = createMedico(1L, "dni", "nombre", "especialidad");

        // Guardar médico
        this.mockMvc.perform(post(MEDICO_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated());

        // Obtener médico y comprobar valores
        this.mockMvc.perform(get(MEDICO_ENDPOINT + "/" + medico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.dni").value("dni"))
            .andExpect(jsonPath("$.nombre").value("nombre"))
            .andExpect(jsonPath("$.especialidad").value("especialidad"));
    }

    @Test
    @DisplayName("Eliminar un médico y comprobar que se ha eliminado")
    public void deleteMedico_ExistingMedico_DeletesMedicoCorrectly() throws Exception {
        Medico medico = createMedico(1L, "dni", "nombre", "especialidad");

        // Guardar médico
        this.mockMvc.perform(post(MEDICO_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated());

        // Eliminar médico
        this.mockMvc.perform(delete(MEDICO_ENDPOINT + "/" + medico.getId()))
            .andExpect(status().isOk());

        // Comprobar que se ha eliminado
        this.mockMvc.perform(get(MEDICO_ENDPOINT + "/" + medico.getId()))
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("Obtener un médico por su dni")
    public void getMedicoByDni_ExistingMedicoWithDni_GetsMedicoCorrectly() throws Exception {
        Medico medico = createMedico(1L, "dni", "nombre", "especialidad");

        // Guardar médico
        this.mockMvc.perform(post(MEDICO_ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated());

        // Obtener médico y comprobar valores
        this.mockMvc.perform(get(MEDICO_ENDPOINT + "/dni/" + medico.getDni()))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.dni").value("dni"))
            .andExpect(jsonPath("$.nombre").value("nombre"))
            .andExpect(jsonPath("$.especialidad").value("especialidad"));
    }

    @Test
    @DisplayName("Obtener un médico que no existe")
    public void getMedico_NonExistingMedico_ReturnsError() throws Exception {
        this.mockMvc.perform(get(MEDICO_ENDPOINT + "/99999"))
            .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Eliminar un médico que no existe")
    public void deleteMedico_NonExistingMedico_ReturnsError() throws Exception {
        this.mockMvc.perform(delete(MEDICO_ENDPOINT + "/99999"))
            .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Obtener un médico que no existe por dni")
    public void getMedicoByDni_NonExistingMedico_ReturnsNotFound() throws Exception {
        this.mockMvc.perform(get(MEDICO_ENDPOINT + "/dni/" + "dni-inexistente"))
            .andExpect(status().isNotFound());
    }
}