<%-- 
    Document   : index
    Created on : 29 Sept 2025, 00:39:04
    Author     : mark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    </head>
    <body>
        <div class="bg-primary text-white p-5 text-center">
            <h1>Culturarte</h1>
        </div>
        <%
            String rol = (String) session.getAttribute("rol");
            String username = (String) session.getAttribute("username");
        %>
        <%
            if (rol == null) {
        %>
            <h2>Bienvenido, visitante</h2>
            <ul>
                <li><a href="/login">Iniciar sesión</a></li>
                <li><a href="/crear-cuenta">Registrarse</a></li>
            </ul>
        <%
            } else {
        %>
            <h2>Bienvenido, <%= username %></h2>
        <% } %>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
    </body>
</html>
