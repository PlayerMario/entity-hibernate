package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository repository;

    public Playlist add(Playlist playlist) {
        return repository.save(playlist);
    }

    public Optional<Playlist> findById(Long id) {
        return repository.findById(id);
    }

    public List<Playlist> findAll() {
        return repository.findAll();
    }

    public Playlist edit(Playlist playlist) {
        return repository.save(playlist);
    }

    public void delete(Playlist playlist) {
        repository.delete(playlist);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void borrarCancionListas(Song s) {
        List<Playlist> listaPlaylist = findAll();
        for (Playlist p : listaPlaylist) {
            while (p.getSongs().contains(s)) {
                p.getSongs().remove(s);
            }
        }
    }

}
