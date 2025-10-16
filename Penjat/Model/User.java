package Penjat.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable{
    
    private String name; 
    private String user; 
    private String password;
    private boolean admin;
    private int punts;
    
    public User(String name, String user, String password, boolean admin) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.admin = admin;
        this.punts = 0;
    }

    public User(String name, String user, String password) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.admin = false;
        this.punts = 0;
    }
    
    // Registra l'usuari serialitzant l'objecte a Penjat/Model/Users/<user>.usr
    public static boolean register(User user) throws IOException {
        File f = new File("Penjat/Model/Users", user.user + ".usr");
        if(f.exists()){
            return false;
        } else {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, true));
            oos.writeObject(user);
            oos.close();
            return true;
        }
    }

    // Fa login llegint Penjat/Model/Users/<user>.usr i comparant contrasenya
    public static boolean login(String user, String password) {
        File f = new File("Penjat/Model/Users",user + ".usr");
        if(!f.exists() ){
            return false;
        } else {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                User u = (User) ois.readObject();
                ois.close();
                if(u.password.equals(password)){
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                return false;
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

    }

    
}
