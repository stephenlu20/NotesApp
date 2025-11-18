package notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner scanner;
    PrintMessages printMessages = new PrintMessages();
    Editor editor = new Editor();
    ArrayList<String> filesList;
    HashMap<String, Note> filesMap;
    
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
            this.filesMap = Note.getFiles();
            this.filesList = getFileNames();
            String[] input = this.scanner.nextLine().split(" ");
            newCommand:
            switch (input[0]) {
                case "q":
                    break whileLoop;
                case "notes" :
                    switch (input[1]) { 
                        case "--help":
                            this.printMessages.notesHelp();
                            break newCommand;
                        case "create":
                            String filepath = "";
                            try {
                                if (input.length == 2) {
                                    filepath = this.editor.getNoteTitle(scanner);
                                    System.out.println(filepath + "testing");
                                } else if (input.length == 3) {
                                    filepath = input[2];
                                } else {
                                    this.printMessages.invalidCommand();
                                    break newCommand;
                                }
                                if (!filesList.contains(filepath)){
                                    Metadata metadata = new Metadata("Stephen");
                                    String content = this.editor.createNote();
                                    metadata.setTags(metadata.askForTags(scanner));
                                    metadata.saveMetadata(filepath);
                                    this.editor.saveFile(filepath, content);
                                } else {
                                    printMessages.fileAlreadyExists();
                                }
                                break newCommand;
                            } catch (Exception e) {
                                break newCommand;
                            }
                        case "list":
                            if (input.length > 3) {
                                if (input[2].equals("--tag")) {
                                    this.filesList = listByTag(input[3]);
                                    listNotes();
                                } else {
                                    this.printMessages.invalidCommand();
                                    break newCommand;
                                }
                            } else {
                                listNotes();
                            }
                            break newCommand;
                        case "edit":
                            if (input.length == 3) {
                                String file = editor.editNote(input[2]);
                                if (file == null) {
                                    this.printMessages.fileDoesNotExist();
                                    break newCommand;
                                }
                            } else {
                                this.printMessages.invalidCommand();
                                break newCommand;
                            }
                            break newCommand;
                        case "search":
                            if (input.length == 3) {
                                this.filesList = fileSearch(input[2]);
                                listNotes();
                            } else {
                                this.printMessages.invalidCommand();
                                break newCommand;
                            }
                            break newCommand;
                    }
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
        if (filesList.isEmpty()) {
            System.out.println("No files");
        }
        for (String s : filesList) {
            System.out.println(s);
        }
    }

    public ArrayList<String> listByTag(String tag)  {
        ArrayList<String> list = new ArrayList<>();
        for (HashMap.Entry<String, Note> entry : this.filesMap.entrySet()) {
            List<String> currentList = entry.getValue().getMetadata().getTags();
            if (currentList.contains(tag)) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    public ArrayList<String> fileSearch(String searchTerm) {
        ArrayList<String> list = new ArrayList<>();

        for (HashMap.Entry<String, Note> entry : this.filesMap.entrySet()) {
            if (entry.getKey().contains(searchTerm)) {
                list.add(entry.getKey());
            }
            if (entry.getValue().getContent().contains(searchTerm)) {
                list.add(entry.getKey());
            }
            List<String> currentList = entry.getValue().getMetadata().getTags();
            if (currentList.contains(searchTerm)) {
                list.add(entry.getKey());
            }
        }
        return list;
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
