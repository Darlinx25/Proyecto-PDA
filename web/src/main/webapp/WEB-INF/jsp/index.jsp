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
        <jsp:include page="header.jsp"/>
        
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
        <%            if (rol == null) {
        %>


        <%
        } else {
        %>
        <form action="/marcar-propuesta-favorita" class="p-3" method="post" style="display: none;" id="add-favorito">
            <input type="hidden" name="propuesta" value="">
            <button type="submit" class="btn btn-primary mb-3">Agregar a favoritos</button>
        </form>

        <%
            if (rol == "colaborador") {
        %>
        <form action="/registrar-colaboracion" class="p-3" method="get" style="display: none;" id="registrar-colab">
            <input type="hidden" name="propuesta" value="">
            <button type="submit" class="btn btn-primary mb-3">Colaborar</button>
        </form>  


        <form action="/hacer-comentario" class="p-3" method="get" style="display: none;" id="comentar-prop">
            <input type="hidden" name="tituloProp" value="">
            <button type="submit" class="btn btn-primary mb-3">Comentar</button>
        </form> 





        <%} else if (rol == "proponente") {
        %>
        <div>
            <form action="/extender-financiacion" class="p-3" method="get" style="display: none;" id="extender-prop">
                <input type="hidden" name="titulo" value="">
                <button type="submit" class="btn btn-primary mb-3">Extender Financiacion</button>
            </form> 
        </div>

        <div>
            <form action="/cancelar-propuesta" class="p-3" method="post" style="display: none;" id="cancelar-prop">
                <input type="hidden" name="titulo" value="">
                <button type="submit" class="btn btn-primary mb-3">Cancelar Propuesta</button>
            </form> 
        </div> 


        <%}%>

        <% }%>


        <div class="d-flex justify-content-between gap-3">
            <div class="ms-5 mt-5">
                <h3>Categorias:</h3>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="radio1" name="optradio" value="Todas" checked>
                    <label class="form-check-label" for="radio1">Todas</label>
                </div>
                <%for (String cat : categorias) {%>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="radio2" name="optradio" value="<%= cat%>">
                    <label class="form-check-label" for="radio2"><%= cat%> </label>
                </div>
                <% }%>
            </div>
            <div class="container" >
                <ul class="nav nav-tabs">
                    <li><button class="nav-link active" data-bs-toggle="tab" data-bs-target="#PCreadas" data-estado="1" onclick="propPorEstado(this)">Propuestas Creadas</button></li>
                    <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PFinanciacion" data-estado="2" onclick="propPorEstado(this)">Propuestas en Financiación</button></li>
                    <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PFinanciadas" data-estado="3" onclick="propPorEstado(this)">Propuestas Financiadas</button></li>
                    <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PNOFinanciadas" data-estado="4" onclick="propPorEstado(this)">Propuestas NO Financiadas</button></li>
                    <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PCanceladas" data-estado="5" onclick="propPorEstado(this)">Propuestas Canceladas</button></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane fade show active" id="PCreadas"></div>
                    <div class="tab-pane fade" id="PFinanciacion"></div>
                    <div class="tab-pane fade" id="PFinanciadas"></div>
                    <div class="tab-pane fade" id="PNOFinanciadas"></div>
                    <div class="tab-pane fade" id="PCanceladas"></div>
                </div>
            </div> 
        </div>    
        <script src="${pageContext.request.contextPath}/resources/js/propuestaPorEstado.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/filtrarPropuestas.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
