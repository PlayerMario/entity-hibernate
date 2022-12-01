package com.salesianostriana.dam.trianafy.dto.song.get;

import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetSongDto {

    private Long id;
    private String title;
    private String artist;
    private String album;
    private String year;

}
