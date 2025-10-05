

function propPorEstado(btn) {
    const ESTADO = btn.getAttribute("data-estado");
    const tabId = btn.getAttribute("data-bs-target");

    fetch(`/obtener-propuesta-por-estado?estado=${encodeURIComponent(ESTADO)}`)
            .then(res => res.json())
            .then(data => {
                const tabDiv = document.querySelector(tabId);
                tabDiv.innerHTML = ""; // limpiar contenido previo

                data.forEach(prop => {
                    const div = document.createElement("div");
                    div.className = "mb-2 p-2 border rounded bg-light";
                    div.innerHTML = `
                        <div class="d-flex align-items-center gap-5">
                            <div>
                                <p><strong>Título:</strong> ${prop.titulo}</p>
                                <p><strong>Descripción:</strong> ${prop.descripcion}</p>
                                <p><strong>Precio entrada:</strong> $${prop.precioEntrada}</p>
                                <p><strong>Categoria:</strong> ${prop.categoria}</p>
                            </div>
                            <div ">
                                <p><strong>${prop.imagen ? `<img src="/imagenes/${prop.imagen}" alt="${prop.titulo}" style="max-width:200px;border-radius:5px;">`: 'N/A'}</p>
                            </div>
                        </div>        
                            `;
            
                    tabDiv.appendChild(div);
                });
            })
            .catch(e => console.error("Error:", e));
}

