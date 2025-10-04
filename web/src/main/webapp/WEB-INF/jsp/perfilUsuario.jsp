<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Perfil</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/resources/css/perfilUsuario.css" rel="stylesheet">
    </head>
    <body>

        <%
            String rol = (String) session.getAttribute("rol");
            String username = (String) session.getAttribute("username");
            String nombre = (String) session.getAttribute("nombre");
            String apellido = (String) session.getAttribute("apellido");
            String email = (String) session.getAttribute("email");
            String imagen = (String) session.getAttribute("ubiImagen");

        %>

        <% if (rol == null) { %>
        <div class="container text-center mt-5">
            <h2>No has iniciado sesión</h2>
            <a href="/login" class="btn btn-primary mt-3">Iniciar sesión</a>
        </div>
        <% } else {%>

        <div class="container mt-3">

            <div class="bg-light p-3 rounded shadow-sm">
                <h1 class=" text-center">  Tu Perfil  </h1>
            </div>


            <div class="bg-white p-4 rounded shadow-sm mt-3">
                <p class="text-center text-uppercase" id="NombreUser"><%= nombre%> <%= apellido%> - <%= rol%><p>
                <p class="text-center text-muted"><%= username%></p>
                <p class="text-center text-muted"><%= email%></p>
                <img src="/imagenes/<%= imagen %>" alt="Foto de perfil">
            </div>

            <div class="align-items-center">
                <div class="d-flex justify-content-between  mt-3">
                    <form action="/index" method="get">
                        <button type="submit" class="btn btn-danger">Inicio</button>
                    </form>
                    <form action="/logout" method="post">
                        <button type="submit" class="btn btn-danger">Cerrar sesión</button>
                    </form>
                </div>
            </div>    
        </div>

        <% }%>

    </body>
</html>
