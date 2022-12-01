package com.salesianostriana.dam.trianafy.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.dto.jackson.SongDto;
import com.salesianostriana.dam.trianafy.dto.jackson.SongViews;
import com.salesianostriana.dam.trianafy.dto.song.create.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.song.get.GetSongDto;
import com.salesianostriana.dam.trianafy.dto.song.create.CreateSongDtoConverter;
import com.salesianostriana.dam.trianafy.dto.song.get.GetSongDtoConverter;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Canción", description = "Controlador para gestionar las canciones.")
public class SongController {

    private final SongService songService;
    private final SongRepository songRepository;
    private final ArtistService artistService;
    private final ArtistRepository artistRepository;
    private final PlaylistService playlistService;

    @Operation(summary = "Obtener el listado de canciones")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de canciones encontrado",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SongDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 7, "title": "Don't Start Now", "artist": "Dua Lipa", "album": "Future Nostalgia", "year": "2019"},
                                                {"id": 9, "title": "Enter Sandman", "artist": "Metallica", "album": "Metallica", "year": "1991"}
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existen canciones",
                    content = @Content)})
    @JsonView(SongViews.GetSongPublic.class)
    @GetMapping("/song/")
    public ResponseEntity<List<SongDto>> listarCanciones() {
        List<Song> listado = songService.findAll();

        if (listado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<SongDto> resultado = listado
                    .stream()
                    .map(SongDto::of)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(resultado);
        }
    }

    @Operation(summary = "Obtener canción por su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Canción encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 9, "title": "Enter Sandman", "artist": {"id": "3", "name": "Metallica"}, "album": "Metallica", "year": "1991"}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Canción no encontrada",
                    content = @Content)})
    @GetMapping("/song/{id}")
    public ResponseEntity<Song> mostrarCancion(@PathVariable Long id) {
        return ResponseEntity.of(songService.findById(id));
    }

    @Operation(summary = "Crear nueva canción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Canción creada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SongDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 14, "title": "Levitating", "artist": "Dua Lipa", "album": "Future Nostalgia", "year": "2020"}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la creación aportado inválido",
                    content = @Content)})
    @JsonView(SongViews.CreateSongPublic.class)
    @PostMapping("/song/")
    public ResponseEntity<SongDto> crearCancion(@RequestBody SongDto songDto) {
        if (!artistRepository.existsById(songDto.getArtistId()) || songDto.getYear() == "" || songDto.getTitle() == "" || songDto.getAlbum() == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            Song nuevaCancion = songDto.createSong(songDto);
            artistService.setearArtistaCancion(songDto.getArtistId(), nuevaCancion);
            songService.add(nuevaCancion);

            return ResponseEntity.status(HttpStatus.CREATED).body(songDto.of(nuevaCancion));
        }
    }

    @Operation(summary = "Modificar una canción, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Canción modificada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SongDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 14, "title": "Cold Heart", "artist": "Dua Lipa", "album": "The Lockdown Sessions", "year": "2021"}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificación aportado inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encuentra la canción",
                    content = @Content)
    })
    @JsonView(SongViews.CreateSongPublic.class)
    @PutMapping("/song/{id}")
    public ResponseEntity<SongDto> actualizarCancion(@PathVariable Long id, @RequestBody SongDto songDto) {
        if (!artistRepository.existsById(songDto.getArtistId()) || songDto.getYear() == "" || songDto.getTitle() == "" || songDto.getAlbum() == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            Song cancionEditada = songDto.createSong(songDto);
            artistService.setearArtistaCancion(songDto.getArtistId(), cancionEditada);
            return ResponseEntity.of(
                    songService.findById(id).map(song -> {
                        song.setTitle(cancionEditada.getTitle());
                        song.setAlbum(cancionEditada.getAlbum());
                        song.setYear(cancionEditada.getYear());
                        song.setArtist(cancionEditada.getArtist());
                        songService.edit(song);

                        return songDto.of(song);
                    }));
        }
    }

    @Operation(summary = "Eliminar una canción, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Canción eliminada correctamente, sin contenido",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {}
                                            """
                            )}
                    )})})
    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> borrarCancion(@PathVariable Long id) {
        if (songRepository.existsById(id)) {
            playlistService.borrarCancionListas(songService.findById(id).get());
            songService.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
