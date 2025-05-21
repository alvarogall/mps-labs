package com.uma.example.springuma.integration;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.*;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

public class InformeControllerIT extends AbstractIntegration {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private Integer port;

    private WebTestClient client;

    private Paciente paciente;

    @PostConstruct
    public void init() {
        client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:" + port)
            .responseTimeout(Duration.ofMillis(30000))
            .build();
        
        paciente = new Paciente();
        paciente.setId(1);    
    }

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
        .andExpect(status().isOk()) // no lanza error aunque el informe ya no exista en la bd
        .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Obtener un informe a través de una imagen")
    public void getInformeImagen_ImagenExistente_DevuelveInformeCorrectamente() throws Exception {
        // Crear paciente
        client.post().uri("/paciente")
            .body(Mono.just(paciente), Paciente.class)
            .exchange()
            .expectStatus().isCreated();

        // Subir imagen        
        File uploadImage = new File("./src/test/resources/healthy.png");        
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadImage));
        builder.part("paciente", paciente);

        client.post()
            .uri("/imagen")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .exchange()
            .expectStatus().is2xxSuccessful();

        // Obtener imagen
        Imagen imagen = client.get()
            .uri("/imagen/paciente/{id}", paciente.getId())
            .exchange()
            .expectStatus().isOk()
            .returnResult(Imagen.class)
            .getResponseBody().blockFirst();
        
        Informe informe = new Informe();
        informe.setId(1);
        informe.setContenido("contenido");
        informe.setPrediccion("prediccion");
        informe.setImagen(imagen);

        // crea el informe
        this.mockMvc.perform(post("/informe")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(informe)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());
        
        // obtiene el informe y comprueba que se ha creado correctamente
		this.mockMvc.perform(get("/informe/imagen/" + imagen.getId()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].prediccion").exists()) // comprueba que el tipo resultante es igual al informe creado
        .andExpect(jsonPath("$[0].contenido").value(informe.getContenido()))
        .andExpect(jsonPath("$[0].imagen.id").value(imagen.getId()))
        .andExpect(jsonPath("$[0].imagen.file_content").exists());
    }
    
    @Test
    @DisplayName("Obtener un informe")
    public void getInforme_InformeExistente_DevuelveInformeCorrectamente() throws Exception {
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
    @DisplayName("Obtener un informe a través de una imagen que no existe")
    public void getInformeImagen_ImagenNoExistente_DevuelveError() throws Exception {
        this.mockMvc.perform(get("/informe/imagen/99999"))
            .andExpect(status().isOk()) // no lanza error aunque el informe no exista en la bd
            .andExpect(content().string("[]"));
    }

    @Test
    @DisplayName("Obtener un informe que no existe")
    public void getInforme_InformeNoExistente_DevuelveError() throws Exception {
        this.mockMvc.perform(get("/informe/99999"))
            .andExpect(status().isOk()) // no lanza error aunque el informe no exista en la bd
            .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Eliminar un informe que no exista")
    public void eliminarInforme_InformeNoExistente_DevuelveError() throws Exception {
        this.mockMvc.perform(delete("/informe/99999"))
            .andExpect(status().isNoContent()); // no lanza error aunque el informe no exista en la bd
    }

}
