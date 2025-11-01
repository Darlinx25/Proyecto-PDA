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
        <h1>Hello World!</h1>
    </body>
</html>
