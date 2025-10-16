package Penjat.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/*
 Gestiona el fitxer de paraules "Penjat/Model/paraules.txt" sense usar BufferedReader/Writer.
 Procediment general:
  - Per llegir: llegim tots els bytes amb FileInputStream, convertim a String (UTF-8),
    normalitzem salts de línia i separem per '\n' per obtenir línies.
  - Per escriure: convertim la paraula + salt de línia a bytes UTF-8 i fem append amb FileOutputStream.
  - Comprovem duplicats treballant amb l'array retornat per listWords.
*/
public class WordManager {

    private static final String DIR = "Penjat/Model";
    private static final String FILE = "paraules.txt";

    // Retorna l'objecte File per al fitxer de paraules, assegurant el directori
    private static File getFile() {
        File dir = new File(DIR);
        if (!dir.exists()) dir.mkdirs();
        return new File(dir, FILE);
    }

    // Llegeix el fitxer complet com a bytes i retorna un array de línies (sense línies buides)
    public static String[] listWords() {
        File f = getFile();
        try {
            if (!f.exists()) {
                // Si no existeix, el creem i retornem array buit
                f.createNewFile();
                return new String[0];
            }
            long length = f.length();
            if (length == 0) return new String[0];

            // Llegir tots els bytes del fitxer
            byte[] data = new byte[(int) length];
            try (FileInputStream fis = new FileInputStream(f)) {
                int read = 0;
                while (read < data.length) {
                    int r = fis.read(data, read, data.length - read);
                    if (r == -1) break;
                    read += r;
                }
            }

            // Convertir a String amb codificació UTF-8
            String content = new String(data, StandardCharsets.UTF_8);

            // Normalitzar salts de línia per fer el parsing més senzill
            content = content.replace("\r\n", "\n").replace("\r", "\n");

            // Comptar línies no buides per determinar la mida de l'array
            int linesCount = 0;
            int pos = 0;
            while (pos < content.length()) {
                int next = content.indexOf('\n', pos);
                String line;
                if (next == -1) {
                    line = content.substring(pos);
                    pos = content.length();
                } else {
                    line = content.substring(pos, next);
                    pos = next + 1;
                }
                if (!line.trim().isEmpty()) linesCount++;
            }

            if (linesCount == 0) return new String[0];

            // Omplir l'array amb les línies no buides
            String[] words = new String[linesCount];
            pos = 0;
            int wi = 0;
            while (pos < content.length()) {
                int next = content.indexOf('\n', pos);
                String line;
                if (next == -1) {
                    line = content.substring(pos);
                    pos = content.length();
                } else {
                    line = content.substring(pos, next);
                    pos = next + 1;
                }
                line = line.trim();
                if (!line.isEmpty()) {
                    words[wi++] = line;
                }
            }
            return words;
        } catch (IOException e) {
            // En cas d'error d'E/S retornem array buit
            return new String[0];
        }
    }

    // Afegeix una paraula si no és buida i no existeix ja (comprovant duplicats amb l'array)
    public static boolean addWord(String word) {
        if (word == null) return false;
        String w = word.trim().toLowerCase();
        if (w.isEmpty()) return false;

        // Comprovació de duplicats
        String[] existing = listWords();
        for (int i = 0; i < existing.length; i++) {
            if (existing[i].equalsIgnoreCase(w)) return false;
        }

        File f = getFile();
        try {
            if (!f.exists()) f.createNewFile();
            byte[] toWrite = (w + System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
            try (FileOutputStream fos = new FileOutputStream(f, true)) {
                fos.write(toWrite);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Retorna una paraula aleatòria o null si no n'hi ha
    public static String getRandomWord() {
        String[] words = listWords();
        if (words.length == 0) return null;
        Random r = new Random();
        return words[r.nextInt(words.length)];
    }
}
