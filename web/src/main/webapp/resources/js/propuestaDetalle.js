function cargarPropuesta() {
    const select = document.getElementById("propuestaSeguidos");
    const valor = select.value;

    if (!valor) return;

    const titulo = valor.split(" - ")[0];
    const container = document.getElementById("contenedorPropuestaSeguidos");

    fetch(`/obtener-propuesta?titulo=${encodeURIComponent(titulo)}`)
        .then(resp => {
            if (!resp.ok) throw new Error("Error al obtener la propuesta");
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
