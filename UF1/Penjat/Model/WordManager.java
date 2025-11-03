package Penjat.Model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 WordManager
 - Gestiona el fitxer de paraules amb puntuació en accés aleatori.
 - Format de registre: [int len][bytes UTF-8 paraula][int score]
 - Per eficiència, usa RandomAccessFile per llegir i editar sense carregar tot el fitxer.
 - Si una edició canvia la longitud, es reconstrueix el fitxer (rebuild).
*/
public class WordManager {

    private static final String FILE = "Penjat/Model/paraules.dat";
    // Cada registre: [int len][bytes palabra UTF-8][int score]

    // Obre un RandomAccessFile garantint la creació del directori i fitxer si cal
    private static RandomAccessFile open(String mode) throws IOException {
        File f = new File(FILE);
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) dir.mkdirs();
        if (!f.exists()) f.createNewFile();
        return new RandomAccessFile(f, mode);
    }

    // Afegeix una nova paraula al final del fitxer
    public static void addWord(String word, int score) throws IOException {
        try (RandomAccessFile raf = open("rw")) {
            raf.seek(raf.length());
            byte[] data = word.getBytes(StandardCharsets.UTF_8);
            raf.writeInt(data.length);
            raf.write(data);
            raf.writeInt(score);
        }
    }

    // Comptar entrades sense carregar tot el fitxer
    public static int countWords() throws IOException {
        try (RandomAccessFile raf = open("r")) {
            int count = 0;
            while (raf.getFilePointer() < raf.length()) {
                int len = raf.readInt();
                raf.skipBytes(len);
                raf.readInt(); // score
                count++;
            }
            return count;
        }
    }

    // Retorna un array de strings per mostrar a la vista (paraula + (score)p)
    public static String[] listWords() throws IOException {
        try (RandomAccessFile raf = open("r")) {
            List<String> list = new ArrayList<>();
            while (raf.getFilePointer() < raf.length()) {
                int len = raf.readInt();
                byte[] data = new byte[len];
                raf.readFully(data);
                String w = new String(data, StandardCharsets.UTF_8);
                int score = raf.readInt();
                list.add(w + " (" + score + "p)");
            }
            return list.toArray(new String[0]);
        }
    }

    // Llegeix l'entrada en l'índex donat i retorna metadades útils per editar
    public static WordEntry readAtIndex(int index) throws IOException {
        try (RandomAccessFile raf = open("r")) {
            int i = 0;
            while (raf.getFilePointer() < raf.length()) {
                long recordStart = raf.getFilePointer(); // posició on comença el registre (int len)
                int len = raf.readInt();
                byte[] data = new byte[len];
                raf.readFully(data);
                String w = new String(data, StandardCharsets.UTF_8);
                int score = raf.readInt();
                if (i == index) {
                    return new WordEntry(w, score, recordStart, len);
                }
                i++;
            }
        }
        return null;
    }

    // Retorna una entrada aleatòria per jugar
    public static WordEntry getRandomEntry() throws IOException {
        int total = countWords();
        if (total == 0) return null;
        int idx = new Random().nextInt(total);
        return readAtIndex(idx);
    }

    // Edita una entrada a l'índex; intenta in-place si la longitud no canvia,
    // si canvia, reconstrueix tot el fitxer.
    public static boolean editWordAtIndex(int index, String newWord, int newScore) throws IOException {
        WordEntry old = readAtIndex(index);
        if (old == null) return false;
        byte[] newData = newWord.getBytes(StandardCharsets.UTF_8);

        // Si la longitud és la mateixa, sobreescrivim en la posició exacte
        if (newData.length == old.lengthBytes) {
            try (RandomAccessFile raf = open("rw")) {
                raf.seek(old.recordStart);
                raf.writeInt(newData.length);
                raf.write(newData);
                raf.writeInt(newScore);
                return true;
            }
        }

        // Si la longitud canvia, llegim totes les entrades i reescrivim el fitxer
        List<WordEntry> entries = new ArrayList<>();
        try (RandomAccessFile raf = open("r")) {
            while (raf.getFilePointer() < raf.length()) {
                int len = raf.readInt();
                byte[] data = new byte[len];
                raf.readFully(data);
                String w = new String(data, StandardCharsets.UTF_8);
                int score = raf.readInt();
                entries.add(new WordEntry(w, score, 0, len));
            }
        }
        // Substituïm l'entrada seleccionada
        entries.set(index, new WordEntry(newWord, newScore, 0, newData.length));

        // Reescrivim tot el fitxer
        try (RandomAccessFile raf = open("rw")) {
            raf.setLength(0);
            for (WordEntry e : entries) {
                byte[] data = e.word.getBytes(StandardCharsets.UTF_8);
                raf.writeInt(data.length);
                raf.write(data);
                raf.writeInt(e.score);
            }
        }
        return true;
    }

    // Classe interna per retornar metadades d'una entrada
    public static class WordEntry {
        public final String word;
        public final int score;
        public final long recordStart; // posició del int len del registre
        public final int lengthBytes;  // longitud en bytes de la paraula UTF-8

        public WordEntry(String word, int score, long recordStart, int lengthBytes) {
            this.word = word;
            this.score = score;
            this.recordStart = recordStart;
            this.lengthBytes = lengthBytes;
        }
    }
}
