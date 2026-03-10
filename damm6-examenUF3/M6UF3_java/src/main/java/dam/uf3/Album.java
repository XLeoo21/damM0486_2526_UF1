package dam.uf3;

import org.bson.Document;

public class Album {
    private String id;
    private String artist;
    private String title;
    private String date;

    public Album(String artist, String title, String date) {
        this.artist = artist;
        this.title = title;
        this.date = date;
    }

    public Album(String id, String artist, String title, String date) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public Document toDocument() {
        Document doc = new Document("artist", artist)
                .append("title", title)
                .append("date", date);
        if (id != null)
            doc.append("id", id);
        return doc;
    }

    public static Album fromDocument(Document doc) {
        if (doc == null)
            return null;
        String id = null;
        if (doc.containsKey("_id") && doc.get("_id") != null) {
            try {
                id = doc.getObjectId("_id").toHexString();
            } catch (ClassCastException e) {
                Object raw = doc.get("_id");
                id = raw == null ? null : raw.toString();
            }
        } else {
            id = doc.getString("id");
        }
        String artist = doc.getString("artist");
        String title = doc.getString("title");
        String date = doc.getString("date");
        return new Album(id, artist, title, date);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) sb.append("[").append(id).append("]\n");        
        sb.append(artist == null ? "<unknown artist>" : artist);
        sb.append(" - ");

        sb.append(title == null ? "<untitled>" : title);
        if (date != null) sb.append("\n").append(date).append("");
        sb.append("\n--------------------------------- ");

        return sb.toString();
    }
}