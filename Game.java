package uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    private ArrayList<Card> deck;
    private ArrayList<Card> discardPile;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private int direction;

    public Game() {
        deck = new ArrayList<Card>();
        discardPile = new ArrayList<Card>();
        players = new ArrayList<Player>();
        currentPlayerIndex = 0;
        direction = 1;
    }

    //Fonction pour initialiser le jeu (création des joueurs, du paquet de carte et distribution des cartes)
    public void setupGame() {
        // Création d'un paquet de carte avec 4 doublons par cartes
        String[] colors = {"Rouge", "Bleu", "Vert", "Jaune"};
        String[] symbols = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
                "Changement de sens", "Saut de tour", "Double pioche"};

        for (int i = 0; i < 4; i++) {
            for (String color : colors) {
                for (String symbol : symbols) {
                    deck.add(new Card(symbol, color));
                }
            }
        }

        //Mélange du paquet
        Collections.shuffle(deck);

        // Création des joueurs
        Player human = new Player("Joueur");
        Player ordi1 = new Player("Ordinateur 1");
        Player ordi2 = new Player("Ordinateur 2");
        Player ordi3 = new Player("Ordinateur 3");
        players.add(human);
        players.add(ordi1);
        players.add(ordi2);
        players.add(ordi3);

        System.out.println("Voici les joueurs présents dans la partie :");
        for (Player player : players) {
            System.out.println(player.getName());
        }

        // Distribution des cartes
        for (int i = 0; i < 10; i++) {
            for (Player player : players) {
                player.addCard(deck.removeFirst());
            }
        }

        // Création de la pioche
        discardPile.add(deck.removeFirst());
    }

    //Fonction pour lancer un tour
    public boolean playTurn() {
        //Récuperer joueur actuel graçe a l'index dans la liste
        Player currentPlayer = players.get(currentPlayerIndex);

        Card topCard = discardPile.getLast();

        System.out.println("\033[31m" + "\nAu tour de " + currentPlayer.getName() + ". Carte sur la pioche : " + topCard + "\033[0m");

        //Lancer .playCard si le nom est "Joueur" ou .playCardOrdi sinon
        Card cardPlayed;
        if (currentPlayer.getName().equals("Joueur")) {
            cardPlayed = currentPlayer.playCard(topCard);
        } else {
            cardPlayed = currentPlayer.playCardOrdi(topCard);
        }

        //Ajoute la carte du joueur a la pile et fais l'action si c'est une carte action sinon fais piocher le joueur
        if (cardPlayed != null) {
            discardPile.add(cardPlayed);

            // Géstion des cartes spéciales
            Player nextPlayer = players.get((currentPlayerIndex + direction + players.size()) % players.size());
            if (cardPlayed.getSymbol().equals("Changement de sens")) {
                //Inverse la direction
                direction *= -1;
                System.out.println("Direction inversé !");
            } else if (cardPlayed.getSymbol().equals("Saut de tour")) {
                currentPlayerIndex = (currentPlayerIndex + direction + players.size()) % players.size();
                System.out.println("Tour sauté pour " + nextPlayer.getName() + " !");
            } else if (cardPlayed.getSymbol().equals("Double pioche")) {
                nextPlayer = players.get((currentPlayerIndex + direction + players.size()) % players.size());
                nextPlayer.addCard(deck.removeFirst());
                nextPlayer.addCard(deck.removeFirst());
                System.out.println(nextPlayer.getName() + " doit piocher deux cartes !");
            }
        } else {
            Card newCard = deck.removeFirst();
            currentPlayer.addCard(newCard);
            System.out.println(currentPlayer.getName() + " a dû piocher une carte.");
        }

        //Si le joueur n'a plus de cartes affiche qu'il a gagné et demande si il veut rejouer sinon affiche un message et mets fin au jeu
        if (currentPlayer.getCardCount() == 0) {
            System.out.println("\n" + currentPlayer.getName() + " a gagné !");
            Scanner scanner = new Scanner(System.in);
            String choice;
            System.out.println("Voulez vous recommencer une partie ? (yes/no) :");
            choice = scanner.nextLine().toLowerCase();
            if (choice.equals("yes")) {
                deck = new ArrayList<Card>();
                discardPile = new ArrayList<Card>();
                players = new ArrayList<Player>();
                currentPlayerIndex = 0;
                direction = 1;
                setupGame();
                return true;
            }
            System.out.println("Merci d'avoir jouer, au revoir !");
            return false;
        }

        //Modifie le "currentPlayerIndex" a chaque tour
        currentPlayerIndex = (currentPlayerIndex + direction + players.size()) % players.size();
        return true;

    }

    //Fonction pour lancer le jeu tant que playTurn ne renvoit pas false
    public void startGame() {
        setupGame();
        boolean gameFinnished = playTurn();
        while (gameFinnished) {
            gameFinnished = playTurn();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}