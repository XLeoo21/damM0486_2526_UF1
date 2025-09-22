import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Exercici22 {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        File f = new File("SaveFile.txt");
        String userString = "";
        FileOutputStream fos = new FileOutputStream(f);

        while (!userString.equals("quit")) {

            System.out.println("Enter a string to exit put quit: ");
            userString = scan.nextLine();

            if (!userString.equals("quit")) {
                fos.write(userString.getBytes());
                fos.write("\n".getBytes());
            }

        }

        fos.close();

        FileInputStream fis = new FileInputStream(f);

        int b = 0;
        while (b != -1) {
            b = fis.read();
            if (b != -1) {
                System.out.print((char) b);
            }
        }
    }
}
