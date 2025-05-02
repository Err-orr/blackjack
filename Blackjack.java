import java.util.*;

public class Blackjack {
    Map<String, Integer> values;
    public Deck deck;
    public boolean gameOver = false;
    public List<Card> yourCards;
    public int yourCardValues;
    public List<Card> dealerCards;
    public int dealerCardValues;

    public Blackjack() {
        deck = new Deck();
        values = new HashMap<>();
        values.put("TWO", 2);
        values.put("THREE", 3);
        values.put("FOUR", 4);
        values.put("FIVE", 5);
        values.put("SIX", 6);
        values.put("SEVEN", 7);
        values.put("EIGHT", 8);
        values.put("NINE", 9);
        values.put("TEN", 10);
        values.put("JACK", 10);
        values.put("QUEEN", 10);
        values.put("KING", 10);
        values.put("ACE", 11);

        // Initialize the lists
        yourCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
    }

    public void beginGame() {
        deck.shuffle();
        System.out.println("Deck Shuffled!");
        // Make sure to check if the deck has enough cards for drawing
        if (deck.size() >= 4) {
            yourCards.add(deck.drawCard());
            yourCards.add(deck.drawCard());
            yourCardValues = calculateHandValue(yourCards);
            System.out.println("Your cards: " + yourCards + " | Total value: " + yourCardValues);
            dealerCards.add(deck.drawCard());
            dealerCardValues = calculateHandValue(dealerCards);
            System.out.println("Dealer's cards: " + dealerCards + " ??? of ??? | Total value: " + dealerCardValues + "?");
            dealerCards.add(deck.drawCard());
            dealerCardValues = calculateHandValue(dealerCards);
            if (yourCardValues == 21) {
                System.out.println("YOU GOT 21!");
                stand();
            } else if (yourCardValues > 21) {
                // Adjust for Aces if necessary
                if (yourCardValues > 21) {
                    for (Card card : yourCards) {
                        // If the card is an Ace and the total is over 21, treat it as 1 instead of 11
                        if (card.getRank() == Rank.ACE && yourCardValues > 21) {
                            yourCardValues -= 10; // Change Ace value from 11 to 1
                            System.out.println("Ace adjusted to 1. Total Value: " + yourCardValues);
                        }
                    }
                }
            }
        } else {
            System.out.println("Not enough cards in the deck to begin the game.");
        }
    }

    // Method to calculate the total value of a hand
    private int calculateHandValue(List<Card> cards) {
        int totalValue = 0;
        for (Card card : cards) {
            totalValue += values.get(card.getRank().toString()); // Convert the rank to a string and get its value
        }
        return totalValue;
    }

    public void hit() {
        // Draw a card from the deck and add it to the player's hand
        yourCards.add(deck.drawCard());

        // Update the total value of the player's hand
        yourCardValues = calculateHandValue(yourCards);

        // Display the drawn card and the updated total value
        Card lastCard = yourCards.get(yourCards.size() - 1);  // Correct way to access the last card
        System.out.println("You drew a " + lastCard.getRank() + " of " + lastCard.getSuit() + " | Total Value: " + yourCardValues);
        System.out.println("Your cards: " + yourCards + " | Total value: " + yourCardValues);

        // If the total value exceeds 21, check for Aces and adjust them
        if (yourCardValues > 21) {
            // Adjust for Aces if necessary
            for (Card card : yourCards) {
                // If the card is an Ace and the total is over 21, treat it as 1 instead of 11
                if (card.getRank() == Rank.ACE && yourCardValues > 21) {
                    yourCardValues -= 10; // Change Ace value from 11 to 1
                    System.out.println("Ace adjusted to 1. Total Value: " + yourCardValues);
                }
            }

            // If after adjusting Aces the value is still over 21, the player loses
            if (yourCardValues > 21) {
                System.out.println("YOU LOSE! DEALER WINS");
                isGameOver();  // Assuming this method exists to end the game
            }
        } else if (yourCardValues == 21) {
            System.out.println("YOU GOT 21!");
            stand();
        }
    }

    public void choose() {
        Scanner scan = new Scanner(System.in);
        while (yourCardValues <= 21) {
            if (!gameOver) {
                System.out.println("Type \"HIT\" to hit, type \"STAND\" to stand. Typing anything else will make you hit.");
                String choice = scan.nextLine().toUpperCase();
                if (choice.equals("HIT")) {
                    hit();
                } else if (choice.equals("STAND")) {
                    stand();
                } else {
                    hit();
                }
            } else {
                break;
            }
        }
    }

    public void stand() {
        System.out.println("Dealer's cards: " + dealerCards + " | Total value: " + dealerCardValues);
        while (dealerCardValues <= 16) {
            dealerCards.add(deck.drawCard());
            dealerCardValues = calculateHandValue(dealerCards);
            System.out.println("Dealer's cards: " + dealerCards + " | Total value: " + dealerCardValues);
            if (dealerCardValues > 21) {
                System.out.println("DEALER LOSES! YOU WIN");
                isGameOver();
                break;
            } else if (dealerCardValues == 21) {
                if (yourCardValues == 21) {
                    System.out.println("TIE!");
                    break;
                } else {
                    System.out.println("THE DEALER HAS 21! YOU LOSE");
                    isGameOver();
                    break;
                }
            }
        }
        if (!gameOver) {
            if (dealerCardValues < 21) {
                standOff();
            } else {
                isGameOver();
                System.out.println("DEALER LOSES! YOU WIN");
            }
        }
    }

    public void standOff() {
        if (!gameOver) {
            if (yourCardValues > dealerCardValues) {
                System.out.println("YOU WIN! DEALER LOSES");
                isGameOver();
            } else if (dealerCardValues > yourCardValues) {
                System.out.println("YOU LOSE! DEALER WINS");
                isGameOver();
            } else {
                System.out.println("TIE!");
                isGameOver();
            }
        }
    }

    public void isGameOver() {
        gameOver = true;
    }
}