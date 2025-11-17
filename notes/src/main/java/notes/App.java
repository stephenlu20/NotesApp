package notes;

import java.util.Scanner;

public class App {
    Scanner scanner;
    PrintMessages printMessages = new PrintMessages();
    
    public static void main(String[] args) {
        App app = new App();
        app.runApplication();
    }

    public App() {

    }

    public void runApplication() {
        this.scanner = new Scanner(System.in);
        this.printMessages.welcome();
        whileLoop:
        while(true) {
            String input = this.scanner.nextLine();
            switch (input) {
                case "q":
                    break whileLoop;
                case "notes --help":
                    this.printMessages.notesHelp();
                    break;
                case "notes create":
                default:
                    this.printMessages.invalidCommand();
                    break;
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
