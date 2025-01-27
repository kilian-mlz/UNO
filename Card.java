package uno;

public class Card {
    private String symbol;
    private String color;

    //Constructeur
    public Card(String symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    //Renvois le symbole
    public String getSymbol() {
        return symbol;
    }

    //Vérifie si la carte est jouable en renoyant un boolean
    public boolean isPlayable(Card other) {
        return this.color.equals(other.color) || this.symbol.equals(other.symbol);
    }

    //Permet d'afficher directement l'objet sous forme de String
    public String toString() {
        return symbol + " " + color;
    }
}