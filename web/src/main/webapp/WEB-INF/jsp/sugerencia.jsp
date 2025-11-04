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
            List<String> Propuesta = (List<String>) request.getAttribute("propuestas");

        %>
        <div>
            <div>
                <h2>Sugerencias para ti:</h2>
            <div class="list-group">
                <% if (Propuesta != null) {
                    for (String cat : Propuesta) {%>
                
                <button type="button" 
                        class="list-group-item list-group-item-action" 
                        onclick="cargarPropuestaSugerida('<%= cat %>')">
                    <%= cat %>
                </button>
                
                <%}
                }%>
            </div>  
            <div class="bg-white p-2 rounded shadow-sm border border-5 border-dark mb-3 mt-3">
                <h3 class=" text-center">  Datos de propuesta seleccionada:  </h3>
                <div id="contenedorPropuestaColaboradas"></div>
            </div>
            </div>
            <script src="${pageContext.request.contextPath}/resources/js/sugerencia.js"></script>
    </body>
</html>
