package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository repository;

    public Song add(Song song) {
        return repository.save(song);
    }

    public Optional<Song> findById(Long id) {
        return repository.findById(id);
    }

    public List<Song> findAll() {
        return repository.findAll();
    }

    public Song edit(Song song) {
        return repository.save(song);
    }

    public void delete(Song song) {
        repository.delete(song);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void setearArtistaBorrado(Artist a) {
        findAll().forEach(song -> {
//            if (song.getArtist().getId() == a.getId() && song.getArtist() != null) { // NO TOCAR
//                song.setArtist(null); // NO TOCAR
//            }
            if(song.getArtist() != null) {
                if(song.getArtist().getId() == a.getId()) {
                    song.setArtist(null);
                }
            }
        });
    }

}
