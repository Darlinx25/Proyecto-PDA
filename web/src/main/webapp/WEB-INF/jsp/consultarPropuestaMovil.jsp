<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registrar colaboración</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico?v=1" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        
        
>

        <link href="/resources/css/registrarColaboracion.css" rel="stylesheet">
        <script>
            const propuestasColabUsuario = [
            <%
                List<String> propuestasColab = (List<String>) request.getAttribute("propuestasColab");
                if (propuestasColab != null) {
                    for (int i = 0; i < propuestasColab.size(); i++) {
                        out.print("\"" + propuestasColab.get(i).replace("\"", "\\\"") + "\"");
                        if (i < propuestasColab.size() - 1) {
                            out.print(",");
                        }
                    }
                }

            %>
            ];

            const propuestasPuedeColab = [
            <%                    
                List<String> propsParaColab = (List<String>) request.getAttribute("propsparacolab");
                    if (propsParaColab != null) {
                        for (int i = 0; i < propsParaColab.size(); i++) {
                            out.print("\"" + propsParaColab.get(i).replace("\"", "\\\"") + "\"");
                            if (i < propsParaColab.size() - 1) {
                                out.print(",");
                            }
                        }

                    }
            %>
            ];
        </script>
    </head>
    <body class="bg-light d-flex justify-content-center align-items-center min-vh-100 py-2" id="cuerpo" onload="propuestaElegida(document.getElementById('propuesta').value)">
        <jsp:include page="headerMovil.jsp"/>

        <form class="card p-5 shadow" id="formulario" style="margin-top: 120px;">



            <%
                List<String> propuestas = (List<String>) request.getAttribute("propuestas");
                String propuestaC = (String) request.getAttribute("propuestaC");
            %>

            <div class="mb-2" style="display: none;">
                <label for="propuesta" class="form-label"><strong>Propuesta:</strong></label>
                <select oninput="propuestaElegida()" id="propuesta" name="propuesta" class="form-select form-select-sm">
                    <option value="<%= propuestaC%>" selected ><%= propuestaC%></option>
                </select>   
            </div>   

            <div id="contenedor-propuesta" class="mt-3"></div>




        </form>

        <script src="${pageContext.request.contextPath}/resources/js/consultarPropuestaMovil.js"></script>

    </body>
</html>