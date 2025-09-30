<%-- 
    Document   : iniciarSesion
    Created on : Sep 30, 2025, 1:36:33 PM
    Author     : faxcundo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar sesión</title>
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
                                    <h2 class="text-uppercase text-center mb-5">Iniciar Sesion</h2>
                                    <form action="/login" method="post"/>

                                        <div data-mdb-input-init class="form-outline mb-4">
                                            <input type="text" id="nombre" name="nombre" required class="form-control form-control-lg" />
                                            <label class="form-label" for="nombre">Usuario</label>
                                        </div>
                                        
                                        <div data-mdb-input-init class="form-outline mb-4">
                                            <input type="password" id="password" name="password" minlength="8" maxlength="24" required class="form-control form-control-lg" />
                                            <label class="form-label" for="password">Contraseña</label>
                                        </div>        
                                         <div class="d-flex justify-content-center">
                                            <button  type="submit" data-mdb-button-init
                                              data-mdb-ripple-init class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Iniciar Sesion</button>
                                        </div>
                                        
                                        <p class="text-center text-muted mt-5 mb-0">¿No tenés cuenta? <a href="/crear-cuenta"
                                            class="fw-bold text-body"><u>Registrate</u></a></p>
                                        </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
