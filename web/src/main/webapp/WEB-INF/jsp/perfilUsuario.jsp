<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Perfil</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/resources/css/perfilUsuario.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <%
            String rol = (String) request.getAttribute("rol");
            String username = (String) request.getAttribute("username");
            String nombre = (String) request.getAttribute("nombre");
            String apellido = (String) request.getAttribute("apellido");
            String imagen = (String) request.getAttribute("ubiImagen");
            String bio = (String) request.getAttribute("biografia");
            String web = (String) request.getAttribute("sitioWeb");

        %>

        <%if ("colaborador".equals(rol)) {%>

        <div class="container mt-3 ">

            <div class="bg-light p-2 rounded shadow-sm border border-5 border-dark">
                <h1 class=" text-center">  Tu Perfil  </h1>
            </div>


            <div class="bg-white p-4 rounded shadow-sm mt-3 d-flex justify-content-center gap-3 border border-5 border-dark">
                <div>
                    <img height="200" width="200" class="rounded-circle border border-5 border-dark "src="/imagenes/<%= imagen%>" onerror="this.src='/resources/images/userdefault.png';" alt="Foto de perfil">
                </div>
                <div class="d-flex flex-column justify-content-center">
                    <p class="text-start text-uppercase" id="NombreUser"><%= nombre%> <%= apellido%> - <%= rol%><p></p>
                    <p class="text-start  "><%= username%></p>
                </div>
            </div>

            <div class="align-items-center">
                <div class="d-flex justify-content-between  mt-3">
                    <form action="/index" method="get">
                        <button type="submit" class="btn btn-danger">Inicio</button>
                    </form>
                    <form action="/logout" method="post">
                        <button type="submit" class="btn btn-danger">Cerrar sesión</button>
                    </form>
                </div>
            </div>    
        </div>

        <% } else if ("proponente".equals(rol)) {%>

        <div class="container mt-3 ">

            <div class="bg-dark p-1 text-white rounded shadow-sm ">
                <h1 class=" text-center">  Tu Perfil  </h1>
            </div>


            <div class="bg-dark p-4 rounded  text-white shadow-sm mt-3 d-flex justify-content-center gap-3 border border-5 border-dark">
                <div>
                    <img height="200" width="200" class="rounded-circle border border-3 border-white "src="/imagenes/<%= imagen%>" onerror="this.src='/resources/images/userdefault.png';" alt="Foto de perfil">
                </div>
                <div class="d-flex flex-column justify-content-center">
                    <p class="text-start text-uppercase" id="NombreUser"><%= nombre%> <%= apellido%> - <%= rol%><p></p>
                    <p class="text-start  "><%= username%></p>
                    <p class="text-start">
                        <a href="<%= web%>" target="_blank" class="text-decoration-none text-primary">
                            <%= web%>
                        </a>
                    </p>
                    <p class="text-start "><%= bio%></p>
                </div>
            </div>

            <div class="align-items-center">
                <div class="d-flex justify-content-between  mt-3">
                    <form action="/index" method="get">
                        <button type="submit" class="btn btn-danger">Inicio</button>
                    </form>
                    <form action="/logout" method="post">
                        <button type="submit" class="btn btn-danger">Cerrar sesión</button>
                    </form>
                </div>
            </div>    
        </div>
        <%}%>

    </body>
</html>
