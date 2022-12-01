package com.salesianostriana.dam.trianafy.dto.list.get;

import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class GetPlaylistDtoConverter {

    public GetPlaylistDto playlistToDto(Playlist p) {
        return GetPlaylistDto
                .builder()
                .id(p.getId())
                .name(p.getName())
                .numberOfSongs(p.getSongs().size())
                .build();
    }

}
