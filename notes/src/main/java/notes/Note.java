package notes;

public class Note {
    private Metadata metadata;
    private String content;

    public Note(Metadata metadata, String content){
        this.metadata = metadata;
        this.content = content;
    }

    public Note(String author) {
        this.metadata = new Metadata(author);
        this.content = "";
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public String getContent() {
        return this.content;
    }

    public Metadata setMetadata(Metadata metadata) {
        this.metadata = metadata;
        return metadata;
    }

    public String setContent(String content) {
        this.content = content;
        return content;
    }
}
