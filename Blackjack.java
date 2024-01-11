import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String toString() {
        return rank + " of " + suit;
    }

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

class Deck {
    private List<Card> cards;

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

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null; // Deck is empty
        }
        return cards.remove(0);
    }
}

class Player {
    private List<Card> hand;
    private int money;
    private int bet;

    public Player() {
        hand = new ArrayList<>();
        money = 10000;
        bet = 0;
    }

    public void placeBet(int amount) {
        bet = Math.min(amount, money);
    }

    public void winBet() {
        money += bet;
        bet = 0;
    }

    public void loseBet() {
        money -= bet;
        bet = 0;
    }

    public void clearHand() {
        hand.clear();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getMoney() {
        return money;
    }

    public int getBet() {
        return bet;
    }
}

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

    private void handleHit() {
        Card card = deck.drawCard();
        if (card != null) {
            player.addCard(card);
            updateLabels();
            if (calculateHandValue(player) > 21) {
                playerLose();
            }
        }
    }

    private void handleStand() {
        while (calculateHandValue(dealer) < 17) {
            Card card = deck.drawCard();
            if (card != null) {
                dealer.addCard(card);
            }
        }

        updateLabels();

        int playerValue = calculateHandValue(player);
        int dealerValue = calculateHandValue(dealer);

        if (dealerValue > 21 || playerValue > dealerValue) {
            playerWin();
        } else if (playerValue < dealerValue) {
            playerLose();
        } else {
            playerDraw();
        }
    }

    private void handleBet() {
        try {
            int betAmount = Integer.parseInt(betTextField.getText());
            if (betAmount > 0 && betAmount <= player.getMoney()) {
                player.placeBet(betAmount);
                dealInitialCards();
                updateLabels();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid bet amount!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid bet amount!");
        }
    }

    private void handleRestart() {
        deck = new Deck();
        player = new Player();
        dealer = new Player();
        updateLabels();
    }

    private void dealInitialCards() {
        player.clearHand();
        dealer.clearHand();

        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
    }

    private void updateLabels() {
        playerTextArea.setText("Player Hand: \n" + getFormattedHand(player) + "\nValue: " + calculateHandValue(player));
        dealerTextArea.setText("Dealer Hand: \n" + getFormattedHand(dealer) + "\nValue: " + calculateHandValue(dealer));
        moneyLabel.setText("Money: $" + player.getMoney() + " (Bet: $" + player.getBet() + ")");
        
        playerTextArea.setFont(new Font("DialogI", Font.BOLD, 12));
        dealerTextArea.setFont(new Font("Courier", Font.BOLD, 12)); 
        moneyLabel.setFont(new Font("Times Roman", Font.BOLD, 13));
    }

    private String getFormattedHand(Player player) {
        StringBuilder handString = new StringBuilder();
        List<Card> hand = player.getHand();

        for (Card card : hand) {
            handString.append(card.toString()).append("\n");
        }

        return handString.toString();
    }

    private int calculateHandValue(Player player) {
        int value = 0;
        int numAces = 0;

        for (Card card : player.getHand()) {
            if (card != null) {
                value += card.getValue();
                if (card.getValue() == 11) {
                    numAces++;
                }
            }
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }

    private void playerWin() {
        player.winBet();
        updateLabels();
        JOptionPane.showMessageDialog(this, "You win!");
    }

    private void playerLose() {
        player.loseBet();
        updateLabels();
        JOptionPane.showMessageDialog(this, "You lose!");
    }

    private void playerDraw() {
        player.clearHand();
        dealer.clearHand();
        updateLabels();
        JOptionPane.showMessageDialog(this, "It's a draw!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Blackjack());
    }
}
