package notes;

import java.util.Scanner;

import org.yaml.snakeyaml.*;

public class App {
    Scanner scanner;
    PrintMessages printMessages = new PrintMessages();
    Editor editor = new Editor();
    
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
                    try {
                        Metadata metadata = new Metadata("Stephen");
                        String content = this.editor.createNote();
                        String filepath = this.editor.getNoteTitle(scanner);
                        metadata.setTags(metadata.askForTags(scanner));
                        metadata.saveMetadata(filepath);
                        this.editor.saveFile(content, filepath);
                        break;
                    } catch (Exception e) {
                        break;
                    }
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
