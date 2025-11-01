<%@page import="java.time.LocalDateTime"%>
<%@page import="culturarte.datatypes.DTPropuesta"%>
<%@page import="java.util.List"%>
<%@page import="culturarte.datatypes.DTColaboracion"%>
<%@page import="culturarte.logica.IController"%>
<%@page import="culturarte.logica.IControllerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Culturarte</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    </head>
    <body>
        <jsp:include page="headerMovil.jsp"/>
        <%
            String usr = (String) session.getAttribute("username");
            IController controller = IControllerFactory.getInstance().getIController();
            
            List<DTColaboracion> colaboraciones = controller.listDTColaboracionUser(usr);
            
        %>
        
        <div class="container mt-4">
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                
                <%
                    for (DTColaboracion c : colaboraciones) {
                            DTPropuesta p = controller.obtenerDTPropuesta(c.getPropuestaColaborada());
                %>
                <div class="col">
                    <div class="card shadow-sm">
                        <img src="/imagenes/<%= p.getImagen() %>" onerror="this.src='/resources/images/propdefault.png';" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title"><%= p.getTitulo() %></h5>
                            <p class="card-text">Fecha de colaboración: <%= c.getFechaHora().toLocalDate() %></p>
                            <p class="card-text">Monto: <%= c.getMonto() %></p>
                            
                            <% if (c.isPagada()) { %>
                            <button type="button" class="btn btn-secondary">Ya pagada</button>
                            <% } else { %>
                            <button type="button" class="btn btn-primary">Realizar pago</button>
                            <% } %>
                        </div>
                    </div>
                </div>
                <% } %>
                

            </div>
        </div>
    </body>
</html>
