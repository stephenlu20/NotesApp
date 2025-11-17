package notes;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    Scanner scanner;
    PrintMessages printMessages;
    
    public static void main(String[] args) {
        App app = new App();
        app.runApplication();
    }

    public App() {
    }

    public void runApplication() {
        this.scanner = new Scanner(System.in);
        this.printMessages = new PrintMessages();
        this.printMessages.welcome();
        whileLoop:
        while(true) {
            String input = this.scanner.nextLine();
            switch (input) {
                case "q":
                    break whileLoop;
                case "notes --help":
                    this.printMessages.notesHelp();
                default:
                    this.printMessages.invalidCommand();
            }
        }
        this.scanner.close();
    }

    public boolean isNumberInput(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return false;
        }
    }
}
