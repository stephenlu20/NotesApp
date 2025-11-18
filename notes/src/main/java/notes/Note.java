package notes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Path;

import org.yaml.snakeyaml.Yaml;

public class Note {
    private Metadata metadata;
    private String content;

    public Note() {
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static HashMap<String, Note> getFiles() {
        HashMap<String, Note> map = new HashMap<>();
        Yaml yaml = new Yaml();
        File content_folder = new File(Constants.PATH);
        File metadata_folder = new File(Constants.METADATA_PATH);

        if (!content_folder.exists() || !content_folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder path: " + Constants.PATH);
        }

        if (!metadata_folder.exists() || !metadata_folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder path: " + Constants.METADATA_PATH);
        }

        File[] files = metadata_folder.listFiles((dir, name) -> name.endsWith(".yaml"));

        if (files == null) return map;

        for (File file : files) {
            try (FileInputStream fis = new FileInputStream(file)) {
                Metadata data = yaml.loadAs(fis, Metadata.class);
                Note note = new Note();
                note.setMetadata(data);
                if (data != null) {
                    String keyName = file.getName().replace(".yaml", "");
                    note.setContent(Files.readString(Path.of(Constants.PATH + keyName + ".note")));
                    map.put(keyName, note);
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + file.getName());
                e.printStackTrace();
            }
        }
        return map;
    }
}
