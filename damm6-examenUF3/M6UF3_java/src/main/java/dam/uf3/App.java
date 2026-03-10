package dam.uf3;

//import static com.mongodb.client.model.Filters.eq;
////import static com.mongodb.client.model.Filters.regex;
import org.bson.Document;
//import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
////import com.mongodb.client.model.Updates;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;

public class App {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    // connection parameters
    private String connectionString;
    private String databaseName;
    // shared scanner for user input
    private Scanner scanner;

    public App() {
        /* EX2 DEFINEIX AQUESTES VARIABLES */
        this.connectionString = "mongodb://localhost:27017";
        this.databaseName = "examen";
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase(databaseName);
        this.collection = database.getCollection("albums");

        // create a single scanner instance for the whole app
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        App app = new App();
        app.menuLoop();
    }

    /**
     * Display the interactive album menu and process user commands.
     * This method encapsulates the loop previously located in main.
     */
    public void menuLoop() {
        boolean running = true;
        while (running) {

            System.out.println();
            System.out.println("=== Menú Álbums ===");
            System.out.println("1) Afegir àlbum");
            System.out.println("2) Llistar àlbums");
            System.out.println("3) Llistar entre dates");
            System.out.println("4) Sortir");
            System.out.print("Tria una opció: ");
            scanner.reset();
            // Clear the scanner buffer for the next input

            String opt = this.scanner.nextLine().trim();
            switch (opt) {
                case "1":
                    Album newAlbum = albumForm();
                    // Afegir àlbum a MongoDB directament, SENSE API
                    addAlbum(newAlbum);
                    break;
                case "2":
                    // LListar els àlbums directament a mongo, SENSE API (no cal fer cap filtrat,
                    // només mostrar tots els àlbums)
                    llistaAlbums();
                    break;
                case "3":
                    // Fem petició a la API i mostrem els resultats quan arribin (no cal esperar a
                    // que arribin per mostrar el menú de nou)
                    llistatEntreDates(); // Aquesta funció ja fa el print de la resposta, només cal esperar a que arribi
                    System.out.println("Esperant resposta...");
                    System.out.println("...Prem ENTER per toranar al menú...");
                    opt = this.scanner.nextLine().trim();

                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Opció desconeguda.");
            }

        }
        // close shared scanner when loop exits
        scanner.close();
    }

    public void addAlbum(Album album) {

        /*********************************************************/
        /*
         * Ex3
         * /* AFEGIR ALBUM DIRECTAMENT A MONGO
         */
        /*********************************************************/

        // System.out.println("Àlbum afegit: " + doc.toJson());

        Document d = new Document(album.toDocument());
        collection.insertOne(d);
        System.out.println("==================================================");
        System.out.println("Album afegit: " + d.toJson());
        System.out.println("==================================================");
    }

    public void llistaAlbums() {

        /*********************************************************
         * Ex4
         * LLISTAR TOTS ELS ALBUMS DIRECTAMENT A MONGO
         *********************************************************/

        System.out.println("\nLlistar albums:");
        for (Document doc : collection.find()) {
            System.out.println("=============================================================================");
            System.out.println("Artista: " + doc.getString("artist") +
                    ", Titol: " + doc.getString("title") +
                    ", Data: " + doc.getString("date"));
            System.out.println("=============================================================================");
        }

    }

    /**
     * 
     * Fes el query a la URI de l'API per obtenir els àlbums entre les dates
     * indicades. La resposta serà un JSON que hauràs de parsejar manualment (sense
     * usar biblioteques externes) per mostrar els àlbums obtinguts.
     */
    public void llistatEntreDates() {
        System.out.print("Data inicial (YYYY-MM-DD): ");
        String dataIni = this.scanner.nextLine();
        System.out.print("Data final (YYYY-MM-DD): ");
        String dataFi = this.scanner.nextLine();
        System.out.println("Searching between " + dataIni + " and " + dataFi + ":");

        try {
            /*********************************************************/
            /* Ex 7a (0.5p) ESCRIU LA URI DE LA API */
            /*********************************************************/

            String uri = "mongodb://127.0.0.1:27017"; /** URI HERE */
            System.out.println("API URI: " + uri);

            /*********************************************************/
            /* Ex 7b (1.5p) ESCRIU LA PETICIO A LA API I FES PRINT DELS RESULTATS */
            /*********************************************************/

            HttpClient cli = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();

            // No hem dona temps

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Album albumForm() {
        System.out.print("Enter artist: ");
        String artist = this.scanner.nextLine();
        System.out.print("Enter title: ");
        String title = this.scanner.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = this.scanner.nextLine();
        return new Album(artist, title, date);
    }

}