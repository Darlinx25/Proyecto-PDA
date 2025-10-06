

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

                    document.getElementById("infoPropuesta")?.remove();
                    document.getElementById("retornosContainer").innerHTML = "";

                    const infoDiv = document.createElement("div");
                    infoDiv.id = "infoPropuesta";
                    infoDiv.className = "mt-3 p-2 border rounded bg-light w-100";

                    document.getElementById("formulario").appendChild(infoDiv);
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
                    <p><strong>Tipos de retorno:</strong> ${data.tiposRetorno.join(", ")}</p>
                        <label class="form-label">Tipo de retorno:</label>
                        <div class="form-check-group mb-2">
                            ${data.tiposRetorno.includes("ENTRADA_GRATIS") ? `
                                <div class="form-check">
                                    <input name="tipoRetorno" value="entradaGratis" type="checkbox" class="form-check-input me-2" id="tipo-retorno-check1">
                                    <label class="form-check-label" for="tipo-retorno-check1">Entrada gratis</label>
                                </div>` : ""}
                            ${data.tiposRetorno.includes("PORCENTAJE_GANANCIAS") ? `
                                <div class="form-check">
                                    <input name="tipoRetorno" value="porcentajeGanancia" type="checkbox" class="form-check-input me-2" id="tipo-retorno-check2">
                                    <label class="form-check-label" for="tipo-retorno-check2">Porcentaje de ganancia</label>
                                </div>` : ""}
                        </div>
                        <p class="text-center mb-0 text-danger d-none" id="mensaje-error">
                            Debe seleccionar al menos un tipo de retorno
                        </p>
                    `;


                        document.getElementById("retornosContainer").appendChild(retornoDiv);
                    }
                })
                .catch(error => console.error("Error:", error));
    }
}








