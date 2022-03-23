package nl.han.dea.spotitubeherkansing.domains;

public class Track {
    private int id;
    private String title;
    private String performer;
    private int duration;
    private String album;
    private int playcount;
    private String publicationDate;
    private String description;
    private boolean offlineAvailable;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getPerformer() {
        return performer;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum() {
        return album;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOfflineAvailable(boolean offlineAvailable) {
        this.offlineAvailable = offlineAvailable;
    }

    public boolean getOfflineAvailable(){
        return offlineAvailable;
    }
}
