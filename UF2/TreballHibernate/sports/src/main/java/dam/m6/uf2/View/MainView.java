package dam.m6.uf2.View;

import java.util.List;
import java.util.Scanner;

import dam.m6.uf2.Tables.deportes;
import dam.m6.uf2.Tables.deportistas;

public class MainView {
    private final Scanner scanner;

    public MainView(Scanner scanner) {
        this.scanner = scanner;

    }

    public int mainMenu() {
        System.out.println("\n-----------------------------------------------------");
        System.out.println("1. Afegir Esport\n" +
                "2. Afegir Atleta\n" +
                "3. Cercar per nom Atleta\n" +
                "4. Llistar Atleta per esport\n" +
                "5. Sortir\n");
        System.out.print("Introdueix la opció: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        System.out.println("-----------------------------------------------------\n");
        return option;
    }

    public deportes sportForm() {
        deportes sport = new deportes();
        System.out.print("Nom de l'esport: ");
        sport.setNombre(scanner.nextLine());
        return sport;
    }

    public String askAthleteNameToSearch() {
        System.out.print("Introdueix el nom (o part del nom) de l'atleta: ");
        return scanner.nextLine();
    }

    public void sportList(List<deportes> esports) {
        System.out.println("\n\n-------------------------------\nLlista d'esports trobats:\n\n");
        if (esports.isEmpty()) {
            System.out.println("No es troben esports.");
        } else {

            System.out.println(esports.toString());
        }
    }

    public void athleteList(List<deportistas> deportistas) {
        System.out.println("\n\n-------------------------------\nLlista d'esportistes trobats:\n\n");
        if (deportistas.isEmpty()) {
            System.out.println("No es troben esportirtes.");
        } else {

            System.out.println(deportistas.toString());
        }
    }

    public String askAthleteName() {
        System.out.print("Nom de l'atleta: ");
        return scanner.nextLine();
    }

}
