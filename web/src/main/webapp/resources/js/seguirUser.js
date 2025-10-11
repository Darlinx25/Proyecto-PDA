

function seguirUser() {
  const btn = document.getElementById("follow");
  
  const accion = btn.textContent === "Seguir" ? "seguir" : "dejar";
  
  if (btn.textContent === "Seguir") {
    btn.textContent = "Siguiendo";
    btn.classList.remove("btn-danger");
    btn.classList.add("btn-secondary");
    
  } else {
    btn.textContent = "Seguir";
    btn.classList.remove("btn-secondary");
    btn.classList.add("btn-danger");
  }

  fetch("/seguir-usuario",{
      method: "POST",
      body: "accion=" + accion + "&usuario=" + username
  })
}

