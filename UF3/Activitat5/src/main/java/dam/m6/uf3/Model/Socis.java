package dam.m6.uf3.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Socis {

    private String nom;
    private String cognom;
    private int edat;
    private double pes;
    private int altura;
    private String subscripcio;
    private ArrayList<String> rutina;
    private ArrayList<LocalDate> assistencia;
    private String objectius;

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

    // ────────────── CONVERTIR A JSON ──────────────
    public String toJson() {
        JSONObject obj = new JSONObject();
        obj.put("nom", nom);
        obj.put("cognom", cognom);
        obj.put("edat", edat);
        obj.put("pes", pes);
        obj.put("altura", altura);
        obj.put("subscripcio", subscripcio);

        JSONArray rut = new JSONArray();
        for (String r : rutina) rut.put(r);
        obj.put("rutina", rut);

        JSONArray ass = new JSONArray();
        for (LocalDate d : assistencia) ass.put(d.toString());
        obj.put("assistencia", ass);

        obj.put("objectius", objectius);

        return obj.toString();
    }

    @Override
    public String toString() {
        return "\nSoci: " + nom + " " + cognom +
               "\nEdat: " + edat + " anys" +
               "\nPes: " + pes + " kg" +
               "\nAltura: " + altura + " cm" +
               "\nSubscripció: " + subscripcio +
               "\nRutina: " + String.join(", ", rutina) +
               "\nAssistència: " + assistencia +
               "\nObjectius: " + objectius + "\n";
    }

    // ────────────── GETTERS ──────────────
    public String getNom() { return nom; }
    public String getCognom() { return cognom; }
    public int getEdat() { return edat; }
    public double getPes() { return pes; }
    public int getAltura() { return altura; }
    public String getSubscripcio() { return subscripcio; }
    public ArrayList<String> getRutina() { return rutina; }
    public ArrayList<LocalDate> getAssistencia() { return assistencia; }
    public String getObjectius() { return objectius; }
}