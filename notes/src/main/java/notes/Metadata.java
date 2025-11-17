package notes;

import java.time.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import org.yaml.snakeyaml.Yaml;
import java.io.FileWriter;

public class Metadata {
    private String title;
    private Instant created;
    private Instant modified;
    private ArrayList<String> tags;
    private String author;
    private String status;
    private int priority;

    public Metadata(String author) {
        this.title = "new note";
        this.created = Instant.now();
        this.modified = Instant.now();
        this.tags = new ArrayList<String>();
        this.author = author;
        this.priority = 5;
    }
    
    public Metadata(String title, Instant created, Instant modified, ArrayList<String> tags, String author, String status, int priority) {
        this.title = title;
        this.created = created;
        this.modified = modified;
        this.tags = tags;
        this.author = author;
        this.status = status;
        this.priority = priority;
    }

    public String getTitle() {
        return this.title;
    }

    public Instant getCreatedDate() {
        return this.created;
    }

    public Instant getModifiedDate() {
        return this.modified;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getStatus() {
        return this.status;
    }

    public int getPriority() {
        return this.priority;
    }

    public String setTitle(String title) {
        this.title = title;
        return title;
    }

    public Instant setCreatedDate(Instant date) {
        this.created = date;
        return date;
    }
    
    public Instant setModifiedDate(Instant date) {
        this.modified = date;
        return date;
    }

    public ArrayList<String> setTags (ArrayList<String> tags) {
        this.tags = tags;
        return tags;
    }

    public String setAuthor (String author) {
        this.author = author;
        return author;
    }

    public String setStatus (String status) {
        this.status = status;
        return status;
    }

    public int setPriority (int priority) {
        this.priority = priority;
        return priority;
    }

    public static Metadata loadMetadata(String filepath) throws Exception {
        Yaml yaml = new Yaml();
        try (FileInputStream inputStream = new FileInputStream(filepath)) {
            return yaml.loadAs(inputStream, Metadata.class);
        }
    }

    public static void saveMetadata(Metadata metadata, String filepath) throws Exception {
        Yaml yaml = new Yaml();
        try (FileWriter writer = new FileWriter(filepath)) {
            yaml.dump(metadata, writer);
        }
    }
}
