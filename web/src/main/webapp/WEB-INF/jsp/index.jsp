<%-- 
    Document   : index
    Created on : 29 Sept 2025, 00:39:04
    Author     : mark
--%>

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

        <%
            String rol = (String) session.getAttribute("rol");
            String nombre = (String) session.getAttribute("nombre");
            String apellido = (String) session.getAttribute("apellido");
            String imagen = (String) session.getAttribute("ubiImagen");
        %>
        <%
            if (rol == null) {
        %>
        <div class=" shadow p-2 bg-dark  text-white mb-1">
                <div class="d-flex justify-content-between gap-3">
                    <div >
                        <h1 class="text-center" id="titulo">Culturarte</h1>
                    </div>
                    <div class="text-center">
                        <h2>Bienvenido</h2>
                        <ul>
                            <a href="/login" class="btn btn-success p-1">Iniciar sesión</a></li>
                            <a href="/crear-cuenta" class="btn btn-success p-1">Registrarse</a></li>
                        </ul>
                    </div>
                </div>
        </div>    

        <%
        } else {
        %>
        <div class=" shadow p-1 bg-dark text-white mb-1">

            <div class="d-flex justify-content-between m-1">
                <div>
                    <h1  id="titulo">Culturarte</h1>
                </div>
                <div  >
                    <div class="d-flex align-items-center gap-2">        
                        <div>
                            <img height="100" width="100" class="rounded-circle border border-2 border-white " src="/imagenes/<%= imagen%>" onerror="this.src='/resources/images/userdefault.png';" alt="Foto de perfil">
                        </div>
                        <div>
                            <p class="text-uppercase"><%= nombre%> <%= apellido%></p>
                            <div class="d-flex align-items-center mb-3 gap-2">
                                <div>
                                    <a href="/perfil" class="btn btn-link p-1 ">Ver perfil  </a>
                                </div>
                                <div>
                                    <form action="/logout" method="post">
                                        <div>
                                            <button type="submit" class="btn btn-link p-1 ">Cerrar sesión</button>
                                        </div>
                                    </form> 
                                </div>
                            </div> 
                        </div>
                    </div>       
                </div>
            </div>
            <div class="d-flex gap-2 mb-3">
                <a href="/seguir-usuario" class="btn btn-danger p-1 ">Seguir usuario</a>
                <a href="/crear-propuesta" class="btn btn-danger p-1 ">Crear propuesta</a>
                <a href="/registrar-colaboracion" class="btn btn-danger p-1 ">Registrar colaboracion</a>
                <a href="/consultar-perfil-usuario" class="btn btn-danger p-1 ">Consultar Perfiles</a>
            </div>

        </div>
        <% }%>

        
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




        <script src="${pageContext.request.contextPath}/resources/js/propuestaPorEstado.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
