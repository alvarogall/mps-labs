package com.uma.example.springuma.integration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.http.MediaType;

import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Paciente;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

@DisplayName("Integración ImagenController")
public class ImagenControllerIT extends AbstractIntegration {
    private static final String IMAGEN_ENDPOINT = "/imagen";
    private static final String PACIENTE_ENDPOINT = "/paciente";

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


    @Test
    @DisplayName("Subir una imagen de un paciente y comprobar que se ha subido")
    public void uploadImage_ExistingPaciente_UploadsImage() throws Exception {
        // Crear paciente
        client.post().uri(PACIENTE_ENDPOINT)
            .body(Mono.just(paciente), Paciente.class)
            .exchange()
            .expectStatus().isCreated();

        // Subir imagen        
        File uploadImage = new File("./src/test/resources/healthy.png");        
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadImage));
        builder.part("paciente", paciente);

        client.post()
            .uri(IMAGEN_ENDPOINT)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .exchange()
            .expectStatus().is2xxSuccessful();

        // Comprobar que se ha subido la imagen
        client.get()
            .uri("/imagen/paciente/{id}", paciente.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(1)
            .jsonPath("$[0].paciente.id").isEqualTo(paciente.getId());
    }

    @Test
    @DisplayName("Obtener la predicción de una imagen")
    public void getImagenPrediction_ExistingImage_ReturnsPrediction() throws Exception {
        // Crear paciente
        client.post().uri(PACIENTE_ENDPOINT)
            .body(Mono.just(paciente), Paciente.class)
            .exchange()
            .expectStatus().isCreated();

        // Subir imagen
        String originalFilename = "healthy.png" ;     
        File uploadImage = new File("./src/test/resources/" + originalFilename);        
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadImage));
        builder.part("paciente", paciente);

        client.post()
            .uri(IMAGEN_ENDPOINT)
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
        
        // Predecir imagen
        client.get()
            .uri("/imagen/predict/{id}", imagen.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.status").exists()
            .jsonPath("$.score").exists();
    }

    @Test
    @DisplayName("Descargar una imagen que existe y comprobar que es igual a la subida")
    public void downloadImage_ExistingImage_ReturnsSameImageAsUploaded() throws Exception {
        // Crear paciente
        client.post().uri(PACIENTE_ENDPOINT)
            .body(Mono.just(paciente), Paciente.class)
            .exchange()
            .expectStatus().isCreated();

        // Subir imagen
        String originalFilename = "healthy.png";     
        File uploadImage = new File("./src/test/resources/" + originalFilename);        
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadImage));
        builder.part("paciente", paciente);

        client.post()
            .uri(IMAGEN_ENDPOINT)
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

        // Descargar la imagen
        byte[] downloadedBytes = client.get()
            .uri("/imagen/{id}", imagen.getId())
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType("image/png")
            .expectBody(byte[].class)
            .returnResult()
            .getResponseBody();

        assertNotNull(downloadedBytes);

        // Comprobar que ambos arrays de bytes son iguales
        byte[] originalBytes = java.nio.file.Files.readAllBytes(uploadImage.toPath());
        
        assertArrayEquals(originalBytes, downloadedBytes);
    }

    @Test
    @DisplayName("Descargar una imagen que no existe")
    public void downloadImage_NonExistingImage_ReturnsError() {
        client.get()
            .uri("/imagen/{id}", 99999L)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("Obtener info de una imagen que existe")
    public void getImagenInfo_ExistingImage_ReturnsInfo() throws Exception {
        // Crear paciente
        client.post().uri(PACIENTE_ENDPOINT)
            .body(Mono.just(paciente), Paciente.class)
            .exchange()
            .expectStatus().isCreated();

        // Subir imagen
        String originalFilename = "healthy.png";     
        File uploadImage = new File("./src/test/resources/" + originalFilename);        
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadImage));
        builder.part("paciente", paciente);

        client.post()
            .uri(IMAGEN_ENDPOINT)
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

        // Obtener info de imagen
        client.get()
            .uri("/imagen/info/{id}", imagen.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(imagen.getId())
            .jsonPath("$.nombre").isEqualTo(imagen.getNombre());
    }

    @Test
    @DisplayName("Obtener info de una imagen que no existe")
    public void getImagenInfo_NonExistingImage_ReturnsError() {
        client.get()
            .uri("/imagen/info/{id}", 99999L)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("Obtener las imágenes de un paciente que existe")
    public void getImagenes_ExistingPaciente_ReturnsList() throws Exception {
        // Crear paciente
        client.post().uri(PACIENTE_ENDPOINT)
            .body(Mono.just(paciente), Paciente.class)
            .exchange()
            .expectStatus().isCreated();

        // Subir imagen
        String originalFilename = "healthy.png";     
        File uploadImage = new File("./src/test/resources/" + originalFilename);        
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadImage));
        builder.part("paciente", paciente);

        client.post()
            .uri(IMAGEN_ENDPOINT)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .exchange()
            .expectStatus().is2xxSuccessful();

        // Obtener imágenes del paciente
        client.get()
            .uri("/imagen/paciente/{id}", paciente.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(1)
            .jsonPath("$[0].paciente.id").isEqualTo(paciente.getId());
    }

    @Test
    @DisplayName("Obtener las imágenes de un paciente que no existe")
    public void getImagenes_NonExistingPaciente_ReturnsEmptyList() {
        client.get()
            .uri("/imagen/paciente/{id}", 99999L)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    @DisplayName("Eliminar una imagen que existe y comprobar que se ha eliminado")
    public void deleteImagen_ExistingImage_DeletesSuccessfully() throws Exception {
        // Crear paciente
        client.post().uri(PACIENTE_ENDPOINT)
            .body(Mono.just(paciente), Paciente.class)
            .exchange()
            .expectStatus().isCreated();

        // Subir imagen
        String originalFilename = "healthy.png";     
        File uploadImage = new File("./src/test/resources/" + originalFilename);        
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadImage));
        builder.part("paciente", paciente);

        client.post()
            .uri(IMAGEN_ENDPOINT)
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

        // Eliminar imagen
        client.delete()
            .uri("/imagen/{id}", imagen.getId())
            .exchange()
            .expectStatus().isNoContent();
        
        // Comprobar que se ha eliminado
        client.get()
            .uri("/imagen/paciente/{id}", paciente.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(0);
    }
}