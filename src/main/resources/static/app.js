const startButton = document.getElementById("start");
const resultsDiv = document.getElementById("results");

const recognition = new (window.SpeechRecognition || window.webkitSpeechRecognition)();
recognition.lang = "ru-RU";
recognition.interimResults = false;
recognition.maxAlternatives = 1;

startButton.onclick = () => recognition.start();

recognition.onresult = async (event) => {
  const transcript = event.results[0][0].transcript;
  console.log("Recognized text:", transcript);

  const response = await fetch("http://localhost:8080/execute/search_ticket_by_summary", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ query: transcript })
  });

  const data = await response.json();
  resultsDiv.innerText = JSON.stringify(data, null, 2);
};
