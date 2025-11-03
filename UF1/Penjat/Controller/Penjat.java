package Penjat.Controller;

import java.io.File;
import java.io.IOException;

import Penjat.Model.Config;
import Penjat.Model.Game;
import Penjat.Model.User;
import Penjat.Model.WordManager;
import Penjat.View.PenjatView;

/*
 Penjat (Controlador)
 - Punt d'entrada del programa (mètode main).
 - Coordina la càrrega de Config, la creació de l'admin si cal,
   el bucle principal de menú, sessions d'usuari i la lògica de crida a Game.
 - Gestiona també la visualització del rànquing quan toca.
*/
public class Penjat {

    public static void main(String[] args) throws IOException {

        // Carregar configuració (crea fitxer per defecte si no existeix)
        Config cfg = Config.load();

        // Crear l'usuari admin per defecte si no existeix el fitxer
        File adminFile = new File("Penjat/Model/Users", "admin.usr");
        if (!adminFile.exists()) {
            User adm = new User("Admin", "admin", "admin", true);
            try {
                User.register(adm);
            } catch (IOException e) {
                System.err.println("Error en registrar l'usuari administrador: " + e.getMessage());
            }
        }

        int op = 0;
        boolean rankingShown = false; // flag per evitar duplicar el rànquing

        // Bucle principal del menú
        do {
            PenjatView.menuInicial();
            op = PenjatView.askOption();

            switch (op) {
                case 1: { // Login
                    String[] logData = PenjatView.askLogin();
                    boolean logOk = User.login(logData[0], logData[1]);
                    PenjatView.logOk(logOk);

                    if (logOk) {
                        // Carregar l'usuari després del login per tenir l'estat persistent
                        User u = User.load(logData[0]);
                        if (u == null) {
                            PenjatView.showMessage("Error carregant l'usuari després del login.");
                            break;
                        }
                        boolean isAdmin = u.isAdmin();
                        boolean exitSession = false;

                        // Mostrar benvinguda amb punts actuals
                        PenjatView.showWelcome(u.getName(), u.getPunts());

                        // Bucle de sessió: el menú varia si l'usuari és admin
                        do {
                            PenjatView.menuJugar(isAdmin);
                            int opJoc = PenjatView.askOption();

                            if (opJoc == 1) {
                                // Jugar: creem una partida i recollim delta de punts
                                Game g = new Game(u, cfg);
                                int delta = g.play();

                                // Si hi ha canvi, l'apliquem i guardem l'usuari
                                if (delta != 0) {
                                    u.addPoints(delta);
                                    User.save(u);
                                    PenjatView.showMessage("Has obtingut " + (delta > 0 ? "+" + delta : delta) + " punts. Total: " + u.getPunts());
                                } else {
                                    PenjatView.showMessage("Cap canvi en puntuació. Total: " + u.getPunts());
                                }

                            } else if (isAdmin && opJoc == 2) {
                                // Editar paraules: llista, editar per index o afegir amb 'N'
                                String[] words;
                                try {
                                    words = WordManager.listWords();
                                } catch (IOException e) {
                                    words = new String[0];
                                }
                                PenjatView.showWords(words);

                                String sel = PenjatView.askEditSelection();
                                if (sel.equalsIgnoreCase("N")) {
                                    String newWord = PenjatView.askNewWord();
                                    if (!newWord.isBlank()) {
                                        int score = PenjatView.askInt("Puntuació per a la paraula: ");
                                        try {
                                            WordManager.addWord(newWord, score);
                                            PenjatView.showMessage("Paraula afegida correctament.");
                                        } catch (IOException e) {
                                            PenjatView.showMessage("No s'ha pogut afegir (error d'E/S).");
                                        }
                                    } else {
                                        PenjatView.showMessage("Afegir cancel·lat.");
                                    }
                                } else {
                                    try {
                                        int idx = Integer.parseInt(sel) - 1;
                                        WordManager.WordEntry entry = WordManager.readAtIndex(idx);
                                        if (entry == null) {
                                            PenjatView.showMessage("Índex invàlid.");
                                        } else {
                                            System.out.println("Editant: " + entry.word + " (" + entry.score + "p)");
                                            String newW = PenjatView.askNewWord();
                                            if (newW.isBlank()) newW = entry.word;
                                            int newScore = PenjatView.askInt("Nova puntuació (enter): ");
                                            boolean ok = WordManager.editWordAtIndex(idx, newW, newScore);
                                            if (ok) PenjatView.showMessage("Entrada actualitzada.");
                                            else PenjatView.showMessage("No s'ha pogut actualitzar.");
                                        }
                                    } catch (NumberFormatException nfe) {
                                        PenjatView.showMessage("Selecció no vàlida.");
                                    } catch (IOException ioe) {
                                        PenjatView.showMessage("Error d'E/S editant la paraula.");
                                    }
                                }

                            } else if (isAdmin && opJoc == 3) {
                                // Editar configuració: només admin pot canviar maxIntents
                                int nouMax = PenjatView.askInt("Introdueix el nou màxim d'intents: ");
                                cfg.setMaxIntents(nouMax);
                                try {
                                    cfg.save();
                                    PenjatView.showMessage("Configuració guardada.");
                                } catch (IOException e) {
                                    PenjatView.showMessage("Error guardant configuració.");
                                }

                            } else {
                                // Gestionar sortida de sessió segons tipus d'usuari
                                if (isAdmin) {
                                    if (opJoc == 4) {
                                        // Mostrar rànquing quan es surt de la sessió i marcar per evitar duplicats
                                        User.listAll();
                                        rankingShown = true;
                                        exitSession = true;
                                    } else {
                                        PenjatView.showMessage("Opció no vàlida.");
                                    }
                                } else {
                                    if (opJoc == 2) {
                                        User.listAll();
                                        rankingShown = true;
                                        exitSession = true;
                                    } else {
                                        PenjatView.showMessage("Opció no vàlida.");
                                    }
                                }
                            }
                        } while (!exitSession);

                    } else {
                        // Si el login fallà, pregunta si vol registrar-se
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
                }

                case 2: { // Register (des del menú principal)
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
                }

                default:
                    if (op != 3) {
                        PenjatView.showMessage("Opció no vàlida.");
                    }
                    break;
            }

        } while (op != 3);

        // Quan es surt del programa des del menú principal, mostrem el rànquing si no s'ha mostrat ja
        if (!rankingShown) {
            User.listAll();
        }
    }
}
