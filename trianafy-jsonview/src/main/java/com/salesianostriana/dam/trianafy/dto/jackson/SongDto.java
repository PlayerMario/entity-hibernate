package com.salesianostriana.dam.trianafy.dto.jackson;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.model.Song;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Builder @Data
public class SongDto {
    @JsonView(SongViews.GetSongPublic.class)
    private Long id;
    @JsonView({SongViews.GetSongPublic.class, SongViews.CreateSongPublic.class})
    private String title;
    @JsonView(SongViews.GetSongPublic.class)
    private String artist;
    @JsonView({SongViews.GetSongPublic.class, SongViews.CreateSongPublic.class})
    private String album;
    @JsonView({SongViews.GetSongPublic.class, SongViews.CreateSongPublic.class})
    private String year;

    @JsonView(SongViews.CreateSongPublic.class)
    private Long artistId;

    public static SongDto of (Song s) {
        String artistName = "";
        Long artistId = 0L;
        if (s.getArtist() == null) {
            artistName = "Desconocido";
            artistId = null;
        } else {
            artistName = s.getArtist().getName();
            artistId = s.getArtist().getId();
        }

        return SongDto
                .builder()
                .id(s.getId())
                .title(s.getTitle())
                .artist(artistName)
                .album(s.getAlbum())
                .year(s.getYear())
                .artistId(artistId)
                .build();
    }

    public static Song createSong(SongDto songDto) {
        return new Song(
                songDto.getTitle(),
                songDto.getAlbum(),
                songDto.getYear()
        );
    }
}
