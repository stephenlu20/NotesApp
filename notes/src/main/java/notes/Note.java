package notes;

import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Note {
    private Metadata metadata;
    private String content;
    private String path;
    private String yamlPath;

    public Note() {
    }

    public Metadata getMetadata() { return metadata; }

    public void setMetadata(Metadata metadata) { this.metadata = metadata; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public String getYamlPath() { return yamlPath; }

    public void setYamlPath(String yamlPath){ this.yamlPath = yamlPath; }

    public static Note createNote(Scanner scanner, String author, String title) {
        Note note = new Note();
        note.content = writeContent();
        note.path = saveFile(title, note.content);
        note.metadata = new Metadata(title, author);
        ArrayList<String> tags = Metadata.askForTags(scanner);
        note.metadata.setTags(tags);
        note.metadata.saveMetadata(title);

        return note;
    }

    private static String writeContent(){
        try {
            Files.writeString(Constants.TEMP_FILE, ""); // start empty

            ProcessBuilder pb = new ProcessBuilder("nano", Constants.TEMP_FILE.toString());
            pb.inheritIO(); // attach terminal input/output
            Process process = pb.start();
            process.waitFor(); // wait until editor is closed
            return Files.readString(Constants.TEMP_FILE);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getNoteTitle(Scanner scanner) {
        System.out.print("Enter note file name: ");
        return scanner.nextLine().trim();
    }

    public static String saveFile(String fileName, String content) {
        try {
            Path finalNotePath = Constants.PATH.resolve(fileName + ".note");
            System.out.println("Saving file");
            Files.writeString(finalNotePath, content);
            Files.deleteIfExists(Constants.TEMP_FILE);
            return finalNotePath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
