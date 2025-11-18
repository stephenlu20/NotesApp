package notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    Scanner scanner;
    PrintMessages printMessages = new PrintMessages();
    Editor editor = new Editor();
    ArrayList<String> filesList;
    HashMap<String, Metadata> filesMap;
    
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
            this.filesMap = Metadata.getFiles();
            this.filesList = getFileNames();
            String input = this.scanner.nextLine();
            switch (input) {
                case "q":
                    break whileLoop;
                case "notes --help":
                    this.printMessages.notesHelp();
                    break;
                case "notes create":
                    try {
                        String filepath = this.editor.getNoteTitle(scanner);
                        if (!filesList.contains(filepath)){
                            Metadata metadata = new Metadata("Stephen");
                            String content = this.editor.createNote();
                            metadata.setTags(metadata.askForTags(scanner));
                            metadata.saveMetadata(filepath);
                            this.editor.saveFile(content, filepath);
                        } else {
                            printMessages.fileAlreadyExists();
                        }
                        break;
                    } catch (Exception e) {
                        break;
                    }
                case "notes list":
                    listNotes();
                    break;
                default:
                    this.printMessages.invalidCommand();
                    break;
            }
        }
        this.scanner.close();
    }

    public ArrayList<String> getFileNames() {
        ArrayList<String> fileNames = new ArrayList<>(filesMap.keySet());
        return fileNames;
    }

    public void listNotes() {
        for (String s : filesList) {
            System.out.println(s);
        }
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
