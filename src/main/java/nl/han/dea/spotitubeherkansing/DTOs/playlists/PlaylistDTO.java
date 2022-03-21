package nl.han.dea.spotitubeherkansing.DTOs.playlists;

import nl.han.dea.spotitubeherkansing.domains.Track;

import java.util.List;

public class PlaylistDTO {
    private int id;
    private String name;
    private boolean owner;
    private List<Track> tracks;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }
}
