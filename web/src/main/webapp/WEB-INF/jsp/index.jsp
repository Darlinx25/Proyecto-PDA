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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/index.css" rel="stylesheet">

    </head>
    <body class="bg-light"> 

        <%
            String rol = (String) session.getAttribute("rol");
            String nombre = (String) session.getAttribute("nombre");
            String apellido = (String) session.getAttribute("apellido");
            String imagen = (String) session.getAttribute("ubiImagen");
        %>
        <%
            if (rol == null) {
        %>
        <div class=" shadow p-1 bg-dark  text-white mb-1">
            <div class="container m-1 bg-dark"> 
                <div class="d-flex justify-content-between">
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
        </div>    

        <%
        } else {
        %>
        <div class=" shadow p-1 bg-dark  text-white mb-1">

            <div class="d-flex justify-content-between m-1">
                <div>
                    <h1  id="titulo">Culturarte</h1>
                </div>
                <div  >
                    <div class="d-flex align-items-center">        
                        <div>
                            <img height="100" width="100" class="rounded-circle border border-2 border-white "src="/imagenes/<%= imagen%>" alt="Foto de perfil">
                        </div>
                        <div>
                            <p class="text-uppercase"><%= nombre%> <%= apellido%></p>
                            <div class="d-flex align-items-center">
                                <div>
                                    <a href="/perfil" class="btn btn-link  p-1">Ver perfil  </a>
                                </div>
                                <div>
                                    <form action="/logout" method="post">
                                        <div>
                                            <button type="submit" class="btn btn-link  p-1">Cerrar sesión</button>
                                        </div>
                                    </form> 
                                </div>
                            </div> 
                        </div>
                    </div>       
                </div>
            </div>
            <div class="d-flex gap-2 mb-3">
                <a href="/seguir-usuario" class="btn btn-success p-1">Seguir usuario</a>
                <a href="/crear-propuesta" class="btn btn-success p-1">Crear propuesta</a>
                <a href="/registrar-colaboracion" class="btn btn-success p-1">Registrar colaboracion</a>
            </div>

        </div>
        <% }%>

        <!-- ACA HACEMOS LAS TABS DINAMICAS --> 
        <div class="container">
            <ul class="nav nav-tabs">
                <li><button class="nav-link active" data-bs-toggle="tab" data-bs-target="#PCreadas">Propuestas Creadas</button></li>
                <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PFinanciacíon">Propuestas en Financiación</button></li>
                <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PFinanciadas">Propuestas Financiadas</button></li>
                <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PNOFinanciadas">Propuestas NO Financiadas</button></li>
                <li><button class="nav-link" data-bs-toggle="tab" data-bs-target="#PCanceladas">Propuestas Canceladas</button></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade show active" id="PCreadas">hola</div>
                <div class="tab-pane fade" id="PFinanciacíon">hola</div>
                <div class="tab-pane fade" id="PFinanciadas">hola</div>
                <div class="tab-pane fade" id="PNOFinanciadas">hola</div>
                <div class="tab-pane fade" id="PCanceladas">hola</div>
            </div>
        </div>





        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
