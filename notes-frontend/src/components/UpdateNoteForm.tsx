import { useState } from "react";

interface Note {
  id: number;
  title: string;
  author: string;
  content: string;
  tags: string[];
  priority: number;
  status: string;
}

export default function UpdateNoteForm() {
  const [noteId, setNoteId] = useState<number | "">("");
  const [note, setNote] = useState<Note | null>(null);

  // Editable fields
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [tags, setTags] = useState("");
  const [priority, setPriority] = useState<number | "">("");
  const [content, setContent] = useState("");

  const fetchNote = () => {
    if (!noteId) return;

    fetch(`http://localhost:8080/notes/${noteId}`)
      .then((res) => {
        if (!res.ok) throw new Error("Note not found");
        return res.json();
      })
      .then((data: Note) => {
        setNote(data);

        // Pre-fill fields
        setTitle(data.title);
        setAuthor(data.author);
        setTags(data.tags?.join(", ") || "");
        setPriority(data.priority);
        setContent(data.content || "");
      })
      .catch(() => alert("Note not found"));
  };

  const updateNote = () => {
    if (!note) return;
    if (!title.trim() || !author.trim()) {
      alert("Title and author cannot be empty");
      return;
    }

    const updatedFields: any = {};

    if (title !== note.title) updatedFields.title = title;
    if (author !== note.author) updatedFields.author = author;

    const newTags = tags.trim()
      ? tags.split(",").map((t) => t.trim())
      : [];

    if (JSON.stringify(newTags) !== JSON.stringify(note.tags)) {
      updatedFields.tags = newTags;
    }

    if (priority && priority !== note.priority) {
      updatedFields.priority = priority;
    }

    if (content !== note.content) updatedFields.content = content;

    if (Object.keys(updatedFields).length === 0) {
      alert("No changes to update!");
      return;
    }

    fetch(`http://localhost:8080/notes/${note.id}`, {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updatedFields),
    })
      .then((res) => res.json())
      .then((data) => {
        setNote(data);
        alert("Note updated!");
      })
      .catch(console.error);
  };

  const toggleStatus = () => {
    if (!note) return;

    fetch(`http://localhost:8080/notes/${note.id}/toggle-status`, {
      method: "PATCH",
    })
      .then((res) => res.json())
      .then((data) => {
        setNote(data);
        alert("Status updated!");
      })
      .catch(console.error);
  };

  return (
    <div>
      <h2>Update Note</h2>

      <div style={{ marginBottom: "1rem" }}>
        <input
          type="number"
          placeholder="Note ID"
          value={noteId}
          onChange={(e) => setNoteId(Number(e.target.value))}
          style={{ marginRight: "0.5rem" }}
        />
        <button onClick={fetchNote}>Load Note</button>
      </div>

      {note && (
        <form
          onSubmit={(e) => {
            e.preventDefault();
            updateNote();
          }}
          style={{
            display: "flex",
            flexDirection: "column",
            gap: "0.5rem",
            maxWidth: "400px",
          }}
        >
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder="Title (required)"
            required
          />

          <input
            type="text"
            value={author}
            onChange={(e) => setAuthor(e.target.value)}
            placeholder="Author (required)"
            required
          />

          <input
            type="text"
            value={tags}
            onChange={(e) => setTags(e.target.value)}
            placeholder="Tags (comma-separated)"
          />

          <input
            type="number"
            min={1}
            max={5}
            value={priority}
            onChange={(e) => setPriority(Number(e.target.value))}
            placeholder="Priority (1-5)"
          />

          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            rows={4}
            placeholder="Content"
          />

          <p><strong>Status:</strong> {note.status}</p>

          <button type="submit">Update Note</button>
          <button type="button" onClick={toggleStatus}>
            Toggle Status
          </button>
        </form>
      )}
    </div>
  );
}
