package nl.han.dea.spotitubeherkansing.domains;

public class Playlist {
    int id;
    String name;
    int owner;

    public Playlist(int id, String name, int owner){
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

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }
}
