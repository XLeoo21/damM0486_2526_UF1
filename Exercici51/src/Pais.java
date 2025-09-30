public class Pais {
    private String nom;
    private String codiISO;
    private int poblacio;
    private String capital;

    public Pais(String nom, String codiISO, String capital) {
        if (codiISO.length() != 3)
            throw new IllegalArgumentException("Els codis ISO sempre tenen 3 caràcters.");
        this.nom = nom;
        this.codiISO = codiISO;
        this.capital = capital;
    }

    public int getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(int poblacio) {
        if (poblacio < 0)
            throw new IllegalArgumentException("La població ha de ser positiva.");
        this.poblacio = poblacio;
    }

    public String getNom() {
        return nom;
    }

    public String getCodiISO() {
        return codiISO;
    }

    public String getCapital() {
        return capital;
    }

    @Override
    public String toString() {
        return "País " + nom + " (" + codiISO + "), capital: " + capital + ", població: " + poblacio;
    }
}
