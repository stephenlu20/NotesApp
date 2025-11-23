import { useState } from "react";

export default function CreateNoteForm() {
  const [title, setTitle] = useState("");
  const [tags, setTags] = useState("");
  const [priority, setPriority] = useState<number | "">("");
  const [content, setContent] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!title) return;

    // Convert comma-separated tags to array, or undefined if empty
    const tagsArray = tags.trim() ? tags.split(",").map((t) => t.trim()) : undefined;

    // Only include priority if number 1-5
    const priorityValue = priority && priority >= 1 && priority <= 5 ? priority : undefined;

    const body: any = { title };
    if (content.trim()) body.content = content;
    if (tagsArray) body.tags = tagsArray;
    if (priorityValue) body.priority = priorityValue;

    fetch("http://localhost:8080/notes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log("Created note:", data);
        // Reset form
        setTitle("");
        setTags("");
        setPriority("");
        setContent("");
      })
      .catch((err) => console.error("Error creating note:", err));
  };

  return (
    <div>
      <h2>Create Note</h2>
      <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "0.5rem", maxWidth: "400px" }}>
        <input
          type="text"
          placeholder="Title (required)"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />

        <input
          type="text"
          placeholder="Tags (comma-separated)"
          value={tags}
          onChange={(e) => setTags(e.target.value)}
        />

        <input
          type="number"
          placeholder="Priority (1-5)"
          value={priority}
          onChange={(e) => setPriority(Number(e.target.value))}
          min={1}
          max={5}
        />

        <textarea
          placeholder="Content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          rows={4}
        />

        <button type="submit">Create Note</button>
      </form>
    </div>
  );
}
