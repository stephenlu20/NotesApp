import { useState } from "react";

interface Note {
  id: number;
  title: string;
  content: string;
  tags: string[];
  priority: number;
}

export default function GetNoteForm() {
  const [allNotes, setAllNotes] = useState<Note[]>([]);
  const [singleNote, setSingleNote] = useState<Note | null>(null);
  const [noteId, setNoteId] = useState<number | "">("");

  // Fetch all notes
  const fetchAllNotes = () => {
    fetch("http://localhost:8080/notes")
      .then((res) => res.json())
      .then((data: Note[]) => {
        setAllNotes(data);
        setSingleNote(null); // clear single note view
      })
      .catch((err) => console.error("Error fetching notes:", err));
  };

  // Fetch single note by ID
  const fetchSingleNote = () => {
    if (!noteId) return;

    fetch(`http://localhost:8080/notes/${noteId}`)
      .then((res) => {
        if (!res.ok) throw new Error("Note not found");
        return res.json();
      })
      .then((data: Note) => {
        setSingleNote(data);
        setAllNotes([]);
      })
      .catch((err) => {
        console.error(err);
        setSingleNote(null);
      });
  };

  return (
    <div>
      <h2>Get Notes</h2>

      <div style={{ marginBottom: "1rem" }}>
        <button onClick={fetchAllNotes} style={{ marginRight: "1rem" }}>
          Get All Notes
        </button>

        <input
          type="number"
          placeholder="Note ID"
          value={noteId}
          onChange={(e) => setNoteId(Number(e.target.value))}
          style={{ marginRight: "0.5rem" }}
        />
        <button onClick={fetchSingleNote}>Get Note by ID</button>
      </div>

      {/* Display all notes */}
      {allNotes.length > 0 && (
        <div style={{ display: "flex", flexDirection: "column", gap: "0.5rem" }}>
          {allNotes.map((note) => (
            <div
              key={note.id}
              style={{
                padding: "0.5rem 1rem",
                border: "1px solid #ccc",
                borderRadius: "6px",
                backgroundColor: "#f9f9f9",
                maxWidth: "500px",
              }}
            >
              <p><strong>ID:</strong> {note.id}</p>
              <p><strong>Title:</strong> {note.title || "No Title"}</p>
            </div>
          ))}
        </div>
      )}

      {/* Display single note */}
      {singleNote && (
        <div
          style={{
            marginTop: "1rem",
            padding: "1rem",
            border: "1px solid #ccc",
            borderRadius: "6px",
            backgroundColor: "#f9f9f9",
            maxWidth: "500px",
          }}
        >
          <h3>{singleNote.title}</h3>
          <p><strong>ID:</strong> {singleNote.id}</p>
          <p><strong>Tags:</strong> {singleNote.tags.join(", ") || "None"}</p>
          <p><strong>Priority:</strong> {singleNote.priority}</p>

          <div
            style={{
              marginTop: "0.5rem",
              padding: "0.5rem",
              border: "1px solid #ddd",
              borderRadius: "4px",
              backgroundColor: "white",
              whiteSpace: "pre-wrap",
            }}
          >
            <strong>Content:</strong>
            <div>{singleNote.content}</div>
          </div>
        </div>
      )}
    </div>
  );
}
