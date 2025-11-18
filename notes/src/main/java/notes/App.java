package notes;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    Scanner scanner;
    PrintMessages printMessages = new PrintMessages();
    Editor editor = new Editor();
    ArrayList<String> files;
    
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
            this.files = getFileNames();
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
                        String filepath = this.editor.getNoteTitle(scanner);
                        String content = this.editor.createNote();
                        metadata.setTags(metadata.askForTags(scanner));
                        metadata.saveMetadata(filepath);
                        this.editor.saveFile(content, filepath);
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
        File folder = new File(Constants.PATH);
        File[] files = folder.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".note")) {
                    String name = file.getName().replace(".note", "").trim();
                    fileNames.add(name);
                }
            }
        }
        return fileNames;
    }

    public void listNotes() {
        for (String s : files) {
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
