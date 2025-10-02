<%-- 
    Document   : perfil
    Created on : 2 oct 2025, 4:39:03 p. m.
    Author     : faxcundo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        

  
        
        <div class="bg-primary text-white p-5 text-center">
            <h1>Culturarte</h1>
            
            <div class="d-flex justify-content-end">
                <form action="/logout" method="post">
                    <button type="submit" class="btn btn-success w-100 mb-3" id="cerrar-sesion">Cerrar sesión</button>
                </form>
            </div>
        </div>

        <h2>Bienvenido, <%= username%></h2>

     

    </body>
</html>
