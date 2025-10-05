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
        <link href="/resources/css/index.css" rel="stylesheet"
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
        <div class="d-flex justify-content-center align-items-center shadow text-center bg-dark text-white">
            <div class="container m-3 bg-dark"> 
                <div>
                    <h1 class="text-center" id="titulo">Culturarte</h1><!--  NO LOGRO QUE ME TOME EL CSS TITULO-->
                </div>

            </div>
        </div>    
        <h2>Bienvenido, visitante</h2>
        <ul>
            <li><a href="/login">Iniciar sesión</a></li>
            <li><a href="/crear-cuenta">Registrarse</a></li>
        </ul>
        <%
        } else {
        %>
        <div class=" card p-1 shadow p-1 bg-dark  text-white mb-3">
             
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
           
        </div>



   


    <ul>
        <li><a href="/seguir-usuario" class="btn btn-success  p-1 mb-3">Seguir usuario</a></li>
        <li><a href="/crear-propuesta" class="btn btn-success p-1 mb-3">Crear propuesta</a></li>
        <li><a href="/registrar-colaboracion" class="btn btn-success p-1 mb-3">Registrar colaboracion</a></li>
    </ul>



    <% }%>

</body>
</html>
