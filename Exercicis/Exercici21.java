import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Exercici21 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Introdueix el nom del fitxer: ");
        String fileName = scan.nextLine();

        int count = 0;
        File f = new File(fileName);
        Scanner reader = new Scanner(f);

        while (reader.hasNextLine()) {
            String linia = reader.nextLine();
            int i = 0;
            while (i < linia.length()) {
                char c = linia.charAt(i);
                if (c == 'a' || c == 'A') {
                    count++;
                }
                i++;
            }
        }
        reader.close();
        System.out.println("Nombre de lletres A al fitxer: " + count);
    }
}
