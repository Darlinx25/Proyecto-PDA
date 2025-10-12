

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
                    const fechahoy = new Date();
                    const fechaFinanciacion = new Date(prop.plazoFinanciacion);
                    const diferenciaDias = Math.ceil((fechaFinanciacion - fechahoy) / (1000 * 3600 * 24)); //  el calculo matematico es para transformarlo en dias
                    div.innerHTML = `
                        <div class="d-flex align-items-center gap-5">
                            <div ">
                                <p><strong>Título:</strong> ${prop.titulo}</p>
                                <p><strong>Proponedor:</strong> ${prop.nickProponedor}</p>
                                <p><img src="/imagenes/${prop.imagen}" onerror="this.src='/resources/images/propdefault.png';" alt="${prop.titulo}" style="max-width:300px;border-radius:5px;"></p>
                                <p class="text-center"><strong>Dinero recaudado:</strong></p>
                                <div class="progress" style="height: 25px;">
                                    <div class="progress-bar" style="width: ${(prop.dineroRecaudado / prop.montoAReunir) * 100}%">$${prop.dineroRecaudado}</div>
                                </div>
                            </div>
                            <div>
                                <p><strong>Descripción:</strong> ${prop.descripcion}</p>
                                <p><strong>Precio entrada:</strong> ${prop.precioEntrada}</p>
                                <p><strong>Categoria:</strong> ${prop.tipoPropuesta}</p>
                                <p><strong>Lugar de realización:</strong> ${prop.lugarRealizara}</p>
                                <p><strong>Fecha Prevista:</strong> ${prop.fechaRealizara}</p>
                                <p><strong>Fecha de Publicacion:</strong> ${prop.fechaPublicacion}</p>
                                <p><strong>Precio de entrada:</strong> $${prop.precioEntrada}</p>
                                <p><strong>Tipos de retorno:</strong> ${prop.tiposRetorno}</p>
                                <p><strong>Monto a reunir: </strong> $${prop.montoAReunir}</p>
                                <p><strong>Dias restantes para Financiación: </strong> ${diferenciaDias}</p>
                            </div>
                        </div>        
                            `;
            
                    tabDiv.appendChild(div);
                });
            })
            .catch(e => console.error("Error:", e));
}

