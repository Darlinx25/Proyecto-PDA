<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Crear cuenta</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/crearCuenta.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <jsp:include page="header.jsp"/>
        <%

            String error = (String) request.getAttribute("error");
        %>

        <div class="d-flex justify-content-center align-items-center min-vh-100">

            <form action="/crear-cuenta" method="post" enctype="multipart/form-data"
                  style="margin-top: 120px;" onsubmit="return coincidenPasswords()" class="card p-5 shadow" id="formulario">


                <h2 class="text-center mb-4">Crear una cuenta</h2>
                <% if (error != null) {%>
                <div class="alert alert-danger text-center"> <%= error%></div>
                <% }%>
                <div class="mb-2">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" id="nombre" name="nombre" class="form-control form-control-sm" required>
                </div>

                <div class="mb-2">
                    <label for="apellido" class="form-label">Apellido</label>
                    <input type="text" id="apellido" name="apellido" class="form-control form-control-sm" required>
                </div>

                <div class="mb-2">
                    <label for="fecha-nacimiento" class="form-label">Fecha de nacimiento</label>
                    <input type="date" id="fecha-nacimiento" name="fechaNacimiento" class="form-control form-control-sm" 
                           min="1900-01-01" max="2007-01-01" required>
                </div>

                <div class="mb-2">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control form-control-sm" required>
                </div>

                <div class="mb-2">
                    <label for="nombre-usuario" class="form-label">Nombre de usuario</label>
                    <input type="text" id="nombre-usuario" name="nickname" class="form-control form-control-sm" required>
                </div>

                <div class="mb-2">
                    <label for="password" class="form-label">Contraseña (8 a 24 caracteres)</label>
                    <input type="password" id="password" name="password" class="form-control form-control-sm" minlength="8" maxlength="24" required>
                </div>

                <div class="mb-2">
                    <label for="password-confirm" class="form-label">Confirmar contraseña</label>
                    <input type="password" id="password-confirm" name="passwordConfirm" class="form-control form-control-sm" minlength="8" maxlength="24" required>
                </div>

                <div class="mb-2">
                    <label for="imagen" class="form-label">Imagen de perfil (JPG o PNG)</label>
                    <input type="file" id="imagen" name="imagen" accept="image/png, image/jpeg" class="form-control form-control-sm">
                </div>

                <div class="mt-3 mb-3">
                    <div class="form-check form-check-inline">
                        <input onclick="toggleCamposProponente()" class="form-check-input" type="radio" name="tipoUsuario" id="radio-colab" value="colaborador" required>
                        <label class="form-check-label" for="radio-colab">Colaborador</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input onclick="toggleCamposProponente()" class="form-check-input" type="radio" name="tipoUsuario" id="radio-prop" value="proponente" required>
                        <label class="form-check-label" for="radio-prop">Proponente</label>
                    </div>
                </div>

                <div class="mb-2" id="campos-proponente">
                    <div class="mb-2">
                        <label for="ciudad" class="form-label">Ciudad</label>
                        <input type="text" id="ciudad" name="ciudad" class="form-control form-control-sm" required>
                    </div>
                    <div class="mb-2">
                        <label for="calle" class="form-label">Calle</label>
                        <input type="text" id="calle" name="calle" class="form-control form-control-sm" required>
                    </div>
                    <div class="mb-2">
                        <label for="num-puerta" class="form-label">Nº de Puerta</label>
                        <input type="number" id="num-puerta" name="numPuerta" min="1" max="99999" class="form-control form-control-sm" required>
                    </div>
                    <div class="mb-2">
                        <label for="biografia" class="form-label">Biografía</label>
                        <textarea class="form-control form-control-sm" id="biografia" name="biografia" rows="5" maxlength="1000"></textarea>
                    </div>
                    <div class="mb-2">
                        <label for="sitio-web" class="form-label">Sitio web</label>
                        <input type="text" id="sitio-web" name="sitioWeb" class="form-control form-control-sm">
                    </div>
                </div>

                <div class="form-check mb-4 d-flex justify-content-center">
                    <input type="checkbox" class="form-check-input me-2" id="aceptar-terminos" required>
                    <label class="form-check-label" for="aceptar-terminos">
                        Acepto los <a href="#!"><u>Términos de Servicio</u></a>
                    </label>
                </div>

                <button type="submit" class="btn btn-success w-100 mb-3">Registrarse</button>

                <p class="text-center text-muted mb-0">
                    ¿Ya tenés cuenta? <a href="/login" class="fw-bold">Iniciar sesión</a>
                </p>

            </form>
        </div>

        <script src="/resources/js/crearCuenta.js"></script>
    </body>
</html>
