<%@page contentType="text/html" pageEncoding="UTF-8"%>

<header class="bg-light fixed-top shadow-sm">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse " " id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0 " >
            <li class="nav-item">
              <a class="nav-link active" aria-current="page" href="/">Home</a>
             </li>
             <li class="nav-item">
              <a href="/crear-propuesta" class="nav-link active ">Crear propuesta</a>
             </li>
             <li class="nav-item">
              <a href="/consultar-perfil-usuario" class="nav-link active ">Consultar Perfiles</a>  
             </li>
          </ul>
          <form class="d-flex" action="/buscar-propuestas" method="get">
            <input class="form-control me-2" type="search" name="busq" placeholder="Título, descripción, lugar" aria-label="Search">
            <button class="btn btn-outline-success" type="submit">Buscar</button>
          </form>
        </div>
      </div>
    </nav>
</header>
<script src="${pageContext.request.contextPath}/resources/js/header.js"></script>
