package notes;

public class PrintMessages {

    public PrintMessages() {

    }

    public void welcome() {
        System.out.println("=========================================");
        System.out.println("                Notes App                ");
        System.out.println("=========================================");
        System.out.println("            Enter 'q' to quit            ");
        System.out.println("=========================================");
        System.out.println("       'notes --help' for commands       ");
        System.out.println();
    }

    public void notesHelp() {
        System.out.println("notes --help                     # Display help information");
        System.out.println("notes create                     # Create a new note (opens in default editor)");
        System.out.println("notes list                       # List all notes");
        System.out.println("notes list --tag \"coursework\"    # List notes with specific tag");
        System.out.println("notes read <note-id>             # Display a specific note");
        System.out.println("notes edit <note-id>             # Edit a specific note");
        System.out.println("notes delete <note-id>           # Delete a specific note");
        System.out.println("notes search \"query\"             # Search notes for text (title, tags, content)");
        System.out.println("notes stats                      # Display statistics about your notes");
    }

    public void invalidCommand() {
        System.out.println("Invalid Command");
    }
}
