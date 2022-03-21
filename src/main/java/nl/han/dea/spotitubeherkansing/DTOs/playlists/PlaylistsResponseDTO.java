package nl.han.dea.spotitubeherkansing.DTOs.playlists;

import java.util.List;

public class PlaylistsResponseDTO {
    List<PlaylistDTO> playlists;
    int length;

    public PlaylistsResponseDTO(List<PlaylistDTO> playlists, int length){
        this.playlists = playlists;
        this.length = length;
    }

    public List<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public int getLength() {
        return length;
    }
}
