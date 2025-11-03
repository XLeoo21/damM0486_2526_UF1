import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.Scanner;

public class Examenuf1_LionnelLaguna {

    public static void main(String[] args) throws IOException {

        // Falta el bucle general pero no volia menjarme el cap y perdre temps.
        File f = new File("users.txt");
        // System.out.println(returnUser(f));

        // EXERCICI 2
        File f2 = new File("shop.bin");
        RandomAccessFile randomF = new RandomAccessFile(f2, "r");
        int quit = 0;
        long pos = 0;
        int id = 0;
        do {
            randomF.seek(pos);
            System.out.println("Id: " + id);
            System.out.println("Codi producte: " + randomF.readInt());
            pos += 4;
            randomF.seek(pos);
            String name = readChars(randomF, 15);
            System.out.println("Nom: " + name);
            pos += 30;
            randomF.seek(pos);
            System.out.println("Preu: " + randomF.readFloat());
            id++;
            quit += 38;
            System.out.println("-------------------------------------------");
            pos += 4;
            randomF.seek(pos);
        } while (quit != f2.length());

        try (// EXERCICI 3
                Scanner scan = new Scanner(System.in)) {
            System.out.println("Quin producte vols editar: ");
            int idProducte = 0;

            if (scan.hasNextInt()) {
                idProducte = scan.nextInt();
                if (idProducte > id) {
                    System.out.println("No hi ha tants productes.");
                }

                pos = idProducte * 38;
                randomF.seek(pos);

                System.out.println("Codi producte: " + randomF.readInt());
                pos += 4;
                randomF.seek(pos);
                String name = readChars(randomF, 15);
                System.out.println("Nom: " + name);
                pos += 30;
                randomF.seek(pos);
                System.out.println("Preu: " + randomF.readFloat());
                System.out.println("-------------------------------------------");

                pos = idProducte * 38 + 4;
                System.out.println("Introdueix el nou nom: ");
                String newName = scan.nextLine();
                randomF.seek(pos);
                writeFixedString(randomF, newName, 15);

                System.out.println("Introdueix el nou preu: ");
                Float newPreu = scan.nextFloat();
                randomF.writeFloat(newPreu);

                // Tornar a mostrar la llista
                pos = 0;
                do {
                    randomF.seek(pos);
                    System.out.println("Id: " + id);
                    System.out.println("Codi producte: " + randomF.readInt());
                    pos += 4;
                    randomF.seek(pos);
                    name = readChars(randomF, 15);
                    System.out.println("Nom: " + name);
                    pos += 30;
                    randomF.seek(pos);
                    System.out.println("Preu: " + randomF.readFloat());
                    id++;
                    quit += 38;
                    System.out.println("-------------------------------------------");
                    pos += 4;
                    randomF.seek(pos);
                } while (quit != f2.length());

                // Exercici 4

                File log = new File("log.bin");
                FileOutputStream fos = new FileOutputStream(log);
                DataOutputStream dos = new DataOutputStream(fos);

                dos.writeChars(dataActual());
                dos.writeInt(idProducte);

            } else {
                System.out.println("Ha de ser un numero.");
            }
        }

    }

    public static String dataActual() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }

    public static String returnUser(File f) throws FileNotFoundException {

        Scanner scan = new Scanner(f);
        String user = "";

        while (scan.hasNextLine()) {
            String linea = scan.nextLine();
            int i = 0;
            while (i < linea.length()) {
                if (linea.charAt(i) == '#') {
                    i++;
                }
                char c = linea.charAt(i);
                user += c;
                i++;
            }
        }

        return user;
    }

    public static String login() {
        return "";
    }

    private static String readChars(RandomAccessFile fitxer, int nChars) throws IOException {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < nChars; i++) {
            char ch = fitxer.readChar();
            if (ch != '\0') {
                b.append(ch);
            }
            // System.out.println(i);
        }
        return b.toString().trim();
    }

    private static void writeFixedString(RandomAccessFile raf, String text, int length) throws IOException {
        StringBuilder sb = new StringBuilder(text);
        if (sb.length() > length) {
            sb.setLength(length); // truncar, basicament si pasa la longitud es retalla la paraula fins la
                                  // longitud adequada.
        } else {
            while (sb.length() < length) {
                sb.append(' '); // omplir amb espais
            }
        }
        raf.writeChars(sb.toString());
    }

}
