
<%@page import="culturarte.datatypes.DTPropuesta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Extender financiación</title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/cancelarPropuesta.css" rel="stylesheet">
</head>
<body class="bg-light" onload="propPorEstado(document.querySelector('.nav-tabs .nav-link.active'))"> 
    <jsp:include page="header.jsp"/>
<div class="container mt-4">

    <h2 class="text-center mb-4">Extender financiación</h2>

    <div class="d-flex justify-content-center mb-3">
        <ul class="nav nav-tabs" id="propuestaTabs" role="tablist">
           
             <li><button class="nav-link active" data-bs-toggle="tab" data-bs-target="#PFinanciadas" data-estado="3" onclick="propPorEstado(this)">Propuestas Financiadas</button></li>
        </ul>
    </div>

    <div class="tab-content text-center" id="propuestaTabContent">
        <div class="tab-pane fade show active" id="PFinanciadas"></div>
    </div>

</div>
<script src="${pageContext.request.contextPath}/resources/js/cancelarPropuesta.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>




</body>
</html>
