package dam.m6.uf2.Model;

public class Esport {
	int cod = 0;
	String nom = "";

	public Esport(int cod, String nom) {
		this.cod = cod;
		this.nom = nom;
	}

	public String toString() {
		return "Cod: " + cod + " - Nom: " + nom;
	}

	public String getNom() {
		return nom;
	}

	public int getCod() {
		return cod;
	}
}
