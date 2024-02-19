import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a playing card with a suit and rank.
 */
class Card {
    private String suit;
    private String rank;

    /**
     * Constructs a Card with the specified suit and rank.
     *
     * @param suit The suit of the card.
     * @param rank The rank of the card.
     */
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return A string representing the card, e.g., "Ace of Hearts".
     */
    public String toString() {
        return rank + " of " + suit;
    }

    /**
     * Returns the numerical value of the card for the game of Blackjack.
     *
     * @return The value of the card.
     */
    public int getValue() {
        switch (rank) {
            case "Ace":
                return 11;
            case "King":
            case "Queen":
            case "Jack":
                return 10;
            default:
                return Integer.parseInt(rank);
        }
    }
}

/**
 * Represents a deck of playing cards for the game of Blackjack.
 */
class Deck {
    private List<Card> cards;

    /**
     * Constructs a shuffled deck of cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }

        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the deck.
     *
     * @return The drawn card or null if the deck is empty.
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null; // Deck is empty
        }
        return cards.remove(0);
    }
}

/**
 * Represents a player in the game of Blackjack.
 */
class Player {
    private List<Card> hand;
    private int money;
    private int bet;

    /**
     * Constructs a player with an initial amount of money and zero bet.
     */
    public Player() {
        hand = new ArrayList<>();
        money = 10000;
        bet = 0;
    }

    /**
     * Places a bet for the player.
     *
     * @param amount The amount to bet.
     */
    public void placeBet(int amount) {
        bet = Math.min(amount, money);
    }

    /**
     * Wins the current bet for the player.
     */
    public void winBet() {
        money += bet;
        bet = 0;
    }

    /**
     * Loses the current bet for the player.
     */
    public void loseBet() {
        money -= bet;
        bet = 0;
    }

    /**
     * Clears the player's hand.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to add.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Gets the player's hand.
     *
     * @return The player's hand.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Gets the player's current money.
     *
     * @return The player's money.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Gets the player's current bet.
     *
     * @return The player's bet.
     */
    public int getBet() {
        return bet;
    }
}

/**
 * Represents a simple Blackjack game using a graphical user interface.
 */
public class Blackjack extends JFrame {
    private Deck deck;
    private Player player;
    private Player dealer;
    private JButton hitButton;
    private JButton standButton;
    private JButton betButton;
    private JButton restartButton;
    private JTextField betTextField;
    private JLabel playerLabel;
    private JLabel dealerLabel;
    private JLabel moneyLabel;
    private JTextArea playerTextArea;
    private JTextArea dealerTextArea;

    /**
     * Constructs a Blackjack game with GUI components.
     */
    public Blackjack() {
        setTitle("Blackjack");
        setLayout(new FlowLayout());

        deck = new Deck();
        player = new Player();
        dealer = new Player();

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        betButton = new JButton("Place Bet");
        restartButton = new JButton("Restart");
        betTextField = new JTextField(10);
        playerLabel = new JLabel();
        dealerLabel = new JLabel();
        moneyLabel = new JLabel("Money: $" + player.getMoney());
        playerTextArea = new JTextArea(4, 30); // 4 rows, 30 columns
        dealerTextArea = new JTextArea(4, 30); // 4 rows, 30 columns

        add(hitButton);
        add(standButton);
        add(betTextField);
        add(betButton);
        add(restartButton);
        add(playerLabel);
        add(dealerLabel);
        add(moneyLabel);
        add(playerTextArea);
        add(dealerTextArea);

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleHit();
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStand();
            }
        });

        betButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBet();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRestart();
            }
        });

        updateLabels();
        setSize(380, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ... (methods omitted for brevity)

    /**
     * The main method to start the Blackjack game.
     *
     * @param args The command line arguments (ignored).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Blackjack());
    }
}

