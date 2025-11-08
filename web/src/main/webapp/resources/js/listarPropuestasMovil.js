function propPorEstado(btn) {


    const catSeleccionada = document.querySelector('input[name="optradio"]:checked');
    const categoriaSelec = 'Todas';
    const tabId = btn.getAttribute("data-bs-target");
    const tabDiv = document.querySelector(tabId);
    tabDiv.innerHTML = "";
    

    for (let ESTADO = 1; ESTADO <= 5; ESTADO++) {
        fetch(`/obtener-propuesta-por-estado?estado=${encodeURIComponent(ESTADO)}&categoriaSelec=${encodeURIComponent(categoriaSelec)}`)
                .then(res => res.json())
                .then(data => {


                    const plantillaFav = document.getElementById("add-favorito");
                    const plantillaColab = document.getElementById("registrar-colab");
                    const plantillaExtend = document.getElementById("extender-prop");
                    const plantillaCancelar = document.getElementById("cancelar-prop");
                    const plantillaComentar = document.getElementById("comentar-prop");

                    data[0].forEach(prop => {
                        const div = document.createElement("div");
                        div.className = "mb-2 p-2 border rounded bg-light";
                        div.setAttribute("data-grupo", "propuestas");
                        const fechahoy = new Date();
                        const fechaFinanciacion = new Date(prop.plazoFinanciacion);
                        const diferenciaDias = Math.ceil((fechaFinanciacion - fechahoy) / (1000 * 3600 * 24));
                        div.innerHTML = `
                    <div class="d-flex align-items-center gap-5">
                        <div>
                            <p><strong>Título:</strong> ${prop.titulo}</p>
                            
                            <p>
                        <a href="/consultar-propuesta-movil?titulo=${encodeURIComponent(prop.titulo)}">
                        <img src="/imagenes/${prop.imagen}" 
                        onerror="this.src='/resources/images/propdefault.png';"
                        alt="${prop.titulo}" 
                        style="max-width:300px;border-radius:5px;cursor:pointer;">
                        </a>
                        </p>  
                        </div>
                    </div>`;
                        tabDiv.appendChild(div);
                    });
                })
                .catch(e => console.error("Error:", e));
    }
}


document.querySelectorAll('input[name="optradio"]').forEach(radio => { //Puse esto para q se recarge laa tab del estado al clikiear una categoria
    radio.addEventListener('change', () => {
        const tabActivo = document.querySelector('.nav-tabs .nav-link.active');
        if (tabActivo) {
            propPorEstado(tabActivo);
        }
    });
});