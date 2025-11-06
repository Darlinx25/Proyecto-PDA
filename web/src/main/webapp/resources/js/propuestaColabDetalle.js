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
                
                const fecha = new Date(data.fechaHora);
                const dia = fecha.getDate().toString().padStart(2, '0');
                const mes = (fecha.getMonth() + 1).toString().padStart(2, '0');
                const anio = fecha.getFullYear();
                const horas = fecha.getHours().toString().padStart(2, '0');
                const minutos = fecha.getMinutes().toString().padStart(2, '0');
                const fechaFormateada = `${dia}/${mes}/${anio} ${horas}:${minutos}`;
                const estaPaga = data.pagada;

                
                container.innerHTML = `
                <p><strong>Monto colaborado:</strong> $${data.monto}</p>
                <p><strong>Fecha colaboracion:</strong> ${fechaFormateada}</p>
                ${estaPaga
                        ? `<button class="btn btn-success" onclick="emitirConstancia('${id}')">Emitir Constancia de Pago</button>`
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
                const fecha = new Date(data.fechaHora);
                const dia = fecha.getDate().toString().padStart(2, '0');
                const mes = (fecha.getMonth() + 1).toString().padStart(2, '0');
                const anio = fecha.getFullYear();
                const horas = fecha.getHours().toString().padStart(2, '0');
                const minutos = fecha.getMinutes().toString().padStart(2, '0');
                const fechaFormateada = `${dia}/${mes}/${anio} ${horas}:${minutos}`;
                
                const fechaPago = new Date(data.pago.fechaPago);
                const diaPago = fechaPago.getDate().toString().padStart(2, '0');
                const mesPago = (fechaPago.getMonth() + 1).toString().padStart(2, '0');
                const anioPago = fechaPago.getFullYear();
                const horasPago = fechaPago.getHours().toString().padStart(2, '0');
                const minutosPago = fechaPago.getMinutes().toString().padStart(2, '0');
                const fechaFormateadaPago = `${diaPago}/${mesPago}/${anioPago} ${horasPago}:${minutosPago}`;


                
                document.getElementById('modal-retorno').innerText = `${data.tipoRetorno}`;
                document.getElementById('modal-propColab').innerText = `${data.propuestaColaborada}`;
                document.getElementById('modal-monto').innerText = `$${data.monto}`;
                document.getElementById('modal-fecha').innerText = fechaFormateada;
                document.getElementById('modal-fechaPago').innerText = fechaFormateadaPago;
                document.getElementById('modal-metodoPago').innerText = data.pago.metodoPago;
                miModal.show();
            })
            .catch(err => {
                console.error("Error:", err);
                document.getElementById('modal-id-colab').value = "Error al cargar datos";
                miModal.show();
            });
}