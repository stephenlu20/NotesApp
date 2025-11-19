package notes;

public class PrintMessages {

    public PrintMessages() {

    }

    public static void welcome() {
        System.out.println("=========================================");
        System.out.println("                Notes App                ");
        System.out.println("=========================================");
        System.out.println("            Enter 'q' to quit            ");
        System.out.println("=========================================");
        System.out.println("       'notes --help' for commands       ");
        System.out.println();
    }

    public static void notesHelp() {
        System.out.println("notes --help                     # Display help information");
        System.out.println("notes create                     # Create a new note (opens in default editor)");
        System.out.println("notes create <note-id>           # Create a new note with <note-id> (opens in default editor)");
        System.out.println("notes list                       # List all notes");
        System.out.println("notes list --tag \"coursework\"    # List notes with specific tag");
        System.out.println("notes read <note-id>             # Display a specific note");
        System.out.println("notes edit <note-id>             # Edit a specific note");
        System.out.println("notes delete <note-id>           # Delete a specific note");
        System.out.println("notes search \"query\"             # Search notes for text (title, tags, content)");
        System.out.println("notes stats                      # Display statistics about your notes");
    }

    public static void fileAlreadyExists() {
        System.out.println("File name already exists");
        System.out.println("Please use 'notes edit' to edit existing file");
    }

    public static void fileDoesNotExist() {
        System.out.println("File does not exist");
    }


    public static void invalidCommand() {
        System.out.println("Invalid Command");
    }
}
