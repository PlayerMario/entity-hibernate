package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.list.create.CreatePlaylistDto;
import com.salesianostriana.dam.trianafy.dto.list.create.CreatePlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.dto.list.get.GetOnePlaylistDto;
import com.salesianostriana.dam.trianafy.dto.list.get.GetOnePlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.dto.list.get.GetPlaylistDto;
import com.salesianostriana.dam.trianafy.dto.list.get.GetPlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Playlist", description = "Controlador para gestionar las playlists.")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final GetPlaylistDtoConverter getPlaylistDtoConverter;
    private final GetOnePlaylistDtoConverter getOnePlaylistDtoConverter;
    private final CreatePlaylistDtoConverter createPlaylistDtoConverter;
    private final PlaylistRepository playlistRepository;
    private final SongService songService;
    private final SongRepository songRepository;

    // GESTIÓN DE PLAYLIST
    @Operation(summary = "Obtener el listado de todas las playlists")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Playlists encontrada",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPlaylistDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 12, "name": "Random", "numberOfSongs": "4"}
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe ninguna playlist",
                    content = @Content)})
    @GetMapping("/list/")
    public ResponseEntity<List<GetPlaylistDto>> listarPlaylists() {
        List<Playlist> listado = playlistService.findAll();

        if (listado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<GetPlaylistDto> resultado = listado
                    .stream()
                    .map(getPlaylistDtoConverter::playlistToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(resultado);
        }
    }

    @Operation(summary = "Obtener los detalles de una playlists")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Playlists encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetOnePlaylistDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {"id": 12, "name": "Random", "description": "Una lista muy loca",
                                                    "songs": [
                                                        {"id": 9, "title": "Enter Sandman", "artist": "Metallica", "album": "Metallica", "year": "1991"},
                                                        {"id": 8, "title": "Love Again", "artist": "Dua Lipa", "album": "Future Nostalgia", "year": "2021"},
                                                        {"id": 9, "title": "Enter Sandman", "artist": "Metallica", "album": "Metallica", "year": "1991"},
                                                        {"id": 11, "title": "Nothing Else Matters", "artist": "Metallica", "album": "Metallica", "year": "1991"}
                                                    ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe la playlist",
                    content = @Content)})
    @GetMapping("/list/{id}")
    public ResponseEntity<GetOnePlaylistDto> mostrarPlaylist(@PathVariable Long id) {
        if (playlistService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(getOnePlaylistDtoConverter.onePlaylistToDto(playlistService.findById(id).get()));
        }
    }

    @Operation(summary = "Crear nueva playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Playlist creada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreatePlaylistDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {"id": 15, "name": "Música de los 90", "description": "Listado de canciones de rap de los 90"}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la creación aportado inválido",
                    content = @Content)})
    @PostMapping("/list/")
    public ResponseEntity<CreatePlaylistDto> crearPlaylist(@RequestBody Playlist playlist) {
        if (playlist.getName() == "" || playlist.getDescription() == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            List<Song> listadoCanciones = new ArrayList<>();
            playlist.setSongs(listadoCanciones);
            playlistService.add(playlist);

            return ResponseEntity.status(HttpStatus.CREATED).body(createPlaylistDtoConverter.playlistToDto(playlist));
        }
    }

    @Operation(summary = "Modificar la información de una playlist, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist modificada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlaylistDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 15, "name": "Rap90", "numberOfSongs": "0"}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificación aportado inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encuentra la playlist",
                    content = @Content)
    })
    @PutMapping("/list/{id}")
    public ResponseEntity<GetPlaylistDto> actualizarPlaylist(@PathVariable Long id, @RequestBody Playlist playlist) {
        if (playlist.getName() == "" || playlist.getDescription() == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            CreatePlaylistDto playlistEditada = createPlaylistDtoConverter.playlistToDto(playlist);
            return ResponseEntity.of(
                    playlistService.findById(id).map(p -> {
                        p.setName(playlistEditada.getName());
                        p.setDescription(playlistEditada.getDescription());
                        playlistService.edit(p);

                        return getPlaylistDtoConverter.playlistToDto(p);
                    }));
        }
    }

    @Operation(summary = "Eliminar una playlist, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Playlist eliminada correctamente, sin contenido",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {}
                                            """
                            )}
                    )})})
    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> borrarPlaylist(@PathVariable Long id) {
        if (playlistRepository.existsById(id)) {
            playlistService.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    // GESTIÓN DE CANCIONES DE UNA PLAYLIST
    @Operation(summary = "Obtener el listado de canciones de una playlist, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de canciones de una playlist encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetOnePlaylistDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {"id": 12, "name": "Random", "description": "Una lista muy loca",
                                                    "songs": [
                                                        {"id": 9, "title": "Enter Sandman", "artist": "Metallica", "album": "Metallica", "year": "1991"},
                                                        {"id": 8, "title": "Love Again", "artist": "Dua Lipa", "album": "Future Nostalgia", "year": "2021"},
                                                        {"id": 9, "title": "Enter Sandman", "artist": "Metallica", "album": "Metallica", "year": "1991"},
                                                        {"id": 11, "title": "Nothing Else Matters", "artist": "Metallica", "album": "Metallica", "year": "1991"}
                                                    ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe la playlist",
                    content = @Content)})
    @GetMapping("/list/{id}/song/")
    public ResponseEntity<GetOnePlaylistDto> listarCancionesPlaylist(@PathVariable Long id) {
        if (playlistService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(getOnePlaylistDtoConverter.onePlaylistToDto(playlistService.findById(id).get()));
        }
    }

    @Operation(summary = "Obtener la canción, por su ID, de una lista, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Canción y playlist encontrados",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 9, "title": "Enter Sandman", "artist": {"id": "3", "name": "Metallica"}, "album": "Metallica", "year": "1991"}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Canción o playlist no encontrados",
                    content = @Content)})
    @GetMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<Song> mostrarCancion(@PathVariable("id1") Long idList, @PathVariable("id2") Long idSong) {
        if (playlistService.findById(idList).isEmpty() || songService.findById(idSong).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.of(playlistService.findById(idList)
                    .get().getSongs()
                    .stream().filter(song -> song.getId().equals(idSong))
                    .findFirst());
        }
    }

    @Operation(summary = "Añadir una canción a una playlist")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Canción añadida con éxito a la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetOnePlaylistDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {"id": 12, "name": "Random", "description": "Una lista muy loca",
                                                    "songs": [
                                                        {"id": 9, "title": "Enter Sandman", "artist": "Metallica", "album": "Metallica", "year": "1991"},
                                                        {"id": 8, "title": "Love Again", "artist": "Dua Lipa", "album": "Future Nostalgia", "year": "2021"},
                                                        {"id": 9, "title": "Enter Sandman", "artist": "Metallica", "album": "Metallica", "year": "1991"},
                                                        {"id": 11, "title": "Nothing Else Matters", "artist": "Metallica", "album": "Metallica", "year": "1991"},
                                                        {"id": 4, "title": "19 días y 500 noches", "artist": "Joaquín Sabina", "album": "19 días y 500 noches", "year": "1999"}
                                                    ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe la playlist o la canción",
                    content = @Content)})
    @PostMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<GetOnePlaylistDto> guardarCancionLista(@PathVariable("id1") Long idList, @PathVariable("id2") Long idSong) {
        if (playlistService.findById(idList).isEmpty() || songService.findById(idSong).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            playlistService.findById(idList).get().getSongs().add(songService.findById(idSong).get());
            playlistService.edit(playlistService.findById(idList).get());
            return ResponseEntity.status(HttpStatus.CREATED).body(getOnePlaylistDtoConverter.onePlaylistToDto(playlistService.findById(idList).get()));
        }
    }

    @Operation(summary = "Eliminar una canción, buscada por su ID, de una lista, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Canción eliminada correctamente, sin contenido",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {}
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe la playlist o la canción",
                    content = @Content)})
    @DeleteMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<?> borrarCancionLista(@PathVariable("id1") Long idList, @PathVariable("id2") Long idSong) {
        if (!playlistRepository.existsById(idList) || !songRepository.existsById(idSong)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            playlistService.borrarCancionListas(songService.findById(idSong).get());
            playlistService.edit(playlistService.findById(idList).get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}
