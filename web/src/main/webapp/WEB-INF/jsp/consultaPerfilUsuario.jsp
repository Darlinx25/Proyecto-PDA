<%-- 
    Document   : consultaPerfilUsuario
    Created on : 6 oct. 2025, 2:32:02 a. m.
    Author     : alexi
--%>

<%@page import="culturarte.logica.Usuario"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.*, culturarte.logica.DTUsuario" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Consultar perfil de usuario</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/consultarPerfilUsuario.css" rel="stylesheet">
    </head>
    <body class="container mt-5">
        <%
            String rol = (String) session.getAttribute("rol");
            String nombre = (String) session.getAttribute("nombre");
            String apellido = (String) session.getAttribute("apellido");
            String imagen = (String) session.getAttribute("ubiImagen");
        %>
        <div class=" shadow p-1 bg-dark text-white mb-1">

            <div class="d-flex justify-content-between m-1">
                <div>
                    <h1  id="titulo">Culturarte</h1>
                </div>
                <div  >
                    <div class="d-flex align-items-center gap-2">        
                        <div>
                            <img height="100" width="100" class="rounded-circle border border-2 border-white "src="/imagenes/<%= imagen%>" alt="Foto de perfil">
                        </div>
                        <div>
                            <p class="text-uppercase"><%= nombre%> <%= apellido%></p>
                            <div class="d-flex align-items-center mb-3 gap-2">
                                <div>
                                    <a href="/perfil" class="btn btn-link p-1 ">Ver perfil  </a>
                                </div>
                                <div>
                                    <form action="/logout" method="post">
                                        <div>
                                            <button type="submit" class="btn btn-link p-1 ">Cerrar sesión</button>
                                        </div>
                                    </form> 
                                </div>
                            </div> 
                        </div>
                    </div>       
                </div>
            </div>
            <div class="d-flex gap-2 mb-3">
                <a href="/seguir-usuario" class="btn btn-danger p-1 ">Seguir usuario</a>
                <a href="/crear-propuesta" class="btn btn-danger p-1 ">Crear propuesta</a>
                <a href="/registrar-colaboracion" class="btn btn-danger p-1 ">Registrar colaboracion</a>

            </div>

        </div>

        
            
                <thead class="table-light">
                    <tr>
                        <th>Usuario</th>
                        <th>Seleccionar</th>


                    </tr>
                </thead>
                <%
                    List<String> usuarios = (List<String>) request.getAttribute("usuarios");
                    for (String u : usuarios) {
                %>
                <tr>
                    <td><%= u%></td>
                    <td>
                        <input type="radio" name="usuarioSeleccionado" value="<%= u%>" class="form-check-input" onchange="obtenerUsuarioSeleccionado()"">
                    </td>
                </tr>
   
                
                    
                    <script>
                    function obtenerUsuarioSeleccionado() {
                        const radios = document.getElementsByName("usuarioSeleccionado");
                        let usuarioSeleccionado = null;

                        for (const radio of radios) {
                            if (radio.checked) {
                                usuarioSeleccionado = radio.value;
                                break;
                            }
                        }

                        return usuarioSeleccionado;
                    }
                </script> 
</html>
