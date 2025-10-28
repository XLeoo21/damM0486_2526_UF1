package Penjat.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 User
 - Representa un usuari amb nom, username, password, flag d'admin i punts.
 - Serialitza l'objecte a fitxers .usr dins "Penjat/Model/Users".
 - Normalitza els usernames a lower-case i sense espais a l'inici/final.
 - Proporciona registres, login, load, save i listAll per al rànquing.
*/
public class User implements Serializable {

    // UID explícit per consistència entre versions si cal
    private static final long serialVersionUID = 1L;

    // Camps de l'usuari
    private String name;      // Nom visible
    private String user;      // Username normalitzat
    private String password;  // Contrasenya (text pla per la pràctica)
    private boolean admin;    // Flag d'administrador
    private int punts;        // Punts acumulats

    // Constructor principal (permèt crear admin si cal)
    public User(String name, String user, String password, boolean admin) {
        this.name = name;
        this.user = normalizeUser(user);
        this.password = password;
        this.admin = admin;
        this.punts = 0;
    }

    // Constructor senzill (no admin)
    public User(String name, String user, String password) {
        this(name, user, password, false);
    }

    // Getters / utilitats
    public String getName() { return name; }
    public String getUser() { return user; }
    public int getPunts() { return punts; }
    public boolean isAdmin() { return admin; }
    public void addPoints(int p) { this.punts += p; }

    // Normalitza l'username: trim + toLowerCase per evitar duplicats
    private static String normalizeUser(String u) {
        if (u == null) return null;
        return u.trim().toLowerCase();
    }

    // Retorna el directori on es guarden els fitxers .usr; crea si cal
    private static File getUsersDir() {
        File dir = new File("Penjat/Model/Users");
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    // Retorna el fitxer associat a un username normalitzat
    private static File getFile(String user) {
        String n = normalizeUser(user);
        File dir = getUsersDir();
        return new File(dir, n + ".usr");
    }

    // Registre: crea un nou fitxer .usr amb l'objecte serialitzat
    // Retorna false si l'usuari ja existeix
    public static boolean register(User user) throws IOException {
        if (user == null || user.getUser() == null) return false;
        File f = getFile(user.getUser());
        if (f.exists()) {
            System.err.println("register: l'usuari ja existeix -> " + f.getAbsolutePath());
            return false;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(user);
            oos.flush();
            System.out.println("register: creat " + f.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("register: error escrivint " + f.getAbsolutePath() + " -> " + e.getMessage());
            throw e;
        }
    }

    // Login: llegeix el fitxer i compara la contrasenya
    // Retorna true si coincideix
    public static boolean login(String user, String password) {
        if (user == null || password == null) return false;
        File f = getFile(user);
        if (!f.exists()) {
            System.err.println("login: fitxer d'usuari no trobat -> " + f.getAbsolutePath());
            return false;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            User u = (User) ois.readObject();
            if (u.password == null) return false;
            boolean ok = u.password.equals(password);
            if (!ok) System.err.println("login: password incorrecta per " + f.getName());
            return ok;
        } catch (Exception e) {
            System.err.println("login: error llegint " + f.getAbsolutePath() + " -> " + e.getMessage());
            return false;
        }
    }

    // Carrega i retorna l'objecte User des del fitxer; null si error
    public static User load(String user) {
        if (user == null) return null;
        File f = getFile(user);
        if (!f.exists()) {
            System.err.println("load: fitxer no trobat -> " + f.getAbsolutePath());
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (User) ois.readObject();
        } catch (Exception e) {
            System.err.println("load: error llegint " + f.getAbsolutePath() + " -> " + e.getMessage());
            return null;
        }
    }

    // Guarda l'objecte User actual a disc (sobreescriu). Mostra missatge de confirmació per debug.
    public static void save(User u) {
        if (u == null || u.getUser() == null) return;
        File f = getFile(u.getUser());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(u);
            oos.flush();
            System.out.println("save: guardat " + f.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("save: error guardant " + f.getAbsolutePath() + " -> " + e.getMessage());
        }
    }

    // Llista tots els usuaris i mostra el rànquing ordenat per punts descendent
    public static void listAll() {
        File dir = getUsersDir();
        File[] files = dir.listFiles((d, n) -> n.endsWith(".usr"));
        System.out.println("\n=== Rànquing de usuaris ===");
        if (files == null || files.length == 0) {
            System.out.println("[Cap usuari]");
        } else {
            List<User> users = new ArrayList<>();
            for (File f : files) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                    User u = (User) ois.readObject();
                    users.add(u);
                } catch (Exception e) {
                    System.err.println("listAll: error llegint " + f.getName() + " -> " + e.getMessage());
                }
            }
            // Ordenar per punts desc; si empaten, per nom
            users.sort((a, b) -> {
                int cmp = Integer.compare(b.punts, a.punts);
                if (cmp != 0) return cmp;
                return a.name.compareToIgnoreCase(b.name);
            });
            for (User u : users) {
                System.out.println(u.name + " --> " + u.punts + " punts");
            }
        }
        System.out.println();
    }
}
