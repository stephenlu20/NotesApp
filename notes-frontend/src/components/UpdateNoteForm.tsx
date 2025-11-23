import { useState } from "react";

export default function UpdateNoteForms() {
  const [id, setId] = useState<number | "">("");
  const [content, setContent] = useState("");

  const handleUpdateContent = () => {
    if (!id) return;

    fetch(`http://localhost:8080/notes/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ content }),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log("Updated note:", data);
        setContent("");
        setId("");
      })
      .catch((err) => console.error(err));
  };

  return (
    <div>
      <h2>Update Note</h2>

      <div style={{ marginBottom: "1rem" }}>
        <h4>Update Content</h4>
        <input
          type="number"
          placeholder="ID"
          value={id}
          onChange={(e) => setId(Number(e.target.value))}
          style={{ marginRight: "0.5rem" }}
        />
        <input
          type="text"
          placeholder="New content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          style={{ marginRight: "0.5rem" }}
        />
        <button onClick={handleUpdateContent}>Update Content</button>
      </div>

      {/* You can add more sections here for tags, status, priority, etc. */}
    </div>
  );
}
