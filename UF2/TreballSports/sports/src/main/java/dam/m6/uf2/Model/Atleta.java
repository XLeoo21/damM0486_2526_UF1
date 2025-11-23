package dam.m6.uf2.Model;

public class Atleta {
    int cod = 0;
    String nom = "";
    int codEsport = 0;
    String deporte = "";

    public Atleta(int cod, String nom, int codEsport) {
        this.cod = cod;
        this.nom = nom;
        this.codEsport = codEsport;
    }

    public Atleta(int cod, String nom, String deporte) {
        this.cod = cod;
        this.nom = nom;
        this.deporte = deporte;
    }

    public String toString() {
        return "Cod: " + cod + " - Nom: " + nom + " - Esport: " + deporte;
    }

    public String getNom() {
        return nom;
    }

    public int getCodEsport() {
        return codEsport;
    }
}
