import { useState } from "react";

interface Note {
  id: number;
  title: string;
  content?: string;
  tags?: string[];
  priority?: number;
  author?: string;
  status?: string;
}

type StatusFilter = "ALL" | "REVIEW" | "COMPLETE";

export default function GetNoteForm() {
  const [allNotes, setAllNotes] = useState<Note[]>([]);
  const [singleNote, setSingleNote] = useState<Note | null>(null);
  const [noteId, setNoteId] = useState<number | "">("");
  const [filterStatus, setFilterStatus] = useState<StatusFilter>("ALL");

  const fetchAllNotes = () => {
    fetch("http://localhost:8080/notes")
      .then((res) => res.json())
      .then((data: Note[]) => {
        setAllNotes(data);
        setSingleNote(null);
      })
      .catch((err) => console.error("Error fetching notes:", err));
  };

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

  // Filter notes based on tab
  const filteredNotes = allNotes.filter(note => {
    if (filterStatus === "ALL") return true;
    return note.status === filterStatus;
  });

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

      {/* Status Filter Tabs */}
      {allNotes.length > 0 && (
        <div style={{ marginBottom: "1rem" }}>
          {(["ALL", "REVIEW", "COMPLETE"] as StatusFilter[]).map((status) => (
            <button
              key={status}
              onClick={() => setFilterStatus(status)}
              style={{
                marginRight: "0.5rem",
                backgroundColor: filterStatus === status ? "#ddd" : "#f5f5f5",
                fontWeight: filterStatus === status ? "bold" : "normal",
              }}
            >
              {status === "ALL" ? "All Notes" : `Status: ${status}`}
            </button>
          ))}
        </div>
      )}

      {/* Display filtered notes */}
      {filteredNotes.length > 0 && (
        <div style={{ display: "flex", flexDirection: "column", gap: "0.5rem" }}>
          {filteredNotes.map((note) => (
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
              <p><strong>Author:</strong> {note.author ?? "Unknown"}</p>
              {note.tags && note.tags.length > 0 && (
                <p><strong>Tags:</strong> {note.tags.join(", ")}</p>
              )}
              {note.status && (
                <p><strong>Status:</strong> {note.status}</p>
              )}
              <p><strong>Priority:</strong> {note.priority}</p>
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
          <p><strong>Author:</strong> {singleNote.author ?? "Unknown"}</p>
          {singleNote.tags && <p><strong>Tags:</strong> {singleNote.tags.join(", ")}</p>}
          {singleNote.status && <p><strong>Status:</strong> {singleNote.status}</p>}
          {singleNote.priority !== undefined && (
            <p><strong>Priority:</strong> {singleNote.priority}</p>
          )}

          {singleNote.content && (
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
          )}
        </div>
      )}
    </div>
  );
}
