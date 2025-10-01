<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Iniciar sesión</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/iniciarSesion.css" rel="stylesheet">
    </head>
    <body class="bg-light d-flex justify-content-center align-items-center vh-100">

        <form action="/login" method="post" class="card p-5 shadow" id="formulario">

            <h2 class="text-center mb-4">Iniciar sesión</h2>

            <div class="mb-3">
                <label for="nombre" class="form-label">Usuario</label>
                <input type="text" id="nombre" name="nombre" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Contraseña</label>
                <input type="password" id="password" name="password" class="form-control" minlength="8" maxlength="24" required>
            </div>

            <button type="submit" class="btn btn-success w-100 mb-3">Iniciar Sesión</button>

            <p class="text-center text-muted mb-0" id="p-registrarse">
                ¿No tenés cuenta? <a href="/crear-cuenta" class="fw-bold">Registrate</a>
            </p>

        </form>

    </body>
</html>
