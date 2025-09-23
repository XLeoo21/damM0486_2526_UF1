import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Exercici31 {
    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();

    public static void main(String[] args) throws IOException {

        int numAleatori;
        int numAnterior = 0;
        int count = 0;
        
        File f = new File("secret.bin");
        FileOutputStream fos = new FileOutputStream(f);
        DataOutputStream dos= new DataOutputStream(fos);

        while (count < 10){
            numAleatori = rand.nextInt(1,4);
            numAnterior += numAleatori;
            
            dos.writeInt(numAnterior);
            dos.writeChars(generarCodi());
            
            count++;
        }

        dos.close();

    }

    static String generarCodi() {
        String codiCaracters = "";
        int cont = 0;

        while(cont < 3){
            int charAleatori = rand.nextInt(0,26);
            char caracter = (char) ('A' + charAleatori);

            cont++;
            codiCaracters += caracter;
        }


        return codiCaracters;
    }
}
