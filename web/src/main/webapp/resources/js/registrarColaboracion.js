

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
                .then(data => {


                    document.getElementById("retornosContainer").innerHTML = "";
                    if (Array.isArray(data.tiposRetorno) && data.tiposRetorno.length > 0) {

                        const retornoDiv = document.createElement("div");
                        retornoDiv.className = "mt-3 mb-2";


                        retornoDiv.innerHTML = `
                    <p><strong>Título:</strong> ${data.titulo}</p>
                    <p><strong>Descripción:</strong> ${data.descripcion}</p>
                    <p><strong>Imagen:</strong> ${data.imagen ? '<img src="/imagenes/' + data.imagen + '" alt="' + data.titulo + '" style="max-width:100%;border-radius:5px;">' : 'N/A'}</p>
                    <p><strong>Lugar de realización:</strong> ${data.lugarRealizara}</p>
                    <p><strong>Fecha prevista:</strong> ${data.fechaPrevista}</p>
                    <p><strong>Fecha publicación:</strong> ${data.fechaPublicacion}</p>
                    <p><strong>Precio entrada:</strong> $${data.precioEntrada}</p>
                    <p><strong>Monto a reunir:</strong> $${data.montoAReunir}</p>
                    <p><strong>Categoría:</strong> ${data.categoria}</p>
                    <p><strong>Propuesta de:</strong> ${data.nickProponedor}</p>
                    <p><strong>Estado actual:</strong> ${data.estadoActual}</p> 
                    <div class="mb-3">
                    <label for="opcion" class="form-label"><strong>Tipo de retorno:</strong></label>
                    <select class="form-select form-select-m" id="opcion" name="opcion" required>
                    <option value="" selected disabled>-- Elege un tipo de retorno --</option>
                    </select>
                    </div>
                    <div class="mb-2">
                    <label for="colaboracion" class="form-label"><strong>Colaboracion a realizar</strong></label>
                    <div class="input-group input-group-sm">
                    <span class="input-group-text">$</span>
                    <input type="text" id="colaboracion" name="colaboracion" class="form-control" required>
                    <input type="hidden" name="tituloProp" value="${data.titulo}">
                    </div>
                    </div>
                    `;



                        document.getElementById("retornosContainer").appendChild(retornoDiv);
                        const select = retornoDiv.querySelector("#opcion");
                        if (Array.isArray(data.tiposRetorno)) {
                            data.tiposRetorno.forEach(retorno => {
                                const option = document.createElement("option");
                                option.value = retorno;
                                option.textContent = retorno;
                                select.appendChild(option);
                            });
                        }
                    }
                })
                .catch(error => console.error("Error:", error));
    }
}



