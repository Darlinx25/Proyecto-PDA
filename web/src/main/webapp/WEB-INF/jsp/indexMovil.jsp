<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Culturarte</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/index.css" rel="stylesheet">

    </head>
    <body class="bg-light" onload="propPorEstado(document.querySelector('.nav-tabs .nav-link.active'))"> 
        <jsp:include page="headerMovil.jsp"/>
        
        <form class="p-3" id="filtros">
            <label for="combo-filtros" id="label-ordenar" class="form-label">Ordenar por:</label>
            <select oninput="filtrarPropuestas()" class="form-select form-select-sm mb-3" id="select-filtros">
                <option selected >-</option>
                <option value="alfabet">Alfabéticamente AZ-az</option>
                <option value="fecha">Fecha creación (descendente)</option>
            </select>
        </form>
        
        <%
            String rol = (String) session.getAttribute("rol");
            String nombre = (String) session.getAttribute("nombre");
            String apellido = (String) session.getAttribute("apellido");
            String imagen = (String) session.getAttribute("ubiImagen");
            String usr = (String) session.getAttribute("username");
            List<String> categorias = (List<String>) request.getAttribute("categorias");


        %>
        


        <div class="d-flex justify-content-between gap-3">
            
            <div class="container" style="display: none;" >
                <ul class="nav nav-tabs">
                    <li><button class="nav-link active" data-bs-toggle="tab" data-bs-target="#PCreadas" data-estado="1" onclick="propPorEstado(this),">Propuestas Creadas</button></li>
                    <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PFinanciacion" data-estado="2" onclick="propPorEstado(this)">Propuestas en Financiación</button></li>
                    <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PFinanciadas" data-estado="3" onclick="propPorEstado(this)">Propuestas Financiadas</button></li>
                    <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PNOFinanciadas" data-estado="4" onclick="propPorEstado(this)">Propuestas NO Financiadas</button></li>
                    <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PCanceladas" data-estado="5" onclick="propPorEstado(this)">Propuestas Canceladas</button></li>
                </ul>
               
            </div>
            <div class="tab-content">
                    <div class="tab-pane fade show active" id="PCreadas"></div>
                    <div class="tab-pane fade" id="PFinanciacion"></div>
                    <div class="tab-pane fade" id="PFinanciadas"></div>
                    <div class="tab-pane fade" id="PNOFinanciadas"></div>
                    <div class="tab-pane fade" id="PCanceladas"></div>
                </div>
        </div>    
        <script src="${pageContext.request.contextPath}/resources/js/listarPropuestasMovil.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/filtrarPropuestas.js"></script>
        
    </body>
</html>
