package dam.m6.uf3.Model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Model
 * -------------
 * Gestiona totes les operacions CRUD (Create, Read, Update, Delete)
 * sobre la col·lecció "Socis" de la base de dades.
 */
public class Model {

    // Col·lecció MongoDB on es guarden els documents dels socis
    private MongoCollection<Document> col;

    /**
     * Constructor de Model
     * -------------------
     * Estableix la connexió amb la base de dades mitjançant ConnectionManager
     * i obté la col·lecció "Socis".
     */
    public Model() {
        MongoDatabase db = ConnectionManager.getConnection();
        col = db.getCollection("Socis");
    }

    /**
     * Inserir un nou soci a la col·lecció.
     * 
     * @param s Soci a afegir
     */
    public void inserirSoci(Socis s) {
        // Converteix l'objecte Socis a Document i l'afegeix a MongoDB
        col.insertOne(s.toDocument());
    }

    /**
     * Eliminar un soci segons el seu nom.
     * 
     * @param nom Nom del soci a eliminar
     */
    public void deleteSoci(String nom) {
        // Filtra per camp "nom" i elimina el document
        col.deleteOne(eq("nom", nom));
    }

    /**
     * Actualitzar un soci segons el seu nom.
     * 
     * @param nom Nom del soci existent
     * @param s   Nou objecte Socis amb les dades actualitzades
     */
    public void updateSoci(String nom, Socis s) {
        // Utilitza $set per substituir tots els camps amb el document del nou soci
        col.updateOne(eq("nom", nom), new Document("$set", s.toDocument()));
    }

    /**
     * Obtenir tots els socis de la col·lecció.
     * 
     * @return ArrayList de Socis
     */
    public ArrayList<Socis> getAllSocis() {
        ArrayList<Socis> llista = new ArrayList<>();
        // Recorre tots els documents i els converteix a objectes Socis
        for (Document doc : col.find()) {
            llista.add(documentToSoci(doc));
        }
        return llista;
    }

    /**
     * Obtenir socis segons el seu nom.
     * 
     * @param nom Nom a cercar
     * @return ArrayList de Socis que coincideixen amb el nom
     */
    public ArrayList<Socis> getSocisByNom(String nom) {
        ArrayList<Socis> llista = new ArrayList<>();
        // Filtra documents amb el camp "nom" igual al paràmetre
        for (Document doc : col.find(eq("nom", nom))) {
            llista.add(documentToSoci(doc));
        }
        return llista;
    }

    /**
     * Obtenir socis que hagin assistit entre dues dates.
     * 
     * @param start Data d'inici
     * @param end   Data final
     * @return ArrayList de Socis amb assistències dins l'interval
     */
    public ArrayList<Socis> getSocisByDate(LocalDate start, LocalDate end) {
        ArrayList<Socis> llista = new ArrayList<>();
        // Recorre tots els documents
        for (Document doc : col.find()) {
            // Recupera la llista d'assistències com a llista de strings
            List<String> assistDates = (List<String>) doc.get("assistencia");
            for (String d : assistDates) {
                LocalDate ld = LocalDate.parse(d); // Converteix el string a LocalDate
                // Si la data està dins l'interval, afegeix el soci a la llista
                if (!ld.isBefore(start) && !ld.isAfter(end)) {
                    llista.add(documentToSoci(doc));
                    break; // Només cal afegir-lo una vegada
                }
            }
        }
        return llista;
    }

    /**
     * Converteix un Document de MongoDB a un objecte Socis.
     * 
     * @param doc Document de MongoDB
     * @return Soci equivalent
     */
    private Socis documentToSoci(Document doc) {

        String nom = doc.getString("nom");
        String cognom = doc.getString("cognom");
        int edat = doc.getInteger("edat");

        // 🔥 LÍNIA CLAVE CORREGIDA
        double pes = ((Number) doc.get("pes")).doubleValue();

        int altura = doc.getInteger("altura");
        String subscripcio = doc.getString("subscripcio");

        // Rutina
        List<?> rawRutina = (List<?>) doc.get("rutina");
        ArrayList<String> rutina = new ArrayList<>();
        if (rawRutina != null) {
            for (Object o : rawRutina) {
                rutina.add(o.toString());
            }
        }

        // Assistència
        List<?> rawAssist = (List<?>) doc.get("assistencia");
        ArrayList<LocalDate> assistencia = new ArrayList<>();
        if (rawAssist != null) {
            for (Object o : rawAssist) {
                assistencia.add(LocalDate.parse(o.toString()));
            }
        }

        String objectius = doc.getString("objectius");

        return new Socis(
                nom, cognom, edat, pes, altura,
                subscripcio, rutina, assistencia, objectius);
    }

}
