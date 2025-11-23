import { useState } from "react";

export default function DeleteNoteForm() {
  const [id, setId] = useState<number | "">("");

  const handleDelete = () => {
    if (!id) return;

    fetch(`http://localhost:8080/notes/${id}`, {
      method: "DELETE",
    })
      .then(() => {
        console.log("Deleted note", id);
        setId("");
      })
      .catch((err) => console.error(err));
  };

  return (
    <div>
      <h2>Delete Note</h2>
      <input
        type="number"
        placeholder="ID"
        value={id}
        onChange={(e) => setId(Number(e.target.value))}
        style={{ marginRight: "0.5rem" }}
      />
      <button onClick={handleDelete}>Delete</button>
    </div>
  );
}
