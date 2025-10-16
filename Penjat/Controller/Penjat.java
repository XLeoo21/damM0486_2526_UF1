package Penjat.Controller;

import java.io.File;
import java.io.IOException;

import Penjat.Model.Game;
import Penjat.Model.User;
import Penjat.Model.WordManager;
import Penjat.View.PenjatView;

public class Penjat {

    public static void main(String[] args) throws IOException {

        File adminFile = new File("Penjat/Model/Users", "admin.usr");
        if (!adminFile.exists()) {
            User adm = new User("Admin", "admin", "admin123", true);
            try {
                User.register(adm);
            } catch (IOException e) {
                System.err.println("Error en registrar l'usuari administrador: " + e.getMessage());
            }
        }

        int op = 0;
        int opJoc = 0;

        do {
            PenjatView.menuInicial();
            op = PenjatView.askOption();

            switch (op) {
                case 1:
                    String[] logData = PenjatView.askLogin();
                    boolean logOk = User.login(logData[0], logData[1]);
                    PenjatView.logOk(logOk);

                    if (logOk) {
                        boolean isAdmin = "admin".equalsIgnoreCase(logData[0]);
                        boolean exitSession = false;

                        do {
                            PenjatView.menuJugar(isAdmin);
                            opJoc = PenjatView.askOption();

                            if (opJoc == 1) {
                                // Cridem al Model per crear i jugar la partida
                                Game g = new Game();
                                g.play();
                            } else if (isAdmin && opJoc == 2) {
                                String[] words = WordManager.listWords();
                                PenjatView.showWords(words);

                                String newWord = PenjatView.askNewWord();
                                if (!newWord.isBlank()) {
                                    boolean added = WordManager.addWord(newWord);

                                    if (added) {
                                        PenjatView.showMessage("Paraula afegida correctament.");
                                    } else {
                                        PenjatView.showMessage("No s'ha pogut afegir (duplicat o error).");
                                    }
                                } else {
                                    PenjatView.showMessage("Afegir cancel·lat.");
                                }

                            } else {
                                if (isAdmin) {
                                    if (opJoc == 3) {
                                        exitSession = true;
                                    } else {
                                        PenjatView.showMessage("Opció no vàlida.");
                                    }
                                } else {
                                    if (opJoc == 2) {
                                        exitSession = true;
                                    } else {
                                        PenjatView.showMessage("Opció no vàlida.");
                                    }
                                }
                            }
                        } while (!exitSession);

                        op = 3; // sortir després de la sessió
                    } else {
                        String resposta = PenjatView.askYesNo();

                        if (resposta.equalsIgnoreCase("S")) {
                            String name = PenjatView.askName();
                            User user = new User(name, logData[0], logData[1], false);

                            try {
                                boolean regOk = User.register(user);
                                PenjatView.regOk(regOk);
                            } catch (IOException e) {
                                PenjatView.regOk(false);
                                System.err.println("Error en registrar l'usuari: " + e.getMessage());
                            }
                        }
                    }
                    break;

                case 2:
                    String[] regData = PenjatView.askRegister();
                    User newUser = new User(regData[0], regData[1], regData[2], false);

                    try {
                        boolean regOk = User.register(newUser);
                        PenjatView.regOk(regOk);
                    } catch (IOException e) {
                        PenjatView.regOk(false);
                        System.err.println("Error en registrar l'usuari: " + e.getMessage());
                    }
                    break;

                default:
                    if (op != 3) {
                        PenjatView.showMessage("Opció no vàlida.");
                    }
                    break;
            }

        } while (op != 3);
    }
}
