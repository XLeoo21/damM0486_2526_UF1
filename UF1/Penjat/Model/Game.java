package Penjat.Model;

import Penjat.View.PenjatView;

/*
 Game
 - Gestiona una partida individual del penjat.
 - No persisteix l'usuari directament; retorna el delta de punts perquè el controlador el guardi.
 - Lògica:
   - Agafa una paraula aleatòria de WordManager.
   - Manté estat local: paraula oculta, lletres provades, wrongs.
   - processGuess: processa una lletra o intent de paraula sencera; no fa saves.
   - play: bucle principal; retorna +scoreWord si ha guanyat, -5 si ha perdut, 0 si sense canvi.
*/
public class Game {

    private String word;            // paraula actual
    private char[] paraulaOculta;   // estat de la paraula mostrada
    private boolean[] tried;        // lletres provades (a..z)
    private int wrongs;             // errors actuals
    private int maxWrongs;          // màxim d'errors (de Config)
    private int scoreWord;          // puntuació assignada a la paraula
    private User currentUser;       // referència local a l'usuari (no s'usa per salvar)

    // Constructor: rep l'usuari i la config per establir maxWrongs
    public Game(User u, Config cfg) {
        this.currentUser = u;
        WordManager.WordEntry entry;
        try {
            entry = WordManager.getRandomEntry();
        } catch (Exception e) {
            entry = null;
        }
        if (entry == null) {
            this.word = null;
            return;
        }
        this.word = entry.word.toLowerCase().trim();
        this.scoreWord = entry.score;
        this.maxWrongs = cfg.getMaxIntents();
        initState();
    }

    // Inicialitza l'estat de la partida (paraula oculta, array de tried, wrongs)
    private void initState() {
        int len = word.length();
        this.paraulaOculta = new char[len];
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            if (Character.isLetter(c))
                paraulaOculta[i] = '_';
            else
                paraulaOculta[i] = c; // caràcters no alfabètics es mostren directament
        }
        this.tried = new boolean[26];
        this.wrongs = 0;
    }

    // Indica si hi ha paraula vàlida per jugar
    public boolean hasWord() {
        return this.word != null;
    }

    // Retorna la paraula enmascarada com a String per la vista (p.ex. "t _ s")
    private String getMaskedAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paraulaOculta.length; i++) {
            sb.append(paraulaOculta[i]);
            if (i < paraulaOculta.length - 1)
                sb.append(' ');
        }
        return sb.toString();
    }

    // Comprova si totes les lletres estan revelades
    private boolean isAllRevealed() {
        for (char c : paraulaOculta)
            if (c == '_')
                return false;
        return true;
    }

    // Processa un intent (lletra o paraula); retorna true si la partida ha finalitzat
    // IMPORTANT: no modifica ni guarda l'usuari persistent; només actualitza l'estat local
    public boolean processGuess(String guess) {
        if (guess == null || guess.isBlank()) {
            PenjatView.showMessage("Entrada buida, intenta-ho de nou.");
            return false;
        }

        if (guess.length() == 1) {
            char ch = guess.charAt(0);
            if (!Character.isLetter(ch)) {
                PenjatView.showMessage("Introdueix una lletra vàlida.");
                return false;
            }
            int idx = Character.toLowerCase(ch) - 'a';
            if (idx < 0 || idx >= 26) {
                PenjatView.showMessage("Lletra fora de l'alfabet a-z.");
                return false;
            }
            if (tried[idx]) {
                PenjatView.showMessage("Ja has provat aquesta lletra.");
                return false;
            }
            tried[idx] = true;
            boolean found = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == ch) {
                    paraulaOculta[i] = ch;
                    found = true;
                }
            }
            if (!found) {
                wrongs++;
                PenjatView.showMessage("Lletra incorrecta.");
            } else {
                PenjatView.showMessage("Lletra correcta!");
            }
        } else {
            // Intent de paraula sencera
            if (guess.equalsIgnoreCase(word)) {
                for (int i = 0; i < word.length(); i++)
                    paraulaOculta[i] = word.charAt(i);
                PenjatView.showMessage("Has encertat la paraula!");
                return true;
            } else {
                wrongs++;
                PenjatView.showMessage("Paraula incorrecta.");
            }
        }

        // Comprovacions finals de derrota/victòria
        if (wrongs >= maxWrongs) {
            PenjatView.showMessage("Has perdut. La paraula era: " + word);
            return true;
        }
        if (isAllRevealed()) {
            PenjatView.showMessage("Enhorabona! Has encertat la paraula: " + word);
            return true;
        }
        return false;
    }

    // Play: bucle principal del joc; retorna delta de punts: +scoreWord, -5 o 0
    public int play() {
        if (!hasWord()) {
            PenjatView.showMessage("No hi ha paraules al fitxer. L'admin ha d'afegir paraules primer.");
            return 0;
        }

        while (true) {
            PenjatView.showMaskedWord(getMaskedAsString());
            PenjatView.showWrongCount(wrongs, maxWrongs);
            String guess = PenjatView.askLetter();
            boolean finished = processGuess(guess);
            if (finished)
                break;
        }

        if (isAllRevealed()) {
            return scoreWord; // victòria: sumar la puntuació de la paraula
        }
        if (wrongs >= maxWrongs) {
            return -5; // derrota: penalització fixada (-5)
        }
        return 0; // cap canvi (cas poc probable)
    }
}
