<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header class="bg-light fixed-top shadow-sm">
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">

      <% 
        String rol = (String) session.getAttribute("rol");
        String nombre = (String) session.getAttribute("nombre");
        String apellido = (String) session.getAttribute("apellido");
        String imagen = (String) session.getAttribute("ubiImagen");
        String usr = (String) session.getAttribute("username");
      %>
      <a class="navbar-brand" >Bienvenido <%= nombre%></a>
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
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          
            <li class="nav-item">
              <a href="/" class="nav-link">Consultar Propuestas</a>
            </li>
            <li class="nav-item">
              <a class="nav-link">Pagar Colaboracion</a> 
            </li>
            <li class="nav-item">
              <a href="/logout" class="nav-link">Cerrar sesión</a>
            </li>   
        </ul>
      </div>
    </div>
  </nav>
</header>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/header.js"></script>




