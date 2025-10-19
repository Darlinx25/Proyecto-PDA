window.addEventListener('load', () => {
    const header = document.querySelector('header');
    if (header) {
      document.body.style.paddingTop = header.offsetHeight + 'px';
    }
  });

const botonBusq = document.getElementById("boton-busq");
const inputBusq = document.getElementById("input-busq");

inputBusq.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
        event.preventDefault();
        botonBusq.click();
    }
});

botonBusq.addEventListener('click', function() {
    const path = window.location.pathname;
    const valorBusq = inputBusq.value;
    
    if (path !== "/index" && path !== "/" && path !== "") {
        window.location = "/";
    }
    const patronBusq = new RegExp(valorBusq, "i");
    const divsPropuestas = document.querySelectorAll('div[data-grupo="propuestas"]');
    
    if (valorBusq === "" || valorBusq === null) {
        const mensajeViejo = document.getElementById("mensajito");
        if (mensajeViejo) {
            mensajeViejo.remove();
        }
        const filtros = document.getElementById("filtros");
        filtros.style.display = "none";
        divsPropuestas.forEach(div => {
           div.style.display = "block"; 
        });
        return;
    }
    
    let cantResultados = 0;
    
    divsPropuestas.forEach(div => {
        const pTitulo = div.children[0].children[0].children[0];
        const pDesc = div.children[0].children[1].children[0];
        const pLugar = div.children[0].children[1].children[3];

        const titulo = getTexto(pTitulo);
        const descripcion = getTexto(pDesc);
        const lugar = getTexto(pLugar);

        const busqTitulo = patronBusq.test(titulo);
        const busqDesc = patronBusq.test(descripcion);
        const busqLugar = patronBusq.test(lugar);

        if (!(busqTitulo || busqDesc || busqLugar)) {
            div.style.display = "none";
        } else {
            const padre = div.parentElement;
            if (padre.classList.contains("active")) {
                div.style.display = "block";
                cantResultados++;
            }

        }

    });
    
    const filtros = document.getElementById("filtros");
    const selecFil = document.getElementById("select-filtros");
    selecFil.options[0].selected = true;
    
    const mensajeViejo = document.getElementById("mensajito");
    if (mensajeViejo) {
        mensajeViejo.remove();
    }
    const mensajeResult = document.createElement("p");
    mensajeResult.id = "mensajito";
    mensajeResult.innerHTML = cantResultados + ' resultados para <strong>"' + valorBusq + '"</strong>';
    filtros.insertBefore(mensajeResult, document.getElementById("label-ordenar"));
    filtros.style.display = "block";
});

function getTexto(elementoP) {
    for (let nodo of elementoP.childNodes) {
        if (nodo.nodeType === Node.TEXT_NODE) {
            return nodo.nodeValue.trim();
        }
    }
    
    return "";
}
