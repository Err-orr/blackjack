import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String option = "";
        while (!option.equals("NO")) {
            Blackjack game = new Blackjack();
            game.beginGame();
            game.choose();
            System.out.println("Try again? (Type \"NO\" to quit. Type anything else to continue.)");
            option = scan.nextLine().toUpperCase();
            if (option.equals("NO")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("New Game Beginning...");
            }
        }
    }
}