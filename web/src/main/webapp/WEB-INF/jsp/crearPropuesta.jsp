<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Crear propuesta</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/crearPropuesta.css" rel="stylesheet">
    </head>
    <body class="bg-light d-flex justify-content-center align-items-center min-vh-100 py-2">
        <jsp:include page="header.jsp"/>
        
        <form action="/crear-propuesta" method="post" enctype="multipart/form-data" class="card p-5 shadow" id="formulario" style="margin-top: 120px;" onsubmit="return validarCheckboxes()">

            <h2 class="text-center mb-4">Crear propuesta</h2>

            <%
                List<String> categorias = (List<String>) request.getAttribute("categorias");

                String fechaActual = LocalDate.now().toString();
            %>

            <div class="mb-2">
                <label for="categoria" class="form-label">Categoría</label>
                <select id="categoria" name="categoria" class="form-select form-select-sm" required>
                    <option value="" selected disabled>-- Seleccione una categoría --</option>
                    <% if (categorias != null) {
                            for (String cat : categorias) {%>
                    <option value="<%= cat%>"><%= cat%></option>
                    <%   }
                    } else { %>
                    <option value="">No hay categorías disponibles</option>
                    <% }%>
                </select>
            </div>

            <div class="mb-2">
                <label for="titulo" class="form-label">titulo</label>
                <input type="text" id="titulo" name="titulo" class="form-control form-control-sm" required>
            </div>

            <div class="mb-2">
                <label for="descripcion" class="form-label">descripcion</label>
                <textarea id="descripcion" name="descripcion" class="form-control form-control-sm" rows="5" required></textarea>
            </div>

            <div class="mb-2">
                <label for="lugar" class="form-label">Lugar donde se realizara</label>
                <input type="text" id="lugar" name="lugar" class="form-control form-control-sm" required>
            </div>



            <div class="mb-2">
                <label for="fecha-prevista" class="form-label">fecha prevista</label>
                <input type="date" id="fecha-prevista" name="fecha-prevista" class="form-control form-control-sm" 
                       min="<%= fechaActual%>"  required>
            </div>

            <div class="mb-2">
                <label for="Precio" class="form-label">Precio de cada entrada</label>
                <div class="input-group input-group-sm">
                    <span class="input-group-text">$</span>
                    <input type="text" id="precio" name="precio" class="form-control" required>
                </div>
            </div>

            <div class="mb-2">
                <label for="Monto" class="form-label">Monto necesario</label>
                <div class="input-group input-group-sm">
                    <span class="input-group-text">$</span>
                    <input type="text" id="monto" name="monto" class="form-control" required>
                </div>
            </div>
            <div class="mb-2">
                <label for="tipo-retorno" class="form-label">Tipo de retorno:</label>
                <div class="mb-2">
                    <input name="tipoRetorno" value="entradaGratis" type="checkbox" class="form-check-input me-2" id="tipo-retorno-check1" >
                    <label class="form-check-label" for="tipo-retorno1">
                        Entrada gratis
                    </label>
                    <input name="tipoRetorno" value="porcentajeGanancia" type="checkbox" class="form-check-input me-2" id="tipo-retorno-check2" >
                    <label class="form-check-label" for="tipo-retorno1">
                        Porcentaje de ganancia
                    </label>
                    
                     <p class="text-center mb-0" id="mensaje-error">Debe seleccionar al menos un tipo de retorno</p>
                </div>
            </div>      

            <div class="mb-2">
                <label for="imagen" class="form-label">Imagen de perfil (JPG o PNG)</label>
                <input type="file" id="imagen" name="imagen" accept="image/png, image/jpeg" class="form-control form-control-sm">
            </div>
            
            <button type="submit" class="btn btn-success w-100 mb-3">Crear propuesta</button>

        </form>

        <script src="/resources/js/crearPropuesta.js"></script>
    </body>
</html>
