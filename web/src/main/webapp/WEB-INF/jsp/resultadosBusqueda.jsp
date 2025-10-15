<%@page import="java.time.LocalDate"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.util.Date"%>
<%@page import="culturarte.datatypes.DTPropuesta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Resultados búsqueda</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link href="/resources/css/index.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <jsp:include page="header.jsp"/>
        <%
            List<DTPropuesta> propuestas = (List<DTPropuesta>) request.getAttribute("propuestas");
            for (DTPropuesta p : propuestas) {
                out.println("<div class=\"mb-2 p-2 border rounded bg-light\">");
                out.println("<div class=\"d-flex align-items-center gap-5\">");
                out.println("<div>");
                out.println("<p><strong>Título:</strong> " + p.getTitulo() + "</p>");
                out.println("<p><strong>Proponedor:</strong> " + p.getNickProponedor() + "</p>");
                out.println("<p><img src=\"/imagenes/" + p.getImagen() + "\" onerror=\"this.src='/resources/images/propdefault.png';\" alt=\"" + p.getTitulo() + "\" style=\"max-width:300px;border-radius:5px;\"></p>");
                out.println("<p class=\"text-center\"><strong>Dinero recaudado:</strong></p>");
                out.println("<div class=\"progress\" style=\"height: 25px;\">");
                float anchoBarra = ((p.getDineroRecaudado() / p.getMontoAReunir()) * 100);
                out.println("<div class=\"progress-bar\" style=\"width: " + anchoBarra + "%\">");
                out.println("$" + p.getDineroRecaudado());
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div>");
                out.println("<p><strong>Descripción:</strong> " + p.getDescripcion() + "</p>");
                out.println("<p><strong>Precio entrada:</strong> $" + p.getPrecioEntrada() + "</p>");
                out.println("<p><strong>Categoria:</strong> " + p.getTipoPropuesta() + "</p>");
                out.println("<p><strong>Lugar de realización:</strong> " + p.getLugarRealizara() + "</p>");
                out.println("<p><strong>Fecha Prevista:</strong> " + p.getFechaRealizara().toString() + "</p>");
                if (p.getFechaPublicacion() != null) {
                    out.println("<p><strong>Fecha de Publicacion:</strong> " + p.getFechaPublicacion().toString() + "</p>");
                } else {
                    out.println("<p><strong>Fecha de Publicacion:</strong> sin fecha de publicación</p>");
                }
                
                out.println("<p><strong>Tipos de retorno:</strong> " + p.getTiposRetorno() + "</p>");
                out.println("<p><strong>Monto a reunir: </strong> $" + p.getMontoAReunir() + "</p>");
                if (p.getPlazoFinanciacion() != null) {
                    long diferenciaDias = ChronoUnit.DAYS.between(LocalDate.now(), p.getPlazoFinanciacion());
                    out.println("<p><strong>Dias restantes para Financiación: </strong> " + diferenciaDias + "</p>");
                } else {
                    out.println("<p><strong>Dias restantes para Financiación: </strong> no quedan</p>");
                }
                
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
            }
            if (propuestas.isEmpty()) {
                out.println("<h1>No se encontraron propuestas que coincidan</h1>");
            }
        %>
    </body>
</html>
