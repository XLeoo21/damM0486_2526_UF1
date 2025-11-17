package dam.m6.uf2.View;

import java.util.List;
import java.util.Scanner;

import dam.m6.uf2.Model.Esport;

public class MainView {

    /**
     * @return
     */
    public int mainMenu() {
        // TODO Auto-generated method stub
        System.out.println("\n-----------------------------------------------------");
        System.out.println("1. Afegir Esport\n" +
                "2. Afegir Atleta\n" +
                "3. Cercar per nom Atleta\n" +
                "4. Llistar Atleta per esport\n" +
                "5. Sortir\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Introdueix la opció: ");
        int option = sc.nextInt();
        System.out.println("-----------------------------------------------------\n");
        sc.nextLine();
        return option;
        // throw new UnsupportedOperationException("Unimplemented method 'mainMenu'");
    }

    // Missatge genèric amb separació
    public static void showMessage(String msg) {
        System.out.println();
        System.out.println(msg);
        System.out.println();
    }

    public static void LlistaEsports(List<Esport> esports) {
        for (Esport esport : esports) {
            System.out.println(esport);
        }
    }

    public static String esportForm() {
        System.out.println("Introdueix el nom de l'Esport: ");
        Scanner sc = new Scanner(System.in);
        String esport = sc.nextLine();
        return esport;

    }

}
