import { useState } from "react";
import Tabs from "./components/Tabs";
import GetNoteForm from "./components/GetNoteForm";
import CreateNoteForm from "./components/CreateNoteForm";
import UpdateNoteForms from "./components/UpdateNoteForm";
import DeleteNoteForm from "./components/DeleteNoteForm";

function App() {
  const [activeTab, setActiveTab] = useState("view");

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Notes App</h1>
      <Tabs activeTab={activeTab} setActiveTab={setActiveTab} />

      {activeTab === "view" && <GetNoteForm />}
      {activeTab === "create" && <CreateNoteForm />}
      {activeTab === "update" && <UpdateNoteForms />}
      {activeTab === "delete" && <DeleteNoteForm />}
    </div>
  );
}

export default App;


// Commenting out Template. Keeping just in case
// import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
// import './App.css'

// function App() {
//   const [count, setCount] = useState(0)

//   return (
//     <>
//       <div>
//         <a href="https://vite.dev" target="_blank">
//           <img src={viteLogo} className="logo" alt="Vite logo" />
//         </a>
//         <a href="https://react.dev" target="_blank">
//           <img src={reactLogo} className="logo react" alt="React logo" />
//         </a>
//       </div>
//       <h1>Vite + React</h1>
//       <div className="card">
//         <button onClick={() => setCount((count) => count + 1)}>
//           count is {count}
//         </button>
//         <p>
//           Edit <code>src/App.tsx</code> and save to test HMR
//         </p>
//       </div>
//       <p className="read-the-docs">
//         Click on the Vite and React logos to learn more
//       </p>
//     </>
//   )
// }

// export default App
