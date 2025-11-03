package Penjat.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 Config
 - Classe que encapsula la configuració persistida del joc.
 - Guarda/obre un fitxer binari amb les propietats bàsiques.
 - Format: writeUTF(version) + writeInt(maxIntents).
 - Ruta per defecte: "Penjat/Model/config.bin".
*/
public class Config {
    private static final String FILE = "Penjat/Model/config.bin";

    // Camps de configuració
    private String version;
    private int maxIntents;

    // Constructor
    public Config(String version, int maxIntents) {
        this.version = version;
        this.maxIntents = maxIntents;
    }

    // Getters / setters
    public String getVersion() { return version; }
    public int getMaxIntents() { return maxIntents; }
    public void setMaxIntents(int m) { this.maxIntents = m; }

    // Guarda la configuració a disc (binari). Crea directoris si cal.
    public void save() throws IOException {
        File f = new File(FILE);
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) dir.mkdirs();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(f))) {
            dos.writeUTF(version);
            dos.writeInt(maxIntents);
        }
    }

    // Carrega la configuració; si no existeix, crea una per defecte i la salva.
    public static Config load() throws IOException {
        File f = new File(FILE);
        if (!f.exists()) {
            Config c = new Config("1.0", 10);
            c.save();
            return c;
        }
        try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
            String v = dis.readUTF();
            int m = dis.readInt();
            return new Config(v, m);
        }
    }
}
