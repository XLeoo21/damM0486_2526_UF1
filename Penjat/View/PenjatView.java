package Penjat.View;

import java.util.Scanner;

/*
 PenjatView
 - Encapsula tota la interacció per consola.
 - Prové mètodes estàtics senzills per mostrar menús, demanar dades i mostrar missatges.
 - Utilitza Scanner(System.in) compartit per evitar múltiples instàncies.
*/
public class PenjatView {

    public static Scanner scan = new Scanner(System.in);

    // Menú inicial (login/register/exit)
    public static void menuInicial() {
        System.out.println();
        System.out.println("====== MENU ======");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Sortir");
        System.out.println();
    }

    // Menú de sessió: numeració coherent per admin i per usuari normal
    public static void menuJugar(boolean isAdmin) {
        System.out.println();
        System.out.println("====== JOC ======");
        System.out.println("1. Jugar");
        if (isAdmin) {
            System.out.println("2. Editar paraules");
            System.out.println("3. Editar configuració");
            System.out.println("4. Sortir");
        } else {
            System.out.println("2. Sortir");
        }
        System.out.println();
    }

    // Missatge després de registrar
    public static void regOk(boolean u) {
        System.out.println();
        if(u == true) {
            System.out.println("Usuari registrat correctament.");
        } else {
            System.out.println("Usuari no registrat, el nom d'usuari ja existeix.");
        }
        System.out.println();
    }

    // Missatge després d'intentar login (mostra prompt si ha fallat)
    public static void logOk(boolean u){
        System.out.println();
        if(u == true) {
            System.out.println("Usuari loguejat correctament.");
        } else {
            System.out.print("Usuari incorrecte o contrasenya incorrectes, vols registrar-te? (S/N): ");
        }
    }

    // Lectures senzilles des de consola
    public static String askPass() {
        System.out.print("Introdueix la contrasenya: ");
        return scan.nextLine();
    }

    public static String askNameUser() {
        System.out.print("Introdueix el nom d'usuari: ");
        return scan.nextLine();
    }

    // Sol·licita dades per registrar: nom visible, username, password
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

    // Pide usuario y contraseña para login
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

    // Llegeix una opció numèrica amb control d'errors
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

    // Mostra la llista de paraules i la opció per afegir (N)
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
        System.out.println("N. Afegir paraula nova");
        System.out.println();
    }

    public static String askNewWord() {
        System.out.print("Introdueix la nova paraula (o deixa buit per cancel·lar): ");
        return scan.nextLine().trim();
    }

    public static String askEditSelection() {
        System.out.print("Selecciona número per editar o 'N' per afegir: ");
        return scan.nextLine().trim();
    }

    // Missatge genèric amb separació
    public static void showMessage(String msg) {
        System.out.println();
        System.out.println(msg);
        System.out.println();
    }

    // Muestra la paraula enmascarada
    public static void showMaskedWord(String masked) {
        System.out.println();
        System.out.println("Paraula:");
        System.out.println();
        System.out.println("  " + masked);
        System.out.println();
    }

    // Pide letra o palabra entera
    public static String askLetter() {
        System.out.print("Introdueix una lletra o la paraula sencera: ");
        return scan.nextLine().trim().toLowerCase();
    }

    // Muestra errores / máximos
    public static void showWrongCount(int wrongs, int maxWrongs) {
        System.out.println("Errors: " + wrongs + " / " + maxWrongs);
    }

    // Mostra benvinguda amb punts actuals
    public static void showWelcome(String name, int punts) {
        System.out.println();
        System.out.println("Benvingut/da " + name + ". Tens " + punts + " punts");
        System.out.println();
    }

    // Pide un int amb validació
    public static int askInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scan.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor no vàlid. Torna-ho a intentar.");
            }
        }
    }
}
