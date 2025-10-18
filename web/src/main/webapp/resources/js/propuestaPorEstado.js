function propPorEstado(btn) {
    const ESTADO = btn.getAttribute("data-estado");

    const catSeleccionada = document.querySelector('input[name="optradio"]:checked');
    const categoriaSelec = catSeleccionada ? catSeleccionada.value : 'Todas';
    const tabId = btn.getAttribute("data-bs-target");

    fetch(`/obtener-propuesta-por-estado?estado=${encodeURIComponent(ESTADO)}&categoriaSelec=${encodeURIComponent(categoriaSelec)}`)
        .then(res => res.json())
        .then(data => {
            const tabDiv = document.querySelector(tabId);
            tabDiv.innerHTML = "";
            
            const plantillaFav = document.getElementById("add-favorito");
            
            data[0].forEach(prop => {
                const div = document.createElement("div");
                div.className = "mb-2 p-2 border rounded bg-light";

                const fechahoy = new Date();
                const fechaFinanciacion = new Date(prop.plazoFinanciacion);
                const diferenciaDias = Math.ceil((fechaFinanciacion - fechahoy) / (1000 * 3600 * 24));

                div.innerHTML = `
                    <div class="d-flex align-items-center gap-5">
                        <div>
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
                            <p><strong>Precio entrada:</strong> $${prop.precioEntrada}</p>
                            <p><strong>Categoria:</strong> ${prop.tipoPropuesta}</p>
                            <p><strong>Lugar de realización:</strong> ${prop.lugarRealizara}</p>
                            <p><strong>Fecha Prevista:</strong> ${prop.fechaRealizara}</p>
                            <p><strong>Fecha de Publicacion:</strong> ${prop.fechaPublicacion}</p>
                            <p><strong>Tipos de retorno:</strong> ${prop.tiposRetorno}</p>
                            <p><strong>Monto a reunir: </strong> $${prop.montoAReunir}</p>
                            <p><strong>Dias restantes para Financiación: </strong> ${diferenciaDias}</p>
                        </div>
                    </div>`;
                const estaEnFav = data[1].includes(prop.titulo);
                if (plantillaFav !== null) {
                    if (!estaEnFav) {
                        const clonFav = plantillaFav.cloneNode(true);
                        clonFav.removeAttribute("id");
                        clonFav.onsubmit = function(event) {
                            const botonFav = this.children[1];
                            botonFav.type = "button";
                            botonFav.textContent = "Ya en favoritos";
                            botonFav.className = "btn btn-secondary mb-3";
                            event.preventDefault();
                            const formData = new FormData(event.currentTarget);
                            fetch(event.currentTarget.action, {
                                method: event.currentTarget.method,
                                body: formData
                            });
                        };
                        const hInput = clonFav.children[0];
                        hInput.value = prop.titulo;
                        const botonFav = clonFav.children[1];
                        clonFav.style.display = "block";
                        div.appendChild(clonFav);
                    } else {
                        const clonFav = plantillaFav.cloneNode(true);
                        clonFav.removeAttribute("id");
                        clonFav.removeAttribute("action");
                        clonFav.removeAttribute("method");
                        const hInput = clonFav.children[0];
                        const botonFav = clonFav.children[1];
                        hInput.remove();
                        botonFav.type = "button";
                        botonFav.textContent = "Ya en favoritos";
                        botonFav.className = "btn btn-secondary mb-3";
                        clonFav.style.display = "block";
                        div.appendChild(clonFav);
                    }
                    
                }
                tabDiv.appendChild(div);
            });
        })
        .catch(e => console.error("Error:", e));
}


document.querySelectorAll('input[name="optradio"]').forEach(radio => { //Puse esto para q se recarge laa tab del estado al clikiear una categoria
    radio.addEventListener('change', () => {
        const tabActivo = document.querySelector('.nav-tabs .nav-link.active');
        if (tabActivo) {
            propPorEstado(tabActivo);
        }
    });
});