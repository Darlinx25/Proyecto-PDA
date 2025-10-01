<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Crear cuenta</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light d-flex justify-content-center align-items-center vh-100">

        <form action="/crear-cuenta" method="post" 
              onsubmit="return coincidenPasswords()" 
              class="card p-5 shadow" style="max-width: 650px; border-radius: 15px;">

            <h2 class="text-center mb-4">Crear una cuenta</h2>

            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" id="nombre" name="nombre" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="apellido" class="form-label">Apellido</label>
                <input type="text" id="apellido" name="apellido" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="fecha-nacimiento" class="form-label">Fecha de nacimiento</label>
                <input type="date" id="fecha-nacimiento" name="fechaNacimiento" class="form-control" 
                       min="1900-01-01" max="2007-01-01" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="nombre-usuario" class="form-label">Nombre de usuario</label>
                <input type="text" id="nombre-usuario" name="nickname" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Contraseña (8 a 24 caracteres)</label>
                <input type="password" id="password" name="password" class="form-control" minlength="8" maxlength="24" required>
            </div>

            <div class="mb-3">
                <label for="password-confirm" class="form-label">Confirmar contraseña</label>
                <input type="password" id="password-confirm" name="passwordConfirm" class="form-control" minlength="8" maxlength="24" required>
            </div>

            <div class="form-check mb-4 text-center">
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

        <script>
            function coincidenPasswords() {
                const password = document.getElementById('password');
                const passwordConfirm = document.getElementById('password-confirm');
                if (password.value === passwordConfirm.value && password.value.length >= 8) {
                    password.classList.remove('is-invalid');
                    password.classList.add('is-valid');
                    passwordConfirm.classList.remove('is-invalid');
                    passwordConfirm.classList.add('is-valid');
                    return true;
                } else {
                    password.classList.add('is-invalid');
                    password.classList.remove('is-valid');
                    passwordConfirm.classList.add('is-invalid');
                    passwordConfirm.classList.remove('is-valid');
                    return false;
                }
            }
            document.getElementById('password').addEventListener('input', coincidenPasswords);
            document.getElementById('password-confirm').addEventListener('input', coincidenPasswords);
        </script>

    </body>
</html>
