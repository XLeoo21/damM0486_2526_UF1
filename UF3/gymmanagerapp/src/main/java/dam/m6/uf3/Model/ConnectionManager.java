package dam.m6.uf3.Model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Classe ConnectionManager
 * ------------------------
 * S'encarrega de gestionar la connexió amb la base de dades MongoDB.
 * Aquesta classe utilitza un mètode estàtic, per tant, no cal crear una
 * instància.
 */
public class ConnectionManager {

    // URI de connexió a MongoDB Atlas.
    // Conté l'usuari, la contrasenya codificada (%3F = ?) i l'adreça del cluster.
    private static final String URI = "mongodb+srv://llaguna:llaguna5617%3F@inscastellet.f4jjgcr.mongodb.net/?appName=inscastellet";

    // Nom de la base de dades a la qual ens connectarem
    private static final String DATABASE_NAME = "GymManager";

    /**
     * Retorna una connexió amb la base de dades MongoDB.
     * 
     * @return MongoDatabase object, representant la base de dades seleccionada
     */
    public static MongoDatabase getConnection() {
        // Crear el client de MongoDB amb l'URI especificada
        MongoClient client = MongoClients.create(URI);

        // Obtenir la base de dades amb el nom DATABASE_NAME
        MongoDatabase database = client.getDatabase(DATABASE_NAME);

        // Mostrar un missatge a consola confirmant la connexió
        System.out.println("Connectat a la base de dades: " + DATABASE_NAME);

        // Retornar l'objecte MongoDatabase per poder fer operacions CRUD
        return database;
    }
}
