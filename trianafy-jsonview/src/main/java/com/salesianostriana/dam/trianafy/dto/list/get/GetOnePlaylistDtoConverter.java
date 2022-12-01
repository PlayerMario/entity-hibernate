package com.salesianostriana.dam.trianafy.dto.list.get;

import com.fasterxml.classmate.AnnotationOverrides;
import com.salesianostriana.dam.trianafy.dto.song.get.GetSongDtoConverter;
import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetOnePlaylistDtoConverter {

    private final GetSongDtoConverter getSongDto;

    public GetOnePlaylistDto onePlaylistToDto(Playlist p) {
        return GetOnePlaylistDto
                .builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .songs(p.getSongs().stream().map(getSongDto::songToDto).collect(Collectors.toList()))
                .build();
    }
    
}
