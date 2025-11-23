package dam.m6.uf2.View;

import java.util.List;
import java.util.Scanner;

import dam.m6.uf2.Model.Atleta;
import dam.m6.uf2.Model.Esport;

public class MainView {

    static Scanner sc = new Scanner(System.in);

    public int mainMenu() {
        System.out.println("\n-----------------------------------------------------");
        System.out.println("1. Afegir Esport\n" +
                "2. Afegir Atleta\n" +
                "3. Cercar per nom Atleta\n" +
                "4. Llistar Atleta per esport\n" +
                "5. Sortir\n");
        System.out.print("Introdueix la opció: ");
        int option = sc.nextInt();
        sc.nextLine(); // limpiar salto de línea pendiente
        System.out.println("-----------------------------------------------------\n");
        return option;
    }

    public static void showMessage(String msg) {
        System.out.println("\n" + msg + "\n");
    }

    public static void LlistaEsports(List<Esport> esports) {
        for (Esport esport : esports) {
            System.out.println(esport);
        }
    }

    public static void LlistaAtletas(List<Atleta> atletas) {
        for (Atleta atleta : atletas) {
            System.out.println(atleta);
        }
    }

    public static String esportForm() {
        System.out.print("Introdueix el nom de l'Esport: ");
        return sc.nextLine();
    }

    public static String atletaForm() {
        System.out.print("Introdueix el nom de l'Atleta: ");
        return sc.nextLine();
    }

    public static int askCodEsport() {
        System.out.print("Introdueix el codi de l'Esport: ");
        int codEsport = sc.nextInt();
        sc.nextLine(); // limpiar salto de línea pendiente
        return codEsport;
    }

    public static String askName() {
        System.out.print("Introdueix el nom: ");
        return sc.nextLine();
    }
}
