package dam.m6.uf2;

public class Llibre {

    int id = 0;
    String title = "";
    String author = "";

    public Llibre(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Llibre(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String toString() {
        return "Id: " + id + " - Title: " + title + " - Author: " + author;
    }
}
