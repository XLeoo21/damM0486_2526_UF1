package Penjat.View;

import java.util.Scanner;

/*
 Vista estàtica encarregada d'interacció amb la consola.
 Aquesta versió afegeix salts de línia i format per fer la partida més agradable visualment.
*/
public class PenjatView {

    static Scanner scan = new Scanner(System.in);

    public static void menuInicial() {
        System.out.println();
        System.out.println("====== MENU ======");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Sortir");
        System.out.println();
    }

    // Menú de sessió amb separació visual
    public static void menuJugar(boolean isAdmin) {
        System.out.println();
        System.out.println("====== JOC ======");
        System.out.println("1. Jugar");
        if (isAdmin) {
            System.out.println("2. Afegir paraules");
            System.out.println("3. Sortir");
        } else {
            System.out.println("2. Sortir");
        }
        System.out.println();
    }

    public static void regOk(boolean u) {
        System.out.println();
        if(u == true) {
            System.out.println("Usuari registrat correctament.");
        } else {
            System.out.println("Usuari no registrat, el nom d'usuari ja existeix.");
        }
        System.out.println();
    }

    public static void logOk(boolean u){
        System.out.println();
        if(u == true) {
            System.out.println("Usuari loguejat correctament.");
        } else {
            System.out.print("Usuari incorrecte o contrasenya incorrectes, vols registrar-te? (S/N): ");
        }
    }

    public static String askPass() {
        System.out.print("Introdueix la contrasenya: ");
        return scan.nextLine();
    }

    public static String askNameUser() {
        System.out.print("Introdueix el nom d'usuari: ");
        return scan.nextLine();
    }


    public static String[] askRegister() {
        String[] user = new String[3];
        System.out.println();
        System.out.print("Introdueix el nom: ");
        user[0] = scan.nextLine();
        System.out.print("Introdueix el nom d'usuari: ");
        user[1] = scan.nextLine();
        System.out.print("Introdueix la contrasenya: ");
        user[2] = scan.nextLine();
        System.out.println();
        return user;
    }

    public static String[] askLogin() {
        String[] user = new String[2];
        System.out.println();
        System.out.print("Introdueix el nom d'usuari: ");
        user[0] = scan.nextLine();
        System.out.print("Introdueix la contrasenya: ");
        user[1] = scan.nextLine();
        System.out.println();
        return user;
    }

    public static int askOption() {
        System.out.print("Introdueix la teva opcio: ");
        int op = 0;
        try {
            op = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            op = -1;
        }
        return op;
    }

    public static String askYesNo(){
        return scan.nextLine();
    }

    public static String askName(){
        System.out.print("Introdueix el nom: ");
        return scan.nextLine();
    }

    // Mostrar la llista de paraules amb una línia en blanc abans i després
    public static void showWords(String[] words) {
        System.out.println();
        System.out.println("Llista de paraules:");
        if (words == null || words.length == 0) {
            System.out.println("[Cap paraula a la llista]");
        } else {
            for (int i = 0; i < words.length; i++) {
                System.out.println((i+1) + ". " + words[i]);
            }
        }
        System.out.println();
    }

    public static String askNewWord() {
        System.out.print("Introdueix la nova paraula (o deixa buit per cancel·lar): ");
        return scan.nextLine().trim();
    }

    // Missatge genèric amb salt de línia abans
    public static void showMessage(String msg) {
        System.out.println();
        System.out.println(msg);
        System.out.println();
    }

    // Mostra la paraula enmascarada centrada amb salts de línia addicionals per destacar-la
    public static void showMaskedWord(String masked) {
        System.out.println();
        System.out.println("Paraula:");
        System.out.println();
        System.out.println("  " + masked);
        System.out.println();
    }

    // Entrada de lletra o paraula amb una línia en blanc abans per separar del text anterior
    public static String askLetter() {
        System.out.print("Introdueix una lletra o la paraula sencera: ");
        return scan.nextLine().trim().toLowerCase();
    }

    // Mostra el comptador d'errors amb una línia en blanc per separació
    public static void showWrongCount(int wrongs, int maxWrongs) {
        System.out.println("Errors: " + wrongs + " / " + maxWrongs);
    }

}
