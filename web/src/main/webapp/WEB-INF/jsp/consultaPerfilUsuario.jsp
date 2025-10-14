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
        <jsp:include page="header.jsp"/>
        <%
            List<String> usuarios = (List<String>) request.getAttribute("usuarios");
            
        %>
        <div>
            <h2>Usuarios:</h2>
            <div class="list-group">
                <% if (usuarios != null) {
                        for (String cat : usuarios) {%>
                <a href="/perfil?user=<%= cat%>" class="list-group-item list-group-item-action"><%= cat%></a>
                <%}
                    }%>
            </div 
        </div>  
    </body>        
</html>
