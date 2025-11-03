import java.io.File;

public class Ex1 {
    public static void main(String[] args) {
        System.out.println("Tinc un paràmetre: ");

        String ruta = args[0];
        File path = new File(ruta);

        File[] files = path.listFiles();
        String resultat = "";

        for (File file : files) {
            if (file.isDirectory()) {
                resultat += "d";

                if (file.canRead()) {
                    resultat += "r";
                } else {
                    resultat += "-";
                }

                if (file.canWrite()) {
                    resultat += "w";
                } else {
                    resultat += "-";
                }

                if (file.canExecute()) {
                    resultat += "x";
                } else {
                    resultat += "-";
                }

                resultat += " " + file.getName() + "\n";

            } else {
                resultat += "-";

                if (file.canRead()) {
                    resultat += "r";
                } else {
                    resultat += "-";
                }

                if (file.canWrite()) {
                    resultat += "w";
                } else {
                    resultat += "-";
                }

                if (file.canExecute()) {
                    resultat += "x";
                } else {
                    resultat += "-";
                }

                resultat += " " + file.getName() + "\n";
            }
        }

        System.out.println(resultat);
    }
}
