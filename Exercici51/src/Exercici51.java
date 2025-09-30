// Lionnel Laguna Fernandez
// Exercici 5.1
// Data ultima modificació: 30/09/2025

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Exercici51 {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String newName, name, codiISO, capital;
        int id, population;
        long pos = 0;

        System.out.println("Introdueix número de registre: ");
        id = scanner.nextInt();
        scanner.nextLine();

        try (RandomAccessFile fitxer = new RandomAccessFile("paisos.dat", "rw")) {
            
            pos = (id - 1) * 174;

            if (pos < 0 || pos >= fitxer.length())
                throw new IOException("Número de registre invàlid.");

            fitxer.seek(pos);
            fitxer.readInt(); 

            name = readChars(fitxer, 40);
            System.out.println("Pais actual: " + name);
            System.out.println("Introdueix el nou nom: ");
            newName = scanner.nextLine();
            pos = fitxer.getFilePointer();
            fitxer.seek(pos - 80);
            writeFixedString(fitxer, newName, 40);

            System.out.println("Codi ISO actual: " + readChars(fitxer, 3));
            System.out.println("Introdueix el nou codi ISO (3 lletres): ");
            codiISO = scanner.nextLine();
            pos = fitxer.getFilePointer();
            fitxer.seek(pos - 6); 
            writeFixedString(fitxer, codiISO, 3);

            System.out.println("Capital actual: " + readChars(fitxer, 40));
            System.out.println("Introdueix la nova capital: ");
            capital = scanner.nextLine();
            pos = fitxer.getFilePointer();
            fitxer.seek(pos - 80); 
            writeFixedString(fitxer, capital, 40);

            population = fitxer.readInt();
            System.out.println("País: " + name + ", població actual: " + population);
            System.out.println("Introdueix la nova població: ");
            population = scanner.nextInt();
            scanner.nextLine();

            if (population >= 0) {
                pos = fitxer.getFilePointer() - 4;
                fitxer.seek(pos);
                fitxer.writeInt(population);
            } else {
                System.err.println("La població ha de ser positiva.");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        scanner.close();
    }

    private static String readChars(RandomAccessFile fitxer, int nChars) throws IOException {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < nChars; i++) {
            char ch = fitxer.readChar();
            if (ch != '\0') {
                b.append(ch);
            }
        }
        return b.toString().trim();
    }

	// EscriptorRandomAccessFile escriu un String de nChars caracters al fitxer raf 
	// si el String es més llarg que nChars, es truncara
	// si el String es més curt que nChars, es completara amb espais
    private static void writeFixedString(RandomAccessFile raf, String text, int length) throws IOException {
        StringBuilder sb = new StringBuilder(text);
        if (sb.length() > length) {
            sb.setLength(length); // truncar, basicament si pasa la longitud es retalla la paraula fins la longitud adequada.
        } else {
            while (sb.length() < length) {
                sb.append(' '); // omplir amb espais
            }
        }
        raf.writeChars(sb.toString());
    }
}
