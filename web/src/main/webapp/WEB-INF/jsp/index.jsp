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
    <body>

        <%
            String rol = (String) session.getAttribute("rol");
            String username = (String) session.getAttribute("nombre");
        %>
        <%
            if (rol == null) {
        %>
        <div class="bg-primary text-white p-5 text-center">
            <h1>Culturarte</h1>
        </div>
        <h2>Bienvenido, visitante</h2>
        <ul>
            <li><a href="/login">Iniciar sesión</a></li>
            <li><a href="/crear-cuenta">Registrarse</a></li>
            
        </ul>
        <%
        } else {
        %>
        <div class="bg-primary text-white p-5 text-center">
            <h1>Culturarte</h1>
            
            <div class="d-flex justify-content-end">
                <form action="/logout" method="post">
                    <button type="submit" class="btn btn-success w-100 mb-3" id="cerrar-sesion">Cerrar sesión</button>
                </form>
            </div>
        </div>

        <h2>Bienvenido, <%= username%></h2>
        <ul>
            <li><a href="/seguir-usuario">Seguir usuario</a></li>
            <li><a href="/crear-propuesta">Crear propuesta</a></li>
            <li><a href="/perfil">Ver perfil</a></li>
            <li><a href="/registrar-colaboracion">Registrar colaboracion</a></li>
        </ul>



        <% }%>

    </body>
</html>
