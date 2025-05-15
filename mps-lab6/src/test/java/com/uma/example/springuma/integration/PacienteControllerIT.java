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

public class PacienteControllerIT extends AbstractIntegration {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Asociar médico a paciente y comprobar que se ha asociado correctamente")
	void asociar_pacienteConMedico() throws Exception {
        Medico medico = new Medico();
        medico.setDni("34583485R");
        medico.setNombre("Ramon");
        medico.setEspecialidad("familia");
        medico.setId(1);
        Paciente paciente = new Paciente();
        paciente.setNombre("Pablo");
        paciente.setEdad(20);
        paciente.setCita("17/05/2025");
        paciente.setDni("20843953F");
        paciente.setMedico(medico);
        paciente.setId(1);

        // crea el médico
        this.mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());

        // crea el paciente
        this.mockMvc.perform(post("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(paciente)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());
        
        // obtiene el paciente y comprueba que se ha creado correctamente con el médico asociado
		this.mockMvc.perform(get("/paciente/1"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$").value(paciente)) // comprueba que el tipo resultante es igual al paciente creado
        .andExpect(jsonPath("$.medico.dni").value(medico.getDni()))
        .andExpect(jsonPath("$.medico.nombre").value(medico.getNombre()))
        .andExpect(jsonPath("$.medico.especialidad").value(medico.getEspecialidad()));
    }
    
    @Test
    @DisplayName("Asociar médico a paciente, editar el médico del paciente a otro y comprobar que se ha editado correctamente")
	void asociar_editar_pacienteConOtroMedico() throws Exception {
        Medico medico1 = new Medico();
        medico1.setDni("34583485R");
        medico1.setNombre("Ramon");
        medico1.setEspecialidad("familia");
        medico1.setId(1);
        Paciente paciente = new Paciente();
        paciente.setNombre("Pablo");
        paciente.setEdad(20);
        paciente.setCita("17/05/2025");
        paciente.setDni("20843953F");
        paciente.setMedico(medico1);
        paciente.setId(1);

        // crea el médico 1
        this.mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico1)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());

        // crea el paciente
        this.mockMvc.perform(post("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(paciente)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());
        
        // edito el médico del paciente
        Medico medico2 = new Medico();
        medico2.setDni("57834705T");
        medico2.setNombre("Felipe");
        medico2.setEspecialidad("cabecera");
        medico2.setId(2);
        paciente.setMedico(medico2);

        // crea el médico 2
        this.mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico2)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());

        // actualizo el paciente asignando el nuevo médico
        this.mockMvc.perform(put("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(paciente)))
        .andExpect(status().isNoContent())
        .andExpect(status().is2xxSuccessful());

        // obtiene el paciente y comprueba que el paciente se ha cambiado de médico correctamente
        this.mockMvc.perform(get("/paciente/1"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$").value(paciente)) // comprueba que el tipo resultante es igual al paciente creada
        .andExpect(jsonPath("$.medico.dni").value(medico2.getDni()))
        .andExpect(jsonPath("$.medico.nombre").value(medico2.getNombre()))
        .andExpect(jsonPath("$.medico.especialidad").value(medico2.getEspecialidad()));
    }

}
