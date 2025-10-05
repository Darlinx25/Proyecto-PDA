

function propPorEstado(btn) {
    const ESTADO = btn.getAttribute("data-estado");
    const tabId = btn.getAttribute("data-bs-target");

    fetch(`/obtener-propuesta-por-estado?estado=${encodeURIComponent(ESTADO)}`)
            .then(res => res.json())
            .then(data => {
                const tabDiv = document.querySelector(tabId);
                tabDiv.innerHTML = "";

                data.forEach(prop => {
                    const div = document.createElement("div");
                    div.className = "mb-2 p-2 border rounded bg-light";
                    div.innerHTML = `
                        <div class="d-flex align-items-center gap-5">
                            <div ">
                                <p><strong>Título:</strong> ${prop.titulo}</p>
                                <p><strong>Proponedor:</strong> ${prop.nickProponedor}</p>
                                <p>${prop.imagen ? `<img src="/imagenes/${prop.imagen}" alt="${prop.titulo}" style="max-width:300px;border-radius:5px;">`: 'N/A'}</p>
                                
                            </div>
                            <div>
                                <p><strong>Descripción:</strong> ${prop.descripcion}</p>
                                <p><strong>Precio entrada:</strong> ${prop.precioEntrada}</p>
                                <p><strong>Categoria:</strong> ${prop.categoria}</p>
                                <p><strong>Lugar:</strong> ${prop.lugarRealizara}</p>
                                <p><strong>Fecha Prevista:</strong> ${prop.fechaPrevista}</p>
                                <p><strong>Fecha de Publicacion:</strong> ${prop.fechaPublicacion}</p>
                                <p><strong>Precio de entrada:</strong> ${prop.precioEntrada}</p>
                                <p><strong>Monto a reunir:</strong> ${prop.montoAReunir}</p>
                                <p><strong>Tipos de retorno:</strong> ${prop.tiposRetorno}</p>
                                    
                            </div>
                        </div>        
                            `;
            
                    tabDiv.appendChild(div);
                });
            })
            .catch(e => console.error("Error:", e));
}

