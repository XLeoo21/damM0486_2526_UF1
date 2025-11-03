import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Exercici32 {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        System.out.println("Introdueix el nom del fitxer: ");
        String fileName = scan.nextLine();
        File f = new File(fileName);
        boolean trobat = false;

        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);

        System.out.println("Introdueix el codi a buscar: ");
        int codiUsuari = scan.nextInt();

        int numRegistres = (int) f.length();

        for (int i = 0; i < numRegistres && !trobat; i++) {
            int secret = dis.readInt();

            String codiFitxer = "";
            for (int j = 0; j < 3; j++) {
                codiFitxer += dis.readChar();
            }

            if (secret == codiUsuari) {
                System.out.println("Numero trobat, secret = " + codiFitxer);
                trobat = true;
            }
        }

        dis.close();

        if (!trobat) {
            System.out.println("No s'ha trobat el codi");
        }

    }
}
