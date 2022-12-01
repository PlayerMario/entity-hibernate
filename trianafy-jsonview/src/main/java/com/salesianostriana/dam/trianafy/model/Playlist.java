package com.salesianostriana.dam.trianafy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Playlist {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String idPlaylist;

    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Song> songs = new ArrayList<>();

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Playlist(String name) {
        this.name = name;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void deleteSong(Song song) {
        songs.remove(song);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(idPlaylist, playlist.idPlaylist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlaylist);
    }
}
