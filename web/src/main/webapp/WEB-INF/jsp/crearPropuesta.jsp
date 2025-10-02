<%-- 
    Document   : crearPropuesta
    Created on : 1 oct. 2025, 10:40:52 p. m.
    Author     : alexi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body class="bg-light d-flex justify-content-center align-items-center vh-100">

        <form action="/crear-propuesta" method="post" class="card p-5 shadow" id="formulario">

            <h2 class="text-center mb-4">Crear Propuesta</h2>
             <%--
             Ejemplo de como vamos a tener que hacer la lista de categorias, substituir numericos por una string lista
             y pasarle las categorias a jsp
            <select class="form-select" size="3" aria-label="size 3 select example">
                <option selected>Categorias:</option>
                for(i=1;i=10;i++){
                <option value="Nombre categoria"></option>
                                
               ´}
             </select>
                --%>
            <div class="mb-3">
                <select class="form-select" size="3" aria-label="size 3 select example">
                    <option selected>Open this select menu</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                </select>
            </div>
                
            
            <div class="mb-3">
                <label for="titulo" class="form-label">Titulo</label>
                <input type="text" id="titulo" name="titulo" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea class="form-control" placeholder="" id="floatingTextarea2" style="height: 100px"></textarea>
            </div>

            <button type="submit" class="btn btn-success w-100 mb-3">Crear Propuesta</button>

            <p class="text-center text-muted mb-0" id="p-registrarse">
               <a href="/index" class="fw-bold">Cancelar?</a>
            </p>

        </form>

    </body>
        
</html>
