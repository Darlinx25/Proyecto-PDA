<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
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
        <jsp:include page="header.jsp"/>
        <%
            String rol = (String) request.getAttribute("rol");
            String rolSesion = (String) session.getAttribute("rol");
            String username = (String) request.getAttribute("username");
            String nombre = (String) request.getAttribute("nombre");
            String apellido = (String) request.getAttribute("apellido");
            String imagen = (String) request.getAttribute("ubiImagen");
            String bio = (String) request.getAttribute("biografia");
            String web = (String) request.getAttribute("sitioWeb");
            List<String> usuariosSeguidos = (List<String>) request.getAttribute("usuariosSeguidos");
            List<String> seguidores = (List<String>) request.getAttribute("seguidores");
            List<String> usuariosSeguidosLog = (List<String>) request.getAttribute("usuariosSeguidosLog");
            List<String> propuestasUsu = (List<String>) request.getAttribute("propuestasUsu");
            List<String> propuestasUsuPropias = (List<String>) request.getAttribute("propuestasPropias");
            boolean miPerfil = false;
            if (username.equals(session.getAttribute("username"))) {
                miPerfil = true;
            }
            List<String> propuestasColaboradas = (List<String>) request.getAttribute("propuestasColab");
            List<String> propuestasfavoritas = (List<String>) request.getAttribute("propuestasFav");

        %>
        <%if ("colaborador".equals(rol)) {%>
        <div class="container mt-3 bg-light">
            <div class="bg-secondary p-2 rounded shadow-sm border border-5 border-dark">
                <h1 class=" text-center">  Perfil  </h1>
            </div>
            <div class="bg-secondary p-4 rounded shadow-sm mt-3 d-flex justify-content-center gap-3 border border-5 border-dark">
                <div>
                    <img height="200" width="200" class="rounded-circle border border-5 border-dark "src="/imagenes/<%= imagen%>" onerror="this.src='/resources/images/userdefault.png';" alt="Foto de perfil">
                    <%
                        boolean yaSigo = false;
                        if (usuariosSeguidosLog != null) {
                            for (String u : usuariosSeguidosLog) {
                                if (u.equals(username)) {
                                    yaSigo = true;
                                    break;
                                }
                            }
                        }
                    %>
                    <% if (yaSigo && miPerfil == false && rolSesion != null) { %>
                    <div class="d-flex justify-content-center mt-3">
                        <button type="button" class="btn btn-success" id="follow" onclick="seguirUser()">Siguiendo</button>
                    </div>
                    <% }
                        if (yaSigo == false && miPerfil == false && rolSesion != null) { %>
                    <div class="d-flex justify-content-center mt-3">
                        <button type="button" class="btn btn-danger" id="follow" onclick="seguirUser()">Seguir</button>
                    </div>
                    <% }%>
                </div>
                <div class="d-flex flex-column justify-content-center">
                    <p class="text-start text-uppercase" id="NombreUser"><%= nombre%> <%= apellido%> - <%= rol%></p>
                    <p class="text-start  "><%= username%></p>
                    <div class="d-flex justify-content-between  mt-3 gap-4">
                        <div class="mb-2">
                            <label for="seguidores" class="form-label"><strong>Seguidores:</strong></label>
                            <select  id="seguidores" name="seguidores" class="form-select form-select-sm">
                                <% if (seguidores != null && !seguidores.isEmpty()) {
                                        for (String cat : seguidores) {%>
                                <option ><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay seguidores</option>
                                <% }%>
                            </select>
                        </div>
                        <div class="mb-2">
                            <label for="usuariosSeguidos" class="form-label"><strong>Seguidos:</strong></label>
                            <select  id="usuariosSeguidos" name="usuariosSeguidos" class="form-select form-select-sm" >
                                <% if (usuariosSeguidos != null && !usuariosSeguidos.isEmpty()) {
                                        for (String cat : usuariosSeguidos) {%>
                                <option ><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay seguidos</option>
                                <% }%>

                            </select>
                        </div> 
                        <div class="mb-3">
                            <label for="propuesta" class="form-label"><strong>Propuestas colaboradas:</strong></label>

                            <%if (miPerfil) {%>
                            <select onchange="cargarPropuestaColab();cargarColabPropia()" id="propuestaColaboradas" name="propuestaColab" class="form-select form-select-sm" required>
                                <option value="" selected disabled>-- Seleccione una propuesta  --</option>
                                <% if (propuestasColaboradas != null) {
                                        for (String cat : propuestasColaboradas) {%>
                                <option value="<%= cat%>"><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay propuestas disponibles</option>
                                <% }%>
                            </select>

                            <% } else {%>
                            <select onchange="cargarPropuestaColab()" id="propuestaColaboradas" name="propuestaColab" class="form-select form-select-sm" required>
                                <option value="" selected disabled>-- Seleccione una propuesta  --</option>
                                <% if (propuestasColaboradas != null) {
                                        for (String cat : propuestasColaboradas) {%>
                                <option value="<%= cat%>"><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay propuestas disponibles</option>
                                <% }%>
                            </select>
                            <%}%>  
                        </div>  
                        <div class="gap-4">
                            <label for="propuesta" class="form-label"><strong>Propuestas favoritas:</strong></label>
                            <select onchange="cargarPropuestaFavorita()" id="propuestaFavoritas" name="propuestaFav" class="form-select form-select-sm" required>
                                <option value="" selected disabled>-- Seleccione una propuesta  --</option>
                                <% if (propuestasfavoritas != null) {
                                        for (String cat : propuestasfavoritas) {%>
                                <option value="<%= cat%>"><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay propuestas disponibles</option>
                                <% }%>
                            </select>
                        </div> 



                    </div>

                </div>
            </div>
            <div class="modal fade" id="modalConstancia" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Constancia de Pago</h5>
                            
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <%
                            LocalDateTime fechaCons = LocalDateTime.now();
                        %>
                        <div class="modal-body">
                            <p><em>(Una vez cerrada no podra volver a ver la constancia)</em></p>
                            <p><strong>Detalles de la colaboración:</strong></p>
                            <p><strong>Plataforma:</strong>  Culturarte</p>
                            <p><strong>Fecha de constancia:</strong> <%= java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(fechaCons)%> </p>
                            <p><strong>Colaborador:</strong>  <%= nombre%> <%= apellido%></p>
                            <p><strong>Propuesta colaborada: </strong> <span id="modal-propColab"></span></p>
                            <p><strong>Monto colaborado:</strong> <span id="modal-monto"></span></p>
                            <p><strong>Tipo de retorno:</strong> <span id="modal-retorno"></span></p>
                            <p><strong>Fecha colaboracion:</strong> <span id="modal-fecha"></span></p>
                            <p><strong>Fecha de pago:</strong> <span id="modal-fechaPago"></span></p>
                            <p><strong>Metodo de pago:</strong> <span id="modal-metodoPago"></span></p>

                        </div>
                            <div class="modal-footer">
                                <form action="/generarPDF" method="POST" target="_blank" style="display: contents;">
                                    <div class="d-flex justify-content-between  mt-3 gap-4">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                        <button type="submit" class="btn btn-primary" onclick="prepararDescarga()">Descargar PDF</button>
                                    </div>
                                        
                                    <input type="hidden" name="fechaCons" value="<%= java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm").format(fechaCons)%>">
                                    <input type="hidden" name="nombre" value="<%= nombre%>">
                                    <input type="hidden" name="apellido" value="<%= apellido%>">
                                    <input type="hidden" id="pdf-propColab" name="propColab">
                                    <input type="hidden" id="pdf-monto" name="monto">
                                    <input type="hidden" id="pdf-retorno" name="retorno">
                                    <input type="hidden" id="pdf-fecha" name="fecha">
                                    <input type="hidden" id="pdf-fechaPago" name="fechaPago">
                                    <input type="hidden" id="pdf-metodoPago" name="metodoPago">  
                                </form>
                            </div>
                    </div>
                </div>
            </div>                
            <script>
                function prepararDescarga() {
                    document.getElementById('pdf-propColab').value = document.getElementById('modal-propColab').innerText;
                    document.getElementById('pdf-monto').value = document.getElementById('modal-monto').innerText;
                    document.getElementById('pdf-retorno').value = document.getElementById('modal-retorno').innerText;
                    document.getElementById('pdf-fecha').value = document.getElementById('modal-fecha').innerText;
                    document.getElementById('pdf-fechaPago').value = document.getElementById('modal-fechaPago').innerText;
                    document.getElementById('pdf-metodoPago').value = document.getElementById('modal-metodoPago').innerText;}
            </script>
            <div class="align-items-center">
                <div class="bg-secondary p-2 rounded shadow-sm border border-5 border-dark mb-3 mt-3">
                    <h3 class=" text-center">  Datos de propuesta colaborada:  </h3>
                    <div id="contenedorPropuestaColaboradas"></div>
                    <div id="contenedorPropuestaColaboradasPropias"></div>

                </div> 
            </div>    
            <div class="align-items-center">
                <div class="bg-secondary p-2 rounded shadow-sm border border-5 border-dark mb-3 mt-3">
                    <h3 class=" text-center">  Datos de propuesta favorita:  </h3>
                    <div id="contenedorPropuestaFavoritas"></div>
                </div> 
            </div>                 
        </div>
        <% } else if ("proponente".equals(rol)) {%>
        <div class="container mt-3 ">
            <div class="bg-dark p-1 text-white rounded shadow-sm ">
                <h1 class=" text-center">  Perfil  </h1>
            </div>
            <div class="bg-dark p-4 rounded  text-white shadow-sm mt-3 d-flex justify-content-center gap-3 border border-5 border-dark">
                <div>
                    <img height="200" width="200" class="rounded-circle border border-3 border-white "src="/imagenes/<%= imagen%>" onerror="this.src='/resources/images/userdefault.png';" alt="Foto de perfil">
                    <%
                        boolean yaSigo = false;
                        if (usuariosSeguidosLog != null) {
                            for (String u : usuariosSeguidosLog) {
                                if (u.equals(username)) {
                                    yaSigo = true;
                                    break;
                                }
                            }
                        }
                    %>
                    <% if (yaSigo && miPerfil == false && rolSesion != null) { %>
                    <div class="d-flex justify-content-center mt-3">
                        <button type="button" class="btn btn-success" id="follow" onclick="seguirUser()">Siguiendo</button>
                    </div>
                    <% }
                        if (yaSigo == false && miPerfil == false && rolSesion != null) { %>
                    <div class="d-flex justify-content-center mt-3">
                        <button type="button" class="btn btn-danger" id="follow" onclick="seguirUser()">Seguir</button>
                    </div>

                    <% }%>

                    <% if (miPerfil == true) { %>
                    <div class="d-flex justify-content-center mt-3">
                        <form action="/baja-proponente" method="post"  ">
                            <button type="submit" class="nav-link btn btn-danger">Darse de baja</button>
                        </form>

                    </div>
                    <% }%>

                </div>
                <div class="d-flex flex-column justify-content-center ">
                    <p class="text-start text-uppercase" id="NombreUser"><%= nombre%> <%= apellido%> - <%= rol%></p>
                    <p class="text-start  "><%= username%></p>
                    <p class="text-start">
                        <a href="<%= web%>" target="_blank" class="text-decoration-none text-primary">
                            <%= web%>
                        </a>
                    </p>
                    <p class="text-start "><%= bio%></p>
                    <div class="d-flex justify-content-between  mt-3 gap-4">
                        <div class="mb-3 me-3">
                            <label for="seguidores" class="form-label"><strong>Seguidores:</strong></label>
                            <select  id="seguidores" name="seguidores" class="form-select form-select-sm">
                                <% if (seguidores != null && !seguidores.isEmpty()) {
                                        for (String cat : seguidores) {%>
                                <option ><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay seguidores</option>
                                <% }%>
                            </select>
                        </div>
                        <div class="mb-3 me-3">
                            <label for="usuariosSeguidos" class="form-label"><strong>Seguidos:</strong></label>
                            <select  id="usuariosSeguidos" name="usuariosSeguidos" class="form-select form-select-sm">
                                <% if (usuariosSeguidos != null && !usuariosSeguidos.isEmpty()) {
                                        for (String cat : usuariosSeguidos) {%>
                                <option ><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay seguidos</option>
                                <% }%>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="propuesta" class="form-label"><strong>Propuestas:</strong></label>

                            <%if (miPerfil) {%>
                            <select onchange="cargarPropuesta()" id="propuestaSeguidos" name="propuesta" class="form-select form-select-sm" required>
                                <option value="" selected disabled>-- Seleccione una propuesta  --</option>
                                <% if (propuestasUsuPropias != null) {
                                        for (String cat : propuestasUsuPropias) {%>
                                <option value="<%= cat%>"><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay propuestas disponibles</option>
                                <% }%>
                            </select>

                            <% } else {%>
                            <select onchange="cargarPropuesta()" id="propuestaSeguidos" name="propuesta" class="form-select form-select-sm" required>
                                <option value="" selected disabled>-- Seleccione una propuesta  --</option>
                                <% if (propuestasUsu != null) {
                                        for (String cat : propuestasUsu) {%>
                                <option value="<%= cat%>"><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay propuestas disponibles</option>
                                <% }%>
                            </select>
                            <%}%>



                        </div >
                        <div class="mb-3">
                            <label for="propuesta" class="form-label"><strong>Propuestas favoritas:</strong></label>
                            <select onchange="cargarPropuestaFavorita()" id="propuestaFavoritas" name="propuestaFav" class="form-select form-select-sm" required>
                                <option value="" selected disabled>-- Seleccione una propuesta  --</option>
                                <% if (propuestasfavoritas != null) {
                                        for (String cat : propuestasfavoritas) {%>
                                <option value="<%= cat%>"><%= cat%></option>
                                <%   }
                                } else { %>
                                <option value="">No hay propuestas disponibles</option>
                                <% }%>
                            </select>
                        </div> 
                    </div>
                </div>
            </div>
            <div class="align-items-center">
            </div>
            <div class="bg-dark p-4 rounded text-white mb-3 mt-3">
                <h3 class=" text-center">  Datos de propuesta:  </h3>
                <div id="contenedorPropuestaSeguidos"></div> 
            </div>
            <div class="align-items-center">
                <div class="bg-dark p-2 rounded shadow-sm border border-5 border-dark mb-3 mt-3 text-white">
                    <h3 class=" text-center">  Datos de propuesta favorita:  </h3>
                    <div id="contenedorPropuestaFavoritas"></div>
                </div> 
            </div>                      
        </div>

        <%}%>
        <script>
            const u = "<%= username%>";
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/colaboracion.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/propuestaColabDetalle.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/propuestaDetalle.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/seguirUser.js"></script>
    </body>
</html>
