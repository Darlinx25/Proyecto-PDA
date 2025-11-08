function propuestaElegida() {
    const select = document.getElementById("propuesta");
    if (select.value && select.value !== "") {
        let valor = select.value;
        const indiceGuion = valor.indexOf(" - ");
        if (indiceGuion !== -1) {
            valor = valor.substring(0, indiceGuion);
        }

        fetch(`/obtener-propuesta?titulo=${encodeURIComponent(valor)}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Error al obtener la propuesta");
                    }
                    return response.json();
                })
                .then(prop => {
                    const div = document.getElementById("contenedor-propuesta");
                    if (!div) {
                        console.error("No se encontró el contenedor contenedor-propuesta");
                        return;
                    }

                    div.innerHTML = "";


                    let diferenciaDias = "";
                    if (prop.plazoFinanciacion) {
                        const fechaFinanciacion = new Date(prop.plazoFinanciacion);
                        const hoy = new Date();
                        diferenciaDias = Math.ceil((fechaFinanciacion - hoy) / (1000 * 3600 * 24));
                    }

                    const retornoDiv = document.createElement("div");
                    retornoDiv.className = "mt-3 mb-2";
                    retornoDiv.innerHTML = `
                    <div class="d-flex align-items-center gap-5 flex-wrap">
                        <div>
                            <p><strong>Título:</strong> ${prop.titulo}</p>
                            <p><strong>Proponedor:</strong> ${prop.nickProponedor}</p>
                            <p><img src="/imagenes/${prop.imagen}" 
                                    onerror="this.src='/resources/images/propdefault.png';" 
                                    alt="${prop.titulo}" 
                                    style="max-width:300px;border-radius:5px;"></p>
                            <p class="text-center"><strong>Dinero recaudado:</strong></p>
                            <div class="progress" style="height: 25px;">
                                <div class="progress-bar" 
                                     style="width: ${(prop.dineroRecaudado / prop.montoAReunir) * 100}%">
                                     $${prop.dineroRecaudado}
                                </div>
                            </div>
                        </div>
                        <div>
                            <p><strong>Descripción:</strong> ${prop.descripcion}</p>
                            <p><strong>Precio entrada:</strong> $${prop.precioEntrada}</p>
                            <p><strong>Categoria:</strong> ${prop.tipoPropuesta}</p>
                            <p><strong>Lugar de realización:</strong> ${prop.lugarRealizara}</p>
                            <p><strong>Fecha Prevista:</strong> ${prop.fechaRealizara}</p>
                            <p><strong>Fecha de Publicación:</strong> ${prop.fechaPublicacion}</p>
                            <p><strong>Tipos de retorno:</strong> ${prop.tiposRetorno}</p>
                            <p><strong>Monto a reunir: </strong> $${prop.montoAReunir}</p>
                            <p><strong>Días restantes para Financiación: </strong> ${diferenciaDias}</p>
                        </div>
                    </div>
                `;
                    div.appendChild(retornoDiv);

                    // Listado de colaboradores
                    if (Array.isArray(prop.nicksColabs) && prop.nicksColabs.length > 0) {
                        const divColabs = document.createElement("form");
                        divColabs.className = "p-3";
                        divColabs.innerHTML = `
                        <select class="form-select form-select-sm mb-3">
                            <option selected>--Colaboradores--</option>
                        </select>`;
                        const selectColabs = divColabs.querySelector("select");

                        for (const i of prop.nicksColabs) {
                            const opcion = document.createElement("option");
                            opcion.textContent = i;
                            opcion.disabled = true;
                            selectColabs.appendChild(opcion);
                        }

                        div.appendChild(divColabs);
                    }

                    //colaborar

                    const yaColaboro = propuestasColabUsuario.includes(prop.titulo);
                    const puedeColaborar = propuestasPuedeColab.includes(prop.titulo);

                    const formColab = document.createElement("form");
                    formColab.className = "mt-3";

                    if (yaColaboro) {
                        formColab.innerHTML = `
                    <button type="button" class="btn btn-secondary mb-3" disabled>
                        Ya colaboraste
                    </button>`;
                    } else if(puedeColaborar){
                        formColab.method = "get";
                        formColab.action = "/registrar-colaboracion";
                        formColab.innerHTML = `
                    <input type="hidden" name="propuesta" value="${prop.titulo}">
                    <button type="submit" class="btn btn-success mb-3">
                        Colaborar
                    </button>`;
                    }else {
                        formColab.innerHTML = `
                    <button type="button" class="btn btn-secondary mb-3" disabled>
                        No puede colaborar
                    </button>`;
                    }

                    div.appendChild(formColab);

                })
                .catch(error => console.error("Error:", error));
    }
}
