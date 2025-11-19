package notes;

import java.util.Scanner;
import java.time.*;

public class App {
    Scanner scanner;
    Notes notes;
    
    public static void main(String[] args) {
        App app = new App();
        app.runApplication();
    }

    public App() {
        this.scanner = new Scanner(System.in);
        this.notes = new Notes();
    }

    public void runApplication() {
        PrintMessages.welcome();
        this.notes.loadNotes();
        whileLoop:
        while(true) {
            System.out.print("> ");
            String[] input = this.scanner.nextLine().split(" ");
            newCommand:
            switch (input[0]) {
                case "q":
                    break whileLoop;
                case "notes":
                    switch (input[1]) { 
                        case "--help":
                            PrintMessages.notesHelp();
                            break newCommand;
                        case "create":
                            if (input.length == 2) {
                                String title = Note.getNoteTitle(this.scanner);
                                if (notes.fileExists(title)) {
                                    PrintMessages.fileAlreadyExists();
                                } else {
                                    Note note = new Note();
                                    note = Note.createNote(this.scanner, "Stephen", title);
                                    this.notes.addNote(note);
                                }
                            } else if (input.length == 3) {
                                if (notes.fileExists(input[2])) {
                                    PrintMessages.fileAlreadyExists();
                                } else {
                                    Note note = new Note();
                                    note = Note.createNote(this.scanner, "Stephen", input[2]);
                                    this.notes.addNote(note);
                                }
                            } else {
                                PrintMessages.invalidCommand();
                            }
                            break newCommand;
                        case "list":
                            if (input.length == 4) {
                                if (input[2].equals("--tag")) {
                                    this.notes.listNotes(input[3]);
                                } else {
                                    PrintMessages.invalidCommand();
                                }
                            } else {
                                this.notes.listNotes();;
                            }
                            break newCommand;
                        case "edit":
                            if (input.length == 3) {
                                if (!notes.fileExists(input[2])) {
                                    PrintMessages.fileDoesNotExist();
                                } else {
                                    Note note = notes.searchByTitle(input[2]);
                                    note.edit(scanner);
                                }
                            } else {
                                PrintMessages.invalidCommand();
                            }
                            break newCommand;
                        case "search":
                            if (input.length == 3) {
                                Notes searchList = this.notes.search(input[2]);
                                searchList.listNotes();
                            } else {
                                PrintMessages.invalidCommand();
                            }
                            break newCommand;
                        case "delete":
                            if (input.length == 3) {
                                if (!notes.fileExists(input[2])) {
                                    PrintMessages.fileDoesNotExist();
                                } else {
                                    Note note = notes.searchByTitle(input[2]);
                                    notes.removeNote(note);
                                    note.delete();
                                }
                            } else {
                                PrintMessages.invalidCommand();
                            }
                            break newCommand;
                        case "read":
                            if (input.length == 3) {
                                if (!notes.fileExists(input[2])) {
                                    PrintMessages.fileDoesNotExist();
                                } else {
                                    Note note = notes.searchByTitle(input[2]);
                                    note.read();
                                }
                            } else {
                                PrintMessages.invalidCommand();
                            }
                            break newCommand;
                        case "stats":
                            if (input.length == 2) {
                                notes.stats();
                            } else {
                                PrintMessages.invalidCommand();
                            }
                            break newCommand;
                        }
                default:
                    PrintMessages.invalidCommand();
                    break;
            }
        }
        this.scanner.close();
    }
}
