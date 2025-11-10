function cargarPropuestaColab() {
    const select = document.getElementById("propuestaColaboradas");
    const valor = select.value;

    if (!valor)
        return;

    const titulo = valor.split(" - ")[0];
    const container = document.getElementById("contenedorPropuestaColaboradas");
    console.log(titulo);
    fetch(`/obtener-propuesta?titulo=${encodeURIComponent(titulo)}`)
            .then(resp => {
                if (!resp.ok)
                    throw new Error("Error al obtener la propuesta");
                return resp.json();
            })
            .then(data => {
                container.innerHTML = `
                <p><strong>Título:</strong> ${data.titulo}</p>
                <p><strong>Descripción:</strong> ${data.descripcion}</p>
                <img src="/imagenes/${data.imagen}" 
                     onerror="this.src='/resources/images/propdefault.png';"
                     alt="${data.titulo}" 
                     style="max-width:300px;border-radius:6px;">
                <p><strong></strong></p>     
                <p><strong>Lugar de realización:</strong> ${data.lugarRealizara}</p>
                <p><strong>Fecha prevista:</strong> ${data.fechaRealizara}</p>
                <p><strong>Fecha de publicación:</strong> ${data.fechaPublicacion}</p>
                <p><strong>Precio de entrada:</strong> $${data.precioEntrada}</p>
                <p><strong>Monto a reunir:</strong> $${data.montoAReunir}</p>
                <p><strong>Categoría:</strong> ${data.tipoPropuesta}</p>
                <p><strong>Estado actual:</strong> ${data.estadoActual.estado}</p>
                <p><strong>Tipos de retorno:</strong> ${data.tiposRetorno}</p>
            `;

            })
            .catch(err => console.error("Error:", err));
}


function cargarColabPropia() {
    const select = document.getElementById("propuestaColaboradas");
    const valor = select.value;
    
    if (!valor)
        return;
    const id = valor.split(" - ")[1];
    console.log(id);
    const container = document.getElementById("contenedorPropuestaColaboradasPropias");

    fetch(`/obtener-colaboracion?id=${encodeURIComponent(id)}`)
            .then(resp => {
                if (!resp.ok)
                    throw new Error("Error al obtener la colaboracion");
                return resp.json();
            })
            .then(data => {

                const estaPaga = data.pagada;
                console.log(data.pagada + "DATA DEL PAGO ACA");
                const seEmitioConsancia = data.constanciaEmitida;
                console.log(estaPaga + "SE PAGO???");
                console.log(seEmitioConsancia + "SE EMITIOCONSTANCIA???");
                container.innerHTML = `
                <p><strong>Monto colaborado:</strong> $${data.monto}</p>
                <p><strong>Fecha colaboracion:</strong> ${formatDateTimeShort(data.fechaHora)}</p>
                
                ${data.pagada
                    ? (seEmitioConsancia
                        ? `<button class="btn btn-secondary" disabled>Constancia Emitida</button>`
                        : `<button id="btn-constancia-${id}" class="btn btn-success" onclick="emitirConstancia('${id}')">Emitir Constancia de Pago</button>`)
                    : '<p><em>(Colaboración aún no pagada)</em></p>'
                }
            `;

            })
            .catch(err => console.error("Error:", err));
}


function emitirConstancia(idColaboracion){
    
    if (!idColaboracion)
        return;

    const modalElemento = document.getElementById('modalConstancia');
    const miModal = new bootstrap.Modal(modalElemento);

    fetch(`/obtener-colaboracion?id=${encodeURIComponent(idColaboracion)}`)
            .then(resp => {
                if (!resp.ok)
                    throw new Error("Error al obtener la colaboracion");
                return resp.json();
            })
            .then(data => {

                document.getElementById('modal-retorno').innerText = `${data.tipoRetorno}`;
                document.getElementById('modal-propColab').innerText = `${data.propuestaColaborada}`;
                document.getElementById('modal-monto').innerText = `$${data.monto}`;
                document.getElementById('modal-fecha').innerText = formatDateTimeShort(data.fechaHora);
                document.getElementById('modal-fechaPago').innerText = formatDateTimeShort(data.pago.fechaPago);
                document.getElementById('modal-metodoPago').innerText = data.pago.metodoPago;
                miModal.show();
            })
            .catch(err => {
                console.error("Error:", err);
                document.getElementById('modal-id-colab').value = "Error al cargar datos";
                miModal.show();
            });
            
          

            fetch(`/marcar-constancia-emitida?idColaboracion=${encodeURIComponent(idColaboracion)}`, {
                method: 'POST'
            })
            .then(respMarcado => {
                    const btn = document.getElementById(`btn-constancia-${idColaboracion}`);
                    if (btn) {
                        btn.disabled = true;
                        btn.innerText = "Constancia Emitida";
                        btn.classList.remove('btn-success');
                        btn.classList.add('btn-secondary');
                    }
                
            })
            .catch(errMarcar => console.error("Error en fetch de marcado:", errMarcar));
            
            
            
            
}

function formatDateTimeShort(isoString) {
  const dateObj = new Date(isoString);
  
  return dateObj.toLocaleString('es-ES', { 
    year: 'numeric', 
    month: '2-digit', 
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false // Usa formato de 24 horas
  });
}