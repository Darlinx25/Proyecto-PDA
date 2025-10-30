<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header class="bg-light fixed-top shadow-sm">
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">

      
      <a class="navbar-brand" href="/">Culturarte</a>

      
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <% 
        String rol = (String) session.getAttribute("rol");
        String nombre = (String) session.getAttribute("nombre");
        String apellido = (String) session.getAttribute("apellido");
        String imagen = (String) session.getAttribute("ubiImagen");
        String usr = (String) session.getAttribute("username");
      %>

      
      <div class="collapse navbar-collapse" id="navbarSupportedContent">

        
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <% if (rol == null) { %>
            <li class="nav-item">
              <a href="/login" class="nav-link">Iniciar sesión</a>
            </li>
            <li class="nav-item">
              <a href="/crear-cuenta" class="nav-link">Registrarse</a>
            </li>
          <% } else if ("colaborador".equals(rol)) { %>
            <li class="nav-item">
              <a href="/consultar-perfil-usuario" class="nav-link">Consultar Perfiles</a>
            </li>
            <li class="nav-item">
              <a href="/ranking-usuario" class="nav-link">Consultar Rankings</a>
            </li>
          <% } else { %>
            <li class="nav-item">
              <a href="/consultar-perfil-usuario" class="nav-link">Consultar Perfiles</a>
            </li>
            <li class="nav-item">
              <a href="/ranking-usuario" class="nav-link">Consultar Rankings</a>
            </li>
          <% } %>
        </ul>

        
        <form class="d-flex mb-2 mb-lg-0" role="search">
          <input class="form-control me-2" type="search" id="input-busq" name="busq"
                 placeholder="Buscar..." aria-label="Search" style="height: 40px;">
          <button class="btn btn-outline-success" id="boton-busq" type="button" style="height: 40px;">Buscar</button>
        </form>

        
        <div class="ms-lg-3 mt-3 mt-lg-0 d-flex align-items-center text-white">
          <% if (rol == null) { %>
            <span class="text-white">Bienvenido, visitante</span>
          <% } else { %>
            <img height="45" width="45" class="rounded-circle border border-white me-2"
                 src="/imagenes/<%= imagen %>"
                 onerror="this.src='/resources/images/userdefault.png';"
                 alt="Foto de perfil">
            <div class="d-flex flex-column">
              <small><%= nombre %> <%= apellido %></small>
              <div class="d-flex">
                <a href="/perfil?user=<%= usr %>" class="btn btn-link text-white p-0 me-2">Ver perfil</a>
                <form action="/logout" method="post">
                  <button type="submit" class="btn btn-link text-white p-0">Cerrar sesión</button>
                </form>
              </div>
            </div>
          <% } %>
        </div>

      </div>
    </div>
  </nav>
</header>

<script src="${pageContext.request.contextPath}/resources/js/header.js"></script>
