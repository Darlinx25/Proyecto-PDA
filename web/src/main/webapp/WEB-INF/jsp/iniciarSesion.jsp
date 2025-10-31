<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String usuarioRecordado = "";
    String passwordRecordado = "";

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("usuarioRecordado".equals(c.getName())) {
                usuarioRecordado = c.getValue();
            } else if ("passwordRecordado".equals(c.getName())) {
                passwordRecordado = c.getValue();
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Iniciar sesión</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/iniciarSesion.css" rel="stylesheet">
    </head>
    <body class="bg-light d-flex justify-content-center align-items-center vh-100">
        <jsp:include page="header.jsp"/>
        <form action="/login" method="post" class="card p-5 shadow" id="formulario">

            <h2 class="text-center mb-4">Iniciar sesión</h2>
            
            <% if (request.getAttribute("mensajeError") != null) { %>
                <p class="text-center mb-0" id="mensaje-error"><%= request.getAttribute("mensajeError") %></p>
            <% } %>
            
            <div class="mb-3">
                <label for="nombre" class="form-label">Usuario/email</label>
                <input type="text" id="nombre" name="nickname" class="form-control" value="<%= usuarioRecordado %>" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Contraseña</label>
                <input type="password" id="password" name="password" class="form-control" minlength="8" maxlength="24" value="<%= passwordRecordado %>" required>
            </div>
            
            <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" id="recordarme" name="recordarme">
            <label class="form-check-label" for="recordarme">Recordarme</label>
            </div>

            <button type="submit" class="btn btn-success w-100 mb-3">Iniciar Sesión</button>

            <p class="text-center text-muted mb-0" id="mensaje-registrarse">
                ¿No tenés cuenta? <a href="/crear-cuenta" class="fw-bold">Registrate</a>
            </p>

        </form>

    </body>
</html>
