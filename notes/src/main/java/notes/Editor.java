package notes;

import java.nio.file.*;
import java.util.Scanner;

public class Editor {
    Path path;
    Path tempFile;

    public Editor (){
        try {
            this.path = Paths.get("notes/src/main/resources");
            Files.createDirectories(this.path);
            this.tempFile = this.path.resolve("temp-note.temp");
        } catch (Exception e) {
            System.out.println("Unable to find filepath");
        }
    }
    
    public String createNote() throws Exception{
        Files.writeString(this.tempFile, ""); // start empty

        ProcessBuilder pb = new ProcessBuilder("nano", tempFile.toString());
        pb.inheritIO(); // attach terminal input/output
        Process process = pb.start();
        process.waitFor(); // wait until editor is closed

        return Files.readString(tempFile);
    }

    public String getNoteTitle(Scanner scanner) {
        System.out.print("Enter note file name: ");
        return scanner.nextLine().trim();    
    }

    public void saveFile(String fileName, String content) throws Exception{
        Path finalNotePath = this.path.resolve(fileName + ".note");
        System.out.println("Saving file");
        Files.writeString(finalNotePath, content);
        Files.deleteIfExists(this.tempFile);
    }
}
