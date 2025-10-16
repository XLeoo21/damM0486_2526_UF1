package Penjat.Model;

import Penjat.View.PenjatView;

/*
 Classe que encapsula tota la lògica de la partida del penjat.
 Conté el bucle de joc, estat (paraula, masked, tried, wrongs) i totes les comprovacions.
 No fa prints directes; utilitza PenjatView per a la I/O.
*/
public class Game {

    private final int MAX_WRONGS = 6;
    private String word; // paraula actual
    private char[] paraulaOculta; // paraula amb lletres revelades
    private boolean[] tried; // lletres provades a..z
    private int wrongs;

    // Crea una nova partida amb una paraula aleatòria obtinguda de WordManager
    public Game() {
        this.word = WordManager.getRandomWord();
        if (this.word != null) {
            this.word = this.word.toLowerCase().trim();
            initState();
        }
    }

    // Inicialitza masked, tried i paraulaOculta segons la paraula actual
    private void initState() {
        int len = word.length();
        this.paraulaOculta = new char[len];
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            if (Character.isLetter(c))
                paraulaOculta[i] = '_';
            else
                paraulaOculta[i] = c;
        }
        this.tried = new boolean[26];
        this.wrongs = 0;
    }

    // Indica si hi ha paraula vàlida per jugar
    public boolean hasWord() {
        return this.word != null;
    }

    // Construeix i retorna la cadena formatejada a mostrar (p.ex. "t _ s")
    private String getMaskedAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paraulaOculta.length; i++) {
            sb.append(paraulaOculta[i]);
            if (i < paraulaOculta.length - 1)
                sb.append(' ');
        }
        return sb.toString();
    }

    // Comprova si totes les lletres han estat revelades
    private boolean isAllRevealed() {
        for (char c : paraulaOculta)
            if (c == '_')
                return false;
        return true;
    }

    // Processa un intent de l'usuari; retorna true si la partida ha acabat
    // (victòria o derrota)
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
            // l'usuari prova la paraula sencera
            if (guess.equalsIgnoreCase(word)) {
                // revelarem tota la paraula per coherència amb la View
                for (int i = 0; i < word.length(); i++)
                    paraulaOculta[i] = word.charAt(i);
                PenjatView.showMessage("Has encertat la paraula!");
                return true;
            } else {
                wrongs++;
                PenjatView.showMessage("Paraula incorrecta.");
            }
        }

        // comprovar condicions finals
        if (wrongs >= MAX_WRONGS) {
            PenjatView.showMessage("Has perdut. La paraula era: " + word);
            return true;
        }
        if (isAllRevealed()) {
            PenjatView.showMessage("Enhorabona! Has encertat la paraula: " + word);
            return true;
        }
        return false; // la partida continua
    }

    // Mètode que controla i realitza la partida: bucle que fa I/O via PenjatView
    public void play() {
        if (!hasWord()) {
            PenjatView.showMessage("No hi ha paraules al fitxer. Admin ha d'afegir paraules primer.");
            return;
        }
        while (true) {
            PenjatView.showMaskedWord(getMaskedAsString());
            PenjatView.showWrongCount(wrongs, MAX_WRONGS);
            String guess = PenjatView.askLetter();
            boolean finished = processGuess(guess);
            if (finished)
                break;
        }

    }

}
