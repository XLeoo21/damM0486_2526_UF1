package castellet.dam.m12.uf2.hibernate;

import java.util.List;
import java.util.Scanner;

public class MainView {

    private final Scanner scanner;

    public MainView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showMenu() {
        System.out.println("""
                \n\n
                --------------------------------------
                1. Afegir usuari
                2. Listar usuaris
                3. Buscar usuari per nom
                0. Salir
                --------------------------------------
                Tria una opció >>> 
                """);
        return scanner.nextInt();
    }

    public User userForm() {
        System.out.println("\n\n-------------------------------\n");
        System.out.print("Nom de l'usuari': ");
        scanner.nextLine(); // Limpiar buffer
        String name = scanner.nextLine();
        return new User(name);
    }


    



    public String askForUserName() {
                System.out.println("\n\n-------------------------------\n");

        System.out.print("Nom de l'usuari' (cerca parcial): ");
        scanner.nextLine(); // Limpiar buffer
        return scanner.nextLine();
    }





    public void userList(List<User> users) {
        System.out.println("\n\n-------------------------------\nLlista d'usuaris trobats:\n\n");
        if (users.isEmpty()) {
            System.out.println("No es troben usuaris.");
        } else {
            // Se invoca el método toString() de cada Athlete

            System.out.println(users.toString());
        }
    }
}
