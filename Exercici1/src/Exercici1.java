import java.io.File;

public class Exercici1{
    public static void main(String[] args){
        System.out.println("Tinc un paràmetre: ");

        String ruta = args[0];
        File path = new File(ruta);

        File[] files = path.listFiles();

        for(File file: files){
            System.out.println(file.getName());
        }
    }
}
