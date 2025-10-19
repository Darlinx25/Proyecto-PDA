function filtrarPropuestas() {
    const select = document.getElementById("select-filtros");
    
    
    const divsPropuestas = document.querySelectorAll('div[data-grupo="propuestas"]');
    
    if (select.value === "alfabet") {
        const listaTitulos = [];

        divsPropuestas.forEach(div => {
            const pTitulo = div.children[0].children[0].children[0];

            const titulo = getTexto(pTitulo);
            listaTitulos.push(titulo);

        });
        listaTitulos.sort();
        
        for (let t of listaTitulos) {
            for (let d of divsPropuestas) {
                const pTitulo = d.children[0].children[0].children[0];

                const titulo = getTexto(pTitulo);
                if (titulo === t) {
                    const padre = d.parentElement;
                    padre.appendChild(d);
                }
            }
        }
        
        
    } else if (select.value === "fecha") {
        const listaFechas= [];

        divsPropuestas.forEach(div => {
            const pFecha = div.children[0].children[1].children[5];

            const fecha = getTexto(pFecha);
            listaFechas.push(fecha);

        });
        //descendente
        listaFechas.sort((fecha1, fecha2) => new Date(fecha2) - new Date(fecha1));;
        
        for (let f of listaFechas) {
            for (let d of divsPropuestas) {
                const pFecha = d.children[0].children[1].children[5];

                const fecha = getTexto(pFecha);
                if (fecha === f) {
                    const padre = d.parentElement;
                    padre.appendChild(d);
                }
            }
        }
    }
}


function getTexto(elementoP) {
    for (let nodo of elementoP.childNodes) {
        if (nodo.nodeType === Node.TEXT_NODE) {
            return nodo.nodeValue.trim();
        }
    }
    
    return "";
}
