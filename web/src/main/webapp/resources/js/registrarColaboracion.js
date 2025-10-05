/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


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

                    const infoDiv = document.createElement("div");
                    infoDiv.id = "infoPropuesta";
                    infoDiv.className = "mt-3 p-2 border rounded bg-light w-100";

                    infoDiv.innerHTML = `
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
                `;
                    
                    document.getElementById("formulario").appendChild(infoDiv);
                })
                .catch(error => console.error("Error:", error));
    }
}








