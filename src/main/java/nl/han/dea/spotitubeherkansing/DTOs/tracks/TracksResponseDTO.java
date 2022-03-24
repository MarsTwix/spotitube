package nl.han.dea.spotitubeherkansing.DTOs.tracks;

import nl.han.dea.spotitubeherkansing.domains.Track;

import java.util.List;

public class TracksResponseDTO {
    private final List<Track> tracks;

    public TracksResponseDTO(List<Track> tracks){
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }
}
