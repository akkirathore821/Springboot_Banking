package com.bank.jwt_service_demo.service;

import com.bank.jwt_service_demo.model.Song;
import com.bank.jwt_service_demo.model.SongResponse;
import com.bank.jwt_service_demo.repo.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public List<SongResponse> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream()
                .map(song -> SongResponse.builder()
                        .songName(song.getSongName())
                        .build())
                .toList(); // or .collect(Collectors.toList())
    }
}
