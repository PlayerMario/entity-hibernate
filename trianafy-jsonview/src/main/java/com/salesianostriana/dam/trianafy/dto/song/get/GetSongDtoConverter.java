package com.salesianostriana.dam.trianafy.dto.song.get;

import com.salesianostriana.dam.trianafy.dto.song.get.GetSongDto;
import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

@Component
public class GetSongDtoConverter {

    public GetSongDto songToDto(Song s) {
        String artistName = "";
        if (s.getArtist() == null) {
            artistName = "Desconocido";
        } else {
            artistName = s.getArtist().getName();
        }

        return GetSongDto
                .builder()
                .id(s.getId())
                .title(s.getTitle())
                .artist(artistName)
                .album(s.getAlbum())
                .year(s.getYear())
                .build();
    }

}
