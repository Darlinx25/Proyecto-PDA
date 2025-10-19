<%@page contentType="text/html" pageEncoding="UTF-8"%>

<header class="bg-light fixed-top shadow-sm">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <%
                String rol = (String) session.getAttribute("rol");
                String nombre = (String) session.getAttribute("nombre");
                String apellido = (String) session.getAttribute("apellido");
                String imagen = (String) session.getAttribute("ubiImagen");
                String usr = (String) session.getAttribute("username");
            %>
            <div class="d-flex justify-content-between container-fluid" >
                <div class="d-flex"  id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 " >
                        <%if (rol == null) {
                        %>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Culturarte</a>
                        </li>
                        <li class="nav-item"> 
                            <a href="/login" class="nav-link active" aria-current="page">Iniciar sesión</a>
                        </li>
                        <li class="nav-item">
                            <a href="/crear-cuenta" class="nav-link active" aria-current="page">Registrarse</a>
                        </li>
                        <li class="nav-item">
                            <a href="/consultar-perfil-usuario" class="nav-link active ">Consultar Perfiles</a>  
                        </li>   
                        <%
                        } else if(rol == "proponente"){
                        %>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Culturarte</a>
                        </li>
                        <li class="nav-item">
                            <a href="/consultar-perfil-usuario" class="nav-link active ">Consultar Perfiles</a>  
                        </li>
                        <li class="nav-item">
                            <a href="/crear-propuesta" class="nav-link active ">Crear propuesta</a>
                        </li>
                        <%} else if(rol == "colaborador"){
                        %>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Culturarte</a>
                        </li>
                        <li class="nav-item">
                            <a href="/consultar-perfil-usuario" class="nav-link active ">Consultar Perfiles</a>  
                        </li>
                        <%}%>
                    </ul>
                    <form class="d-flex">
                        <input class="form-control me-2" type="search" id="input-busq" name="busq" placeholder="Título, descripción, lugar" aria-label="Search" style="height: 40px;" >
                        <button class="btn btn-outline-success" id="boton-busq" style="height: 40px;" type="button" >Buscar</button>
                    </form>
                </div>

                <div>
                    <%if (rol == null) {
                    %>
                    <h2 class="text-white ms-auto">Bienvenido, visitante </h2>
                    <%
                    } else {
                    %>

                    <div class="d-flex align-items-center gap-2 text-white">        
                        <div>
                            <img height="100" width="100" class="rounded-circle border border-2 border-white " src="/imagenes/<%= imagen%>" onerror="this.src='/resources/images/userdefault.png';" alt="Foto de perfil">
                        </div>
                        <div>
                            <p class="text-uppercase"><%= nombre%> <%= apellido%></p>
                            <div class="d-flex align-items-center mb-3 gap-2">
                                <div>
                                    <a href="/perfil?user=<%= usr%>" class="btn btn-link p-1 ">Ver perfil  </a>
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

                    <%}%> 
                </div>
            </div>



        </div>
    </nav>
</header>
<script src="${pageContext.request.contextPath}/resources/js/header.js"></script>
