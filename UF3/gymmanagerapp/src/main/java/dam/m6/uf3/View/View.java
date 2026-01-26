package dam.m6.uf3.View;

import dam.m6.uf3.Model.Socis;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe View
 * ------------
 * Gestió de la interfície d'usuari.
 * Aquesta classe s'encarrega de mostrar menús, formularis i llistats,
 * així com de recollir dades de l'usuari.
 */
public class View {

    // Scanner per llegir entrada de l'usuari
    private Scanner scanner = new Scanner(System.in);

    /**
     * Mostra el menú principal i retorna l'opció escollida.
     * 
     * @return enter amb l'opció triada per l'usuari
     */
    public int mainMenu() {
        System.out.println("\n---------------- MENÚ ----------------");
        System.out.println("1. Afegir Soci");
        System.out.println("2. Eliminar Soci");
        System.out.println("3. Modificar Soci");
        System.out.println("4. Llistar tots els socis");
        System.out.println("5. Llistar socis entre dates");
        System.out.println("6. Cercar Soci per nom");
        System.out.println("7. Sortir");
        System.out.print("Tria una opció: ");

        int op = scanner.nextInt(); // Llegeix l'opció
        scanner.nextLine(); // Neteja el buffer de l'entrada
        return op;
    }

    /**
     * Formulari per afegir un nou soci.
     * Demana totes les dades i retorna un objecte Socis.
     * 
     * @return Soci amb les dades introduïdes
     */
    public Socis formulariNouSoci() {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Cognom: ");
        String cognom = scanner.nextLine();
        System.out.print("Edat: ");
        int edat = scanner.nextInt();
        System.out.print("Pes: ");
        double pes = scanner.nextDouble();
        System.out.print("Altura: ");
        int altura = scanner.nextInt();
        scanner.nextLine(); // Neteja buffer després de llegir números

        System.out.print("Subscripció: ");
        String subscripcio = scanner.nextLine();

        // Rutina: separada per comes
        System.out.print("Rutina (separada per comes): ");
        String[] rut = scanner.nextLine().split(",");
        ArrayList<String> rutina = new ArrayList<>();
        for (String r : rut)
            rutina.add(r.trim());

        // Assistència: dates separades per comes
        System.out.print("Assistència (YYYY-MM-DD, separades per comes): ");
        String[] ass = scanner.nextLine().split(",");
        ArrayList<LocalDate> assistencia = new ArrayList<>();
        for (String a : ass)
            assistencia.add(LocalDate.parse(a.trim()));

        System.out.print("Objectius: ");
        String obj = scanner.nextLine();

        // Retorna un nou soci amb totes les dades introduïdes
        return new Socis(nom, cognom, edat, pes, altura, subscripcio, rutina, assistencia, obj);
    }

    /**
     * Formulari per actualitzar un soci.
     * Només crida formulariNouSoci, ja que el procés és igual.
     * 
     * @return Soci amb les dades actualitzades
     */
    public Socis formulariUpdate() {
        System.out.println("Introdueix les dades noves del soci:");
        return formulariNouSoci();
    }

    /**
     * Demana a l'usuari un nom de soci.
     * 
     * @return String amb el nom introduït
     */
    public String demanarNom() {
        System.out.print("Nom del soci: ");
        return scanner.nextLine();
    }

    /**
     * Demana una data a l'usuari i la converteix a LocalDate.
     * 
     * @param missatge Missatge que es mostrarà a l'usuari
     * @return LocalDate amb la data introduïda
     */
    public LocalDate demanarData(String missatge) {
        System.out.print(missatge + " (YYYY-MM-DD): ");
        return LocalDate.parse(scanner.nextLine());
    }

    /**
     * Mostra els resultats d'una llista de socis.
     * Si la llista està buida, mostra un missatge indicant-ho.
     * 
     * @param llista ArrayList de Socis a mostrar
     */
    public void mostrarResultats(ArrayList<Socis> llista) {
        if (llista.isEmpty()) {
            System.out.println("No s'han trobat resultats.");
        } else {
            for (Socis s : llista) {
                System.out.println(s); // Crida al toString() del Soci
            }
        }
    }

}
