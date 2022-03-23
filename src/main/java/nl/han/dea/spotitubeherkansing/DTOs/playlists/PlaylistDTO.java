package nl.han.dea.spotitubeherkansing.DTOs.playlists;

public class PlaylistDTO {
    private int id;
    private String name;
    private boolean owner;

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
}
