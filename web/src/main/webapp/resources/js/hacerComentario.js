/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function propuestaElegida() {
    const select = document.getElementById("propuesta");

    if (select.value && select.value !== "") {
        let valor = select.value.trim();
        
        console.log(valor);
        fetch(`/obtener-propuesta?titulo=${encodeURIComponent(valor)}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Error al obtener la propuesta");
                    }
                    return response.json();
                })
                .then(data => {


                    document.getElementById("retornosContainer").innerHTML = "";
                    if (Array.isArray(data.tiposRetorno) && data.tiposRetorno.length > 0) {

                        const retornoDiv = document.createElement("div");
                        retornoDiv.className = "mt-3 mb-2";


                        retornoDiv.innerHTML = `
                    <p><strong>Título:</strong> ${data.titulo}</p>
                    <p><strong>Descripción:</strong> ${data.descripcion}</p>
                    <p><strong>Imagen:</strong> <img src="/imagenes/${data.imagen}" onerror="this.src='/resources/images/propdefault.png';" alt="${data.titulo}" style="max-width:100%;border-radius:5px;"></p>
                    <p><strong>Lugar de realización:</strong> ${data.lugarRealizara}</p>
                    <p><strong>Fecha prevista:</strong> ${data.fechaRealizara}</p>
                    <p><strong>Fecha publicación:</strong> ${data.fechaPublicacion}</p>
                    <p><strong>Precio entrada:</strong> $${data.precioEntrada}</p>
                    <p><strong>Monto a reunir:</strong> $${data.montoAReunir}</p>
                    <p><strong>Categoría:</strong> ${data.tipoPropuesta}</p>
                    <p><strong>Propuesta de:</strong> ${data.nickProponedor}</p>
                    <div class="form-floating">
                    <textarea class="form-control" id="comment" name="comentario" placeholder="Escriba su comentario aqui" required></textarea>
                    <label for="comment">Comentario</label>
                    </div>
                    </div>
                    `;



                    document.getElementById("retornosContainer").appendChild(retornoDiv);
                    }
                })
                .catch(error => console.error("Error:", error));
    }
}
