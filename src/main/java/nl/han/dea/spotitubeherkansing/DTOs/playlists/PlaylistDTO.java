package nl.han.dea.spotitubeherkansing.DTOs.playlists;

public class PlaylistDTO {
    private int id;
    private String name;
    private boolean owner;

    public PlaylistDTO() {
    }

    public PlaylistDTO(int id, String name, boolean owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
