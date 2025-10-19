<%-- 
    Document   : hacerComentario
    Created on : 12 oct. 2025, 8:22:24 p. m.
    Author     : alexi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hacer comentario</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/registrarColaboracion.css" rel="stylesheet">
    </head>
    <body class="bg-light d-flex justify-content-center align-items-center min-vh-100 py-2" id="cuerpo" 
          onload="propuestaElegida(document.getElementById('propuesta').value)">
        <jsp:include page="header.jsp"/>
        
        <form action="/hacer-comentario" method="post" class="card p-5 shadow" style="margin-top: 120px;" id="formulario" >

            <h2 class="text-center mb-4">Comentar:</h2>

            <%
                
                String propuesta = (String) request.getAttribute("propuesta");
            %>

            <div class="mb-2">
                <label for="propuesta" class="form-label"><strong>Propuestas</strong></label>
                <select onchange="propuestaElegida()" id="propuesta" name="propuesta" class="form-select form-select-sm" >
                    <option value=" <%= propuesta%>"><%= propuesta%> </option>
                    
                </select>
                
                    
            </div>
                    
            <div id="retornosContainer"></div>



            <button type="submit" class="btn btn-success w-100 mb-3">Agregar comentario</button>

        </form>

        <script src="${pageContext.request.contextPath}/resources/js/hacerComentario.js"></script>

    </body>
</html>