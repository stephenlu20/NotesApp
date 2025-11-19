package notes;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Notes {
    private ArrayList<Note> notes;

    public Notes() {
        this.notes = new ArrayList<>();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public ArrayList<Note> loadNotes(){
        try {
            // Collect .note and .yaml files into maps by base name
            Map<String, Path> noteFiles = Files.list(Constants.PATH)
                    .filter(p -> p.getFileName().toString().endsWith(".note"))
                    .collect(Collectors.toMap(
                            p -> stripExtension(p.getFileName().toString()),
                            p -> p
                    ));

            Map<String, Path> yamlFiles = Files.list(Constants.METADATA_PATH)
                    .filter(p -> p.getFileName().toString().endsWith(".yaml"))
                    .collect(Collectors.toMap(
                            p -> stripExtension(p.getFileName().toString()),
                            p -> p
                    ));

            ArrayList<Note> notes = new ArrayList<>();

            // Match pairs by filename (base name)
            for (String baseName : noteFiles.keySet()) {
                if (!yamlFiles.containsKey(baseName)) {
                    System.err.println("Warning: YAML missing for " + baseName);
                    continue;
                }

                Path notePath = noteFiles.get(baseName);
                Path yamlPath = yamlFiles.get(baseName);

                // Load metadata (your custom method)
                Metadata metadata = Metadata.loadMetadata("/" + baseName);

                // Load file content as string
                String content = Files.readString(notePath);

                // Construct Note object
                Note note = new Note();
                note.setMetadata(metadata);
                note.setContent(content);
                note.setPath(notePath.toString());
                note.setYamlPath(yamlPath.toString());

                this.notes.add(note);
            }
            return notes;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Note>();
        }
    }

    private String stripExtension(String name) {
        int idx = name.lastIndexOf('.');
        return (idx > 0) ? name.substring(0, idx) : name;
    }

    public void listNotes(String tag) {
        for (Note n : this.notes) {
            if (n.getMetadata().getTags().contains(tag)) {
                System.out.println(n.getMetadata().getTitle());
            }
        }
    }

    public void listNotes() {
        for (Note n : this.notes) {
            System.out.println(n.getMetadata().getTitle());
        }
    }

    public boolean fileExists(String fileName) {
        for (Note n : this.notes) {
            if (fileName.equals(n.getMetadata().getTitle())) {
                return true;
            }
        }
        return false;
    }
}
