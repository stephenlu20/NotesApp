package notes;

import java.time.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.yaml.snakeyaml.Yaml;

public class Note {
    public Metadata metadata;
    private String content;
    private String path;

    public Note() {
    }

    public Metadata getMetadata() { return metadata; }

    public void setMetadata(Metadata metadata) { this.metadata = metadata; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public static Note createNote(Scanner scanner, String author, String title) {
        Note note = new Note();
        note.content = writeContent();

        note.metadata = new Metadata(title, author);
        ArrayList<String> tags = Metadata.askForTags(scanner);
        note.metadata.setTags(tags);

        Yaml yaml = new Yaml();
        String yamlText = yaml.dump(note.metadata.toMap());
        Path finalNotePath = Constants.PATH.resolve(title + ".note");
        note.path = finalNotePath.toString();
        File finalFile = finalNotePath.toFile();
        try (FileWriter writer = new FileWriter(finalFile)) {
            writer.write("---\n");
            writer.write(yamlText);
            writer.write("---\n\n");
            writer.write(note.content);
            Files.deleteIfExists(Constants.TEMP_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return note;
    }

    public void edit(Scanner scanner) {
        String text = "";
        try{
            text = Files.readString(Paths.get(this.path));
            Files.writeString(Constants.TEMP_FILE, text.split("---")[2]);
            ProcessBuilder pb = new ProcessBuilder("nano", Constants.TEMP_FILE.toString());
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
            this.metadata.setModified(Instant.now().toString());
            this.content = Files.readString(Constants.TEMP_FILE);

            Yaml yaml = new Yaml();
            String yamlText = yaml.dump(this.metadata.toMap());
            try (FileWriter writer = new FileWriter(this.path)) {
                writer.write("---\n");
                writer.write(yamlText);
                writer.write("---\n\n");
                writer.write(this.content);
                Files.deleteIfExists(Constants.TEMP_FILE);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            Path path = Paths.get(this.path);
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        String text = "";
        try{
            text = Files.readString(Paths.get(this.path));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("===");
        System.out.println(text.split("---")[2]);
        System.out.println("===");
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
}
