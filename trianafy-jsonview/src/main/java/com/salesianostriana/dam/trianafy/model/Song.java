package com.salesianostriana.dam.trianafy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String idCancion;

    private String title;
    private String album;
    @Column(name = "year_of_song")
    private String year;

    @ManyToOne(fetch = FetchType.EAGER)
    private Artist artist;

    public Song(String title, String album, String year) {
        this.title = title;
        this.album = album;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(idCancion, song.idCancion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCancion);
    }

}
