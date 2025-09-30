<%-- 
    Document   : crearCuenta
    Created on : 30 Sept 2025, 05:15:25
    Author     : mark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrarse</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    </head>
    <body>
        <section class="vh-100">
            <div class="mask d-flex align-items-center h-100 gradient-custom-3">
                <div class="container h-100">
                    <div class="row d-flex justify-content-center align-items-center h-100">
                        <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                            <div class="card" style="border-radius: 15px;">
                                <div class="card-body p-5">
                                    <h2 class="text-uppercase text-center mb-5">Crear una cuenta</h2>
                                    <form onsubmit="return coincidenPasswords()" action="/crear-cuenta" method="post">

                                        <div data-mdb-input-init class="form-outline mb-4">
                                            <input type="text" id="nombre" name="nombre" required class="form-control form-control-lg" />
                                            <label class="form-label" for="nombre">Nombre</label>
                                        </div>
                                        
                                        <div data-mdb-input-init class="form-outline mb-4">
                                            <input type="text" id="apellido" name="apellido" required class="form-control form-control-lg" />
                                            <label class="form-label" for="apellido">Apellido</label>
                                        </div>
                                        
                                        <div data-mdb-input-init class="form-outline mb-4">
                                            <input type="date" id="fecha-nacimiento" name="fechaNacimiento" min="1900-01-01" max="2007-01-01" required class="form-control form-control-lg" />
                                            <label class="form-label" for="fecha-nacimiento">Fecha de nacimiento</label>
                                        </div>

                                        <div data-mdb-input-init class="form-outline mb-4">
                                            <input type="email" id="email" name="email" required class="form-control form-control-lg" />
                                            <label class="form-label" for="email">Email</label>
                                        </div>
                                        
                                        <div data-mdb-input-init class="form-outline mb-4">
                                            <input type="text" id="nombre-usuario" name="nickname" required class="form-control form-control-lg" />
                                            <label class="form-label" for="nombre-usuario">Nombre de usuario</label>
                                        </div>
                                        
                                        <div data-mdb-input-init class="form-outline mb-4">
                                            <input type="password" id="password" name="password" minlength="8" maxlength="24" required class="form-control form-control-lg" />
                                            <label class="form-label" for="password">Contraseña (8 a 24 caracteres)</label>
                                        </div>

                                        <div data-mdb-input-init class="form-outline mb-4">
                                            <input type="password" id="password-confirm" name="passwordConfirm" minlength="8" maxlength="24" required class="form-control form-control-lg" />
                                            <label class="form-label" for="password-confirm">Confirmar contraseña</label>
                                        </div>

                                        <div class="form-check d-flex justify-content-center mb-5">
                                            <input class="form-check-input me-2" type="checkbox" value="" id="aceptar-terminos" required />
                                            <label class="form-check-label" for="aceptar-terminos">
                                              Acepto los <a href="#!" class="text-body"><u>Términos de Servicio</u></a>
                                            </label>
                                        </div>

                                        <div class="d-flex justify-content-center">
                                            <button  type="submit" data-mdb-button-init
                                              data-mdb-ripple-init class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Registrarse</button>
                                        </div>

                                        <p class="text-center text-muted mt-5 mb-0">¿Ya tenés cuenta? <a href="/login"
                                            class="fw-bold text-body"><u>Iniciar sesión</u></a></p>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <script>
            function coincidenPasswords() {
                const password = document.getElementById('password');
                const passwordConfirm = document.getElementById('password-confirm');

                if (password.value === passwordConfirm.value && passwordConfirm.value !== '' && password.value.length >= 8) {
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
            const password = document.getElementById('password');
            const passwordConfirm = document.getElementById('password-confirm');
            password.addEventListener('input', coincidenPasswords);
            passwordConfirm.addEventListener('input', coincidenPasswords);
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
    </body>
</html>
