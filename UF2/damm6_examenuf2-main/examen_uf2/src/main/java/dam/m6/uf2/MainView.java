package dam.m6.uf2;

import java.util.List;
import java.util.Scanner;

public class MainView {

    static Scanner sc = new Scanner(System.in);

    public MainView(Scanner scan) {

    }

    public static int mainMenu() {
        System.out.println("\n-----------------------------------------------------");
        System.out.println("1. Llistar llibres\n" +
                "2. Afegir llibres\n" +
                "3. Afegir llibre Hibernate\n" +
                "4. Sortir");
        System.out.print("Introdueix la opció: ");
        int option = sc.nextInt();
        sc.nextLine();
        System.out.println("-----------------------------------------------------\n");
        return option;
    }

    public static void showMessage(String msg) {
        System.out.println("\n" + msg + "\n");
    }

    public static void LlistaLlibres(List<Llibre> llibres) {
        for (Llibre llibre : llibres) {
            System.out.println(llibre);
        }
    }

    public static String askTitle() {
        System.out.print("Introdueix el titol del llibre: ");
        String title = sc.nextLine();
        return title;
    }

    public static String askAuthor() {
        System.out.print("Introdueix el autor del llibre: ");
        String author = sc.nextLine();
        return author;
    }

    public static LlibreH llibreForm() {
        System.out.println("----------------");
        System.out.println("Nom del Titol: ");
        String title = sc.nextLine();
        System.out.println("Introdueix el nom del autor: ");
        String author = sc.nextLine();
        sc.nextLine();
        LlibreH llibre = new LlibreH(0,title, author);

        return llibre;

    }
}
