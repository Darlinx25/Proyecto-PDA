

function seguirUser(){
  const btn = document.getElementById("follow");
  const accion = btn.textContent === "Seguir" ? "seguir" : "dejar";


  if (accion === "seguir") {
    btn.textContent = "Siguiendo";
    btn.classList.replace("btn-danger", "btn-success");
  } else {
    btn.textContent = "Seguir";
    btn.classList.replace("btn-success", "btn-danger");
  }

  fetch("/seguir-usuario", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: "accion=" + accion + "&usuario=" + u
  })
  .then(resp => resp.text())
  .then(data => console.log("Servidor respondió:", data))
  .catch(err => console.error(err));
}
