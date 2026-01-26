package dam.m6.uf3.Controller;

import dam.m6.uf3.Model.*;
import dam.m6.uf3.View.View;
import java.time.LocalDate;

/**
 * Classe Controller
 * -----------------
 * Aquesta és la capa de control dins del patró MVC.
 * S'encarrega de coordinar la View (interfície amb l'usuari)
 * i el Model (gestió de la base de dades).
 */
public class Controller {
    public static void main(String[] args) {

        // Creem instàncies de Model i View
        Model model = new Model();
        View view = new View();

        int op; // Variable per guardar l'opció triada al menú

        // Bucle principal del programa: mostra el menú fins que l'usuari tria sortir
        do {
            op = view.mainMenu(); // Mostra menú i llegeix l'opció

            switch (op) {

                case 1: // Afegir soci
                    Socis nou = view.formulariNouSoci(); // Demana totes les dades al usuari
                    model.inserirSoci(nou); // Inserim el soci a la base de dades
                    System.out.println("Soci afegit correctament!");
                    break;

                case 2: // Eliminar soci
                    String nomEliminar = view.demanarNom(); // Demana el nom del soci a eliminar
                    model.deleteSoci(nomEliminar); // Elimina el soci de la col·lecció
                    System.out.println("Soci eliminat (si existia).");
                    break;

                case 3: // Modificar soci
                    String nomModificar = view.demanarNom(); // Nom del soci a modificar
                    Socis sociActualitzat = view.formulariUpdate(); // Nova informació del soci
                    model.updateSoci(nomModificar, sociActualitzat); // Actualitza el soci
                    System.out.println("Soci actualitzat correctament!");
                    break;

                case 4: // Llistar tots els socis
                    view.mostrarResultats(model.getAllSocis());
                    break;

                case 5: // Llistar socis entre dates
                    LocalDate start = view.demanarData("Data inici"); // Demana data d'inici
                    LocalDate end = view.demanarData("Data fi"); // Demana data de final
                    view.mostrarResultats(model.getSocisByDate(start, end));
                    break;

                case 6: // Cercar soci per nom
                    String nomCerca = view.demanarNom();
                    view.mostrarResultats(model.getSocisByNom(nomCerca));
                    break;

                case 7: // Sortir
                    System.out.println("Sortint...");
                    break;

                default: // Opció invàlida
                    System.out.println("Opció no vàlida.");
            }

        } while (op != 7); // Continua fins que l'usuari tria sortir
    }
}
