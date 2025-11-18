package notes;

import java.time.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;
import java.io.FileWriter;

public class Metadata {
    private String title;
    private String created;
    private String modified;
    private ArrayList<String> tags;
    private String author;
    private String status;
    private int priority;

    public Metadata(String author) {
        this.title = "new note";
        this.created = Instant.now().toString();
        this.modified = Instant.now().toString();
        this.tags = new ArrayList<String>();
        this.author = author;
        this.status = "REVIEW";
        this.priority = 5;
    }
    
    public Metadata(String title, String created, String modified, ArrayList<String> tags, String author, String status, int priority) {
        this.title = title;
        this.created = created;
        this.modified = modified;
        this.tags = tags;
        this.author = author;
        this.status = status;
        this.priority = priority;
    }

    public String getTitle() { return title; }
    public String getCreated() { return created; }
    public String getModified() { return modified; }
    public List<String> getTags() { return tags; }
    public String getAuthor() { return author; }
    public String getStatus() { return status; }
    public int getPriority() { return priority; }

    public void setTitle(String title) { this.title = title; }
    public void setCreated(String created) { this.created = created; }
    public void setModified(String modified) { this.modified = modified; }
    public void setTags(ArrayList<String> tags) { this.tags = tags; }
    public void setAuthor(String author) { this.author = author; }
    public void setStatus(String status) { this.status = status; }
    public void setPriority(int priority) { this.priority = priority; }

    public String addTag (String tag) {
        this.tags.add(tag);
        return tag;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("title", this.getTitle());
        data.put("created", this.getCreated());
        data.put("modified", this.getModified());
        data.put("tags", this.getTags());
        data.put("author", this.getAuthor());
        data.put("status", this.getStatus());
        data.put("priority", this.getPriority());

        return data;
    }

    public ArrayList<String> askForTags(Scanner scanner) {
        System.out.print("Enter tags (comma-separated, or blank for none): ");
        String line = scanner.nextLine().trim();

        if (line.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(line.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toCollection(ArrayList::new));
    }

    public Metadata loadMetadata(String filepath) throws Exception {
        Yaml yaml = new Yaml();
        try (FileInputStream inputStream = new FileInputStream("notes/src/main/metadata/" + filepath + ".yaml")) {
            return yaml.loadAs(inputStream, Metadata.class);
        }
    }

    public void saveMetadata(String filepath) throws Exception {
        Yaml yaml = new Yaml();
        this.title = filepath;
        try (FileWriter writer = new FileWriter("notes/src/main/metadata/" + filepath + ".yaml")) {
            yaml.dump(this.toMap(), writer);
        }
    }
}
