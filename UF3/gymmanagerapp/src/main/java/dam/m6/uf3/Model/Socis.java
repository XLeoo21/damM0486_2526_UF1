package dam.m6.uf3.Model;

import org.bson.Document;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe Socis
 * -------------
 * Representa un soci del gimnàs. Cada instància correspon a un document
 * dins de la col·lecció "Socis" de MongoDB.
 *
 * Aquesta classe conté totes les dades d'un soci i té mètodes per convertir
 * l'objecte a un Document (MongoDB) i per mostrar-lo en format string.
 */
public class Socis {

    // Camps del soci
    private String nom;
    private String cognom;
    private int edat;
    private double pes;
    private int altura;
    private String subscripcio;
    private ArrayList<String> rutina; // Exercicis que fa el soci
    private ArrayList<LocalDate> assistencia; // Dates d'assistència
    private String objectius; // Objectius del soci

    /**
     * Constructor
     */
    public Socis(String nom, String cognom, int edat, double pes, int altura,
            String subscripcio, ArrayList<String> rutina,
            ArrayList<LocalDate> assistencia, String objectius) {
        this.nom = nom;
        this.cognom = cognom;
        this.edat = edat;
        this.pes = pes;
        this.altura = altura;
        this.subscripcio = subscripcio;
        this.rutina = rutina;
        this.assistencia = assistencia;
        this.objectius = objectius;
    }

    /**
     * Converteix l'objecte Socis a un Document de MongoDB.
     * - Les dates d'assistència es converteixen a String en format ISO (YYYY-MM-DD)
     * perquè MongoDB no entén directament LocalDate.
     * - Això facilita inserir, actualitzar i llegir documents de la col·lecció.
     */
    public Document toDocument() {
        ArrayList<String> assistDates = new ArrayList<>();
        for (LocalDate d : assistencia) {
            assistDates.add(d.toString()); // Converteix LocalDate a String
        }

        // Crear Document amb tots els camps del soci
        return new Document("nom", nom)
                .append("cognom", cognom)
                .append("edat", edat)
                .append("pes", pes)
                .append("altura", altura)
                .append("subscripcio", subscripcio)
                .append("rutina", rutina)
                .append("assistencia", assistDates)
                .append("objectius", objectius);
    }

    // ───────────────────────────────────────────────
    // Getters: necessaris per llegir dades si cal actualitzar o mostrar
    public String getNom() {
        return nom;
    }

    public String getCognom() {
        return cognom;
    }

    public int getEdat() {
        return edat;
    }

    public double getPes() {
        return pes;
    }

    public int getAltura() {
        return altura;
    }

    public String getSubscripcio() {
        return subscripcio;
    }

    public ArrayList<String> getRutina() {
        return rutina;
    }

    public ArrayList<LocalDate> getAssistencia() {
        return assistencia;
    }

    public String getObjectius() {
        return objectius;
    }

    /**
     * toString() sobrescrit
     * ---------------------
     * Permet mostrar l'objecte en un format llegible per consola.
     */
    @Override
    public String toString() {
        return "\n----------------------------------\n" +
                "SOCI\n" +
                "----------------------------------\n" +
                "Nom: " + nom + " " + cognom + "\n" +
                "Edat: " + edat + " anys\n" +
                "Pes: " + pes + " kg\n" +
                "Altura: " + altura + " cm\n" +
                "Subscripció: " + subscripcio + "\n" +
                "Rutina: " + String.join(", ", rutina) + "\n" +
                "Assistència: " + assistencia + "\n" +
                "Objectius: " + objectius + "\n" +
                "----------------------------------";
    }
}
