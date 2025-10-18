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
                const plantillaColab = document.getElementById("registrar-colab");
                const plantillaExtend = document.getElementById("extender-prop");
                const plantillaCancelar = document.getElementById("cancelar-prop");
                const plantillaComentar = document.getElementById("comentar-prop");

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
                //Favoritas
                const estaEnFav = data[1].includes(prop.titulo);
                if (plantillaFav !== null) {
                if (!estaEnFav) {
                const clonFav = plantillaFav.cloneNode(true);
                        clonFav.removeAttribute("id");
                        clonFav.onsubmit = function (event) {
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
                //Colaborar
                }
                const yaColaboro = data[2].includes(prop.titulo);
                const sePuedeColaborar = data[3].includes(prop.titulo);
                if (plantillaColab !== null) {
                    const clonColab = plantillaColab.cloneNode(true);
                    clonColab.removeAttribute("id");
                    clonColab.style.display = "block";
                    const hInput = clonColab.querySelector("input");
                    const botonColab = clonColab.querySelector("button");
                    if (!yaColaboro && sePuedeColaborar) {
                        hInput.value = prop.titulo;
                        clonColab.method = "get";
                        clonColab.action = "/registrar-colaboracion";
                    } else if (yaColaboro){
                        hInput.remove();
                        botonColab.type = "button";
                        botonColab.textContent = "Ya colaborada";
                        botonColab.className = "btn btn-secondary mb-3";
                    } else{
                        botonColab.remove();
                        if (hInput){
                            hInput.remove();
                        }
                    }       
                div.appendChild(clonColab);
                }
                
                
                //Extender financiacion 
                const esPropia = data[4].includes(prop.titulo);
                        const sePuedeExtender = data[3].includes(prop.titulo);
                        if (plantillaExtend !== null) {
                const clonExtender = plantillaExtend.cloneNode(true);
                        clonExtender.removeAttribute("id");
                        clonExtender.style.display = "block";
                        const hInput = clonExtender.querySelector("input");
                        const botonExt = clonExtender.querySelector("button");
                        if (esPropia && sePuedeExtender) {
                hInput.value = prop.titulo;
                        clonExtender.method = "post";
                        clonExtender.action = "/extender-financiacion";
                } else{
                botonExt.remove();
                        if (hInput){
                hInput.remove();
                }
                }
                div.appendChild(clonExtender);
                }



                //Cancelar prop
                const sePuedeCancelar = data[5].includes(prop.titulo);
                if (plantillaCancelar !== null) {
                    const clonCancelar = plantillaCancelar.cloneNode(true);
                    clonCancelar.removeAttribute("id");
                    clonCancelar.style.display = "block";
                    const hInput = clonCancelar.querySelector("input");
                    const botonCancelar = clonCancelar.querySelector("button");
                    if (esPropia && sePuedeCancelar) {
                            hInput.value = prop.titulo;
                            clonCancelar.method = "post";
                            clonCancelar.action = "/cancelar-propuesta";
                    } else{
                            botonCancelar.remove();
                            if (hInput) {
                                hInput.remove();
                            }
                    }   
                    div.appendChild(clonCancelar);
                }
                
                //Comentar
                const sePuedeComentar = data[6].includes(prop.titulo);
                const seComento = data[7].includes(prop.titulo);
                if (plantillaComentar !== null) {
                    const clonComent = plantillaComentar.cloneNode(true);
                    clonComent.removeAttribute("id");
                    clonComent.style.display = "block";
                    const hInput = clonComent.querySelector("input");
                    const botonComentar = clonComent.querySelector("button");
                    if (sePuedeComentar) {
                        hInput.value = prop.titulo;
                        clonComent.method = "get";
                        clonComent.action = "/hacer-comentario";
                    }else if(seComento){
                        hInput.remove();
                        botonComentar.type = "button";
                        botonComentar.textContent = "Ya comentada";
                        botonComentar.className = "btn btn-secondary mb-3";
                    }else{
                        botonComentar.remove();
                        if (hInput){
                            hInput.remove();
                        }
                    }
                div.appendChild(clonComent);
                }
                    //para q no queden todos los botones amontonados
                    const contenedorBotones = document.createElement("div");
                    contenedorBotones.style.display = "flex";
                    contenedorBotones.style.flexWrap = "wrap";
                    contenedorBotones.style.gap = "8px";
                    div.querySelectorAll("form").forEach(form => {
                        contenedorBotones.appendChild(form);
                    });
                    div.appendChild(contenedorBotones);
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