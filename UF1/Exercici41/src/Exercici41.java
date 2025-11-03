import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Exercici41 {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        

        System.out.print("Introdueix el nom d'usuari: ");
        String nom = scan.nextLine();

        System.out.print("Introdueix la contrasenya: ");
        String pass = scan.nextLine();

        File f = new File(nom + ".usr");

        if (f.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                User u = (User) ois.readObject();
                ois.close();

                if (u.pass.equals(pass)) {
                    System.out.println("Acces correcte a l'usuari: " + u.name);
                } else {
                    System.out.println("Acces no concedit: La contrasenya no és correcta");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No s'ha trobat l'usuari, vols registrar-te? (si/no): ");
            String resposta = scan.nextLine();

            if (resposta.equalsIgnoreCase("si")) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                    User newUser = new User(nom, pass);
                    oos.writeObject(newUser);
                    oos.close();
                    System.out.println("Usuari registrat correctament!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No s'ha creat cap usuari");
            }
        }
    }
}

