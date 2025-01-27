package uno;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private String name;
    private ArrayList<Card> hand;

    //Constructeur
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    //Renvois le name
    public String getName() {
        return name;
    }

    //Ajoute une carte a la main du joueur
    public void addCard(Card card) {
        hand.add(card);
    }

    //Renvois le nombre de carte restante dans la main du joueur
    public int getCardCount() {
        return hand.size();
    }

    //Vérifie si le joueur a une carte jouable, si la carte choisie est jouable et renvois la carte jouée par l'utilisateur
    public Card playCard(Card topCard) {
        System.out.println("\033[34m" + "Voici votre main :\n" + this.hand + "\033[0m");
        boolean canPlay = false;

        for (Card check : hand) {
            if (check.isPlayable(topCard)) {
                canPlay = true;
                break;
            }
        }

        while (canPlay) {
            System.out.println("\033[32m" + "Veuillez choisir une carte en indiquant son emplacement (1 - 10) : " + "\033[0m");
            Scanner sc = new Scanner(System.in);

            int choice = sc.nextInt();
            Card card = hand.get(choice - 1);

            if (canPlay) {
                if (card.isPlayable(topCard)) {
                    hand.remove(card);
                    System.out.println("Vous avez placé un " + card);
                    return card;
                }
                System.out.println("Cette carte ne peut pas être posée..");
            }
        }
        System.out.println("\033[32m" + "Vous ne pouvez pas jouer" + "\033[0m");
        return null;
    }

    //Comme playCard mais en choisissant aléatoirement la carte jouée
    public Card playCardOrdi(Card topCard) {
        Random rand = new Random();

        boolean canPlay = false;

        for (Card check : hand) {
            if (check.isPlayable(topCard)) {
                canPlay = true;
                break;
            }
        }

        while (canPlay) {
            int choice = rand.nextInt(hand.size());
            Card card = hand.get(choice);
            if (card.isPlayable(topCard)) {
                hand.remove(card);
                System.out.println("L'ordinateur " + this.name + " à placé un " + card);
                return card;
            }
        }
        return null;
    }
}