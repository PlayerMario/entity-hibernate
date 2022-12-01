package com.salesianostriana.dam.trianafy.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Artist {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String idArtist;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(idArtist, artist.idArtist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArtist);
    }

}
