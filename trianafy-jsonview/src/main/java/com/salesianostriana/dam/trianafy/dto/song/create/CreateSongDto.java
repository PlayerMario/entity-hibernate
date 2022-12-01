package com.salesianostriana.dam.trianafy.dto.song.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSongDto {

    private String title;
    private Long artistId;
    private String album;
    private String year;

}
