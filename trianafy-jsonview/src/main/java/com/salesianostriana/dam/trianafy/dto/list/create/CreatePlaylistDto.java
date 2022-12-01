package com.salesianostriana.dam.trianafy.dto.list.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlaylistDto {

    private Long id;
    private String name;
    private String description;

}
