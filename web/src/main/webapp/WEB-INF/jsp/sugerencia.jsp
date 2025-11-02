<%-- 
    Document   : sugerencia
    Created on : 1 nov. 2025, 7:53:47 p. m.
    Author     : alexi
--%>

<%@page import="culturarte.logica.Propuesta"%>
<%@page import="culturarte.logica.Usuario"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.*, culturarte.datatypes.DTUsuario" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sugerencias para ti</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/consultarPerfilUsuario.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <%
            List<Propuesta> Propuesta = (List<Propuesta>) request.getAttribute("propuestas");

        %>
        <div>
            <div>
                <h2>Sugerencias para ti:</h2>
                <div class="list-group">
                    <% if (Propuesta != null) {
                            for (Propuesta cat : Propuesta) {
                                String titulo = cat.getTitulo();
                                int puntaje = cat.getPuntaje();
                    %>
                    <a href="#" 
                       class="list-group-item list-group-item-action propuesta-link" 
                       data-titulo="<%= titulo%>">
                        <%= titulo + " - " + puntaje + " puntos"%>
                    </a>
                    <%}
                        }%>
                </div 
            </div>  
            <div class="bg-secondary p-2 rounded shadow-sm border border-5 border-dark mb-3 mt-3">
                <h3 class=" text-center">  Datos de propuesta seleccionada:  </h3>
                <div id="propuestaSeleccionada"></div>
            </div> 
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const links = document.querySelectorAll(".propuesta-link");
                const container = document.getElementById("propuestaSeleccionada");

                links.forEach(link => {
                    link.addEventListener("click", (event) => {
                        event.preventDefault();
                        const titulo = link.dataset.titulo;

                        container.innerHTML = "<p class='text-center text-muted'>Cargando información...</p>";
                        fetch("/obtener-propuesta?titulo=" + encodeURIComponent(titulo))
                                .then(resp => {
                                    if (!resp.ok)
                                        throw new Error("Error al obtener la propuesta");
                                    return resp.json();
                                })
                                .then(data => {
                                    container.innerHTML = `
                        <div class="card shadow-sm mt-3">
                            <div class="card-body">
                                <h4 class="card-title">${data.titulo}</h4>
                                <p class="card-text">${data.descripcion}</p>
                                <img src="/imagenes/${data.imagen}" 
                                     onerror="this.src='/resources/images/propdefault.png';"
                                     alt="${data.titulo}" 
                                     class="img-fluid rounded mb-3" 
                                     style="max-width:300px;">
                                <p><strong>Lugar de realización:</strong> ${data.lugarRealizara}</p>
                                <p><strong>Fecha prevista:</strong> ${data.fechaRealizara}</p>
                                <p><strong>Fecha de publicación:</strong> ${data.fechaPublicacion}</p>
                                <p><strong>Precio de entrada:</strong> $${data.precioEntrada}</p>
                                <p><strong>Monto a reunir:</strong> $${data.montoAReunir}</p>
                                <p><strong>Categoría:</strong> ${data.tipoPropuesta}</p>
                                <p><strong>Estado actual:</strong> ${data.estadoActual.estado}</p>
                                <p><strong>Tipos de retorno:</strong> ${data.tiposRetorno}</p>
                            </div>
                        </div>
                    `;
                                })
                                .catch(err => {
                                    console.error(err);
                                    container.innerHTML = `<p class="text-danger">Error al cargar la propuesta.</p>`;
                                });
                    });
                });
            });
        </script>
    </body>
</html>
