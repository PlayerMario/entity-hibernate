package com.salesianostriana.dam.trianafy.dto.list.create;

import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class CreatePlaylistDtoConverter {

    public CreatePlaylistDto playlistToDto(Playlist p) {
        return CreatePlaylistDto
                .builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .build();
    }

    public Playlist createPlaylistDtoToPlaylist(CreatePlaylistDto createPlaylistDto) {
        return new Playlist(
                createPlaylistDto.getName(),
                createPlaylistDto.getDescription()
        );
    }

}
