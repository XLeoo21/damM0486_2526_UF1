import java.io.*;
import java.util.Scanner;

public class Exercici22 {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String fileName = "usertext.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        boolean continuar = true;
        while (continuar) {
            System.out.print("Escriu una cadena (quit per sortir): ");
            String text = scan.nextLine();

            if (text.equalsIgnoreCase("quit")) {
                continuar = false;
            } else {
                writer.write(text);
                writer.newLine();
            }
        }
        writer.close();

        System.out.println("\nContingut del fitxer:");
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String linia = reader.readLine();
        while (linia != null) {
            System.out.println(linia);
            linia = reader.readLine();
        }
        reader.close();
    }
}
