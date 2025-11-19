package notes;
import java.nio.file.*;

public final class Constants {
    public static final Path PATH = Paths.get("notes/src/main/resources/");
    public static final Path TEMP_FILE = PATH.resolve("temp-note.temp");
}
