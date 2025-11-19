package notes;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            
            ArrayList<Path> filePaths = Files.list(Constants.PATH)
                                        .filter(path -> Files.isRegularFile(path))
                                        .filter(path -> path.getFileName().toString().endsWith(".note"))
                                        .collect(Collectors.toCollection(ArrayList::new));

            ArrayList<Note> notes = new ArrayList<>();

            for (Path path : filePaths) {
                // Load metadata (your custom method)
                Metadata metadata = Metadata.loadMetadata(path.toString());

                // Load file content as string
                String content = Files.readString(path);

                content = content.split("---")[2];

                // Construct Note object
                Note note = new Note();
                note.setMetadata(metadata);
                note.setContent(content);
                note.setPath(path.toString());

                this.notes.add(note);
            }
            return notes;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Note>();
        }
    }

    public Note searchByTitle(String title) {
        for (Note n : this.notes) {
            if (n.metadata.getTitle().equals(title)) {
                return n;
            }
        }
        return new Note();
    }

    public Note removeNote(Note note) {
        this.notes.remove(note);
        return note;
    }

    public Notes search(String query) {
        Notes returnList = new Notes();
        returnList.notes = new ArrayList<Note>();
        for (Note n : this.notes) {
            if (n.metadata.getTitle().equals(query) && !returnList.notes.contains(n)) {
                returnList.notes.add(n);
            }
        }
        for (Note n : this.notes) {
            if (n.metadata.getTags().contains(query) && !returnList.notes.contains(n)) {
                returnList.notes.add(n);
            }
        }
        for (Note n : this.notes) {
            if (n.getContent().contains(query) && !returnList.notes.contains(n)) {
                returnList.notes.add(n);
            }
        }
        return returnList;
    }

    public void listNotes(String tag) {
        for (Note n : this.notes) {
            if (n.metadata.getTags().contains(tag)) {
                System.out.println(n.metadata.getTitle());
            }
        }
    }

    public void listNotes() {
        for (Note n : this.notes) {
            System.out.println(n.metadata.getTitle());
        }
    }

    public void stats() {
        System.out.print("Total number of notes: ");
        System.out.println(notes.size());
    }

    public boolean fileExists(String fileName) {
        for (Note n : this.notes) {
            if (fileName.equals(n.metadata.getTitle())) {
                return true;
            }
        }
        return false;
    }
}
