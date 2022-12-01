package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Artista", description = "Controlador para gestionar los artistas.")
public class ArtistController {

    private final ArtistService artistService;
    private final SongService songService;
    private final ArtistRepository artistRepo;

    @Operation(summary = "Obtener el listado de artistas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de artistas encontrado",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "nombre": "Joaquín Sabina"},
                                                {"id": 2, "nombre": "Dua Lipa"}
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existen artistas",
                    content = @Content)})
    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> listarArtistas() {
        if (artistService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(artistService.findAll());
        }
    }

    @Operation(summary = "Obtener artista por su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Artista encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 1, "nombre": "Joaquín Sabina"}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Artista no encontrado",
                    content = @Content)})
    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> mostrarArtista(@PathVariable Long id) {
        return ResponseEntity.of(artistService.findById(id));
    }

    @Operation(summary = "Crear nuevo artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artista creado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 13, "nombre": "Wu-Tang Clan"}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la creación aportado inválido",
                    content = @Content)})
    @PostMapping("/artist/")
    public ResponseEntity<Artist> crearArtista(@RequestBody Artist a) {
        if (a.getName() == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(artistService.add(a));
        }
    }

    @Operation(summary = "Modificar un artista, buscado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artista modificado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 13, "nombre": "Westside Connection"}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificación aportado inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encuentra el artista",
                    content = @Content)
    })
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> actualizarArtista(@PathVariable Long id, @RequestBody Artist a) {
        if (a.getName() == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.of(
                    artistService.findById(id).map(art -> {
                        art.setName(a.getName());
                        artistService.edit(art);

                        return art;
                    })
            );
        }
    }

    @Operation(summary = "Eliminar un artista, buscado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Artista eliminado correctamente, sin contenido",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {}
                                            """
                            )}
                    )})})
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Artist> borrarArtista(@PathVariable Long id) {
        if (artistRepo.existsById(id)) {
            songService.setearArtistaBorrado(artistService.findById(id).get());
            artistService.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
