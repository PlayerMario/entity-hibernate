package com.salesianostriana.dam.trianafy.dto.list.get;

import com.salesianostriana.dam.trianafy.dto.song.get.GetSongDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOnePlaylistDto {

    private Long id;
    private String name;
    private String description;
    private List<GetSongDto> songs = new ArrayList<>();

}
