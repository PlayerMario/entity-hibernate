package com.salesianostriana.dam.trianafy.dto.list.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPlaylistDto {

    private Long id;
    private String name;
    private int numberOfSongs;

}
