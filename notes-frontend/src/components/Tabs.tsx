interface TabsProps {
  activeTab: string;
  setActiveTab: (tab: string) => void;
}

const tabs = ["view", "create", "update", "delete"];

export default function Tabs({ activeTab, setActiveTab }: TabsProps) {
  return (
    <div style={{ marginBottom: "1rem" }}>
      {tabs.map((tab) => (
        <button
          key={tab}
          onClick={() => setActiveTab(tab)}
          style={{
            padding: "0.5rem 1rem",
            marginRight: "0.5rem",
            backgroundColor: activeTab === tab ? "#4f46e5" : "#e5e7eb",
            color: activeTab === tab ? "white" : "black",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          {tab.charAt(0).toUpperCase() + tab.slice(1)}
        </button>
      ))}
    </div>
  );
}
