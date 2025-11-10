<%@page import="webservices.DTPropuesta"%>
<%@page import="webservices.DTColaboracion"%>
<%@page import="webservices.ControllerWS"%>
<%@page import="webservices.ControllerWS_Service"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.List"%>

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

            ControllerWS_Service service = new ControllerWS_Service();
            ControllerWS webServices = service.getControllerWSPort();

            List<DTColaboracion> colaboraciones = webServices.listDTColaboracionUser(usr);

        %>
        
        <% if (colaboraciones.isEmpty()) { %>
        <div class="d-flex justify-content-center align-items-center vh-100">
            <h1 class="text-center display-4">¡No has hecho ninguna colaboración!</h1>
        </div>
        <% } else {%>
        <div class="container mt-4">
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">

                <%                    for (DTColaboracion c : colaboraciones) {
                        DTPropuesta p = webServices.obtenerDTPropuesta(c.getPropuestaColaborada());
                %>
                <div class="col">
                    <div class="card shadow-sm">
                        <img src="/imagenes/<%= p.getImagen()%>" onerror="this.src='/resources/images/propdefault.png';" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title"><%= p.getTitulo()%></h5>
                            <p class="card-text">Fecha de colaboración: <%=  java.time.LocalDateTime.parse(c.getFechaHora()).toLocalDate() %></p>
                            <p class="card-text">Monto: <%= c.getMonto()%></p>

                            <% if (c.isPagada()) { %>
                            <button type="button" class="btn btn-secondary">Ya pagada</button>
                            <% } else {%>
                            <button type="button" data-id-colab="<%= c.getId() %>" data-monto="<%= c.getMonto()%>" class="btn btn-primary"
                                    data-bs-toggle="modal" data-bs-target="#modal-pago">Realizar pago</button>
                            <% } %>
                        </div>
                    </div>
                </div>
                <% }%>
            </div>
        </div>
        <% } %>
            
        <div class="modal fade" id="modal-pago" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Realizar Pago</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form id="formulario-pago" action="/pagar" method="post">
                            <div class="mb-3">
                                <label for="monto-colaboracion" class="form-label">Monto de Colaboración</label>
                                <input name="montoPago" type="text" class="form-control" id="monto-colaboracion" value="" readonly>
                            </div>
                            
                            <input name="idColaboracion" type="hidden" class="form-control" id="id-colaboracion" value="" readonly>
                            
                            <div class="mb-3">
                                <label for="metodo-pago" class="form-label">Método de Pago</label>
                                <select name="metodoPago" class="form-select" id="metodo-pago" required>
                                    <option value="elegir" selected disabled>Elige un método de pago</option>
                                    <option value="tarjeta">Tarjeta</option>
                                    <option value="transferencia">Transferencia Bancaria</option>
                                    <option value="paypal">PayPal</option>
                                </select>
                            </div>

                            <div id="campos-tarjeta" class="campos-pago" style="display: none;">
                                <div class="mb-3">
                                    <label for="tipo-tarjeta" class="form-label">Tipo de tarjeta</label>
                                    <select name="tipoTarjeta" class="form-select" id="tipo-tarjeta" required>
                                        <option value="elegir" selected disabled>Elige la marca</option>
                                        <option value="oca">OCA</option>
                                        <option value="visa">Visa</option>
                                        <option value="master">Master</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="numero-tarjeta" class="form-label">Número de tarjeta</label>
                                    <input required name="nroTarjeta" type="text" class="form-control" id="numero-tarjeta" placeholder="XXXX XXXX XXXX XXXX">
                                </div>
                                <div class="mb-3">
                                    <label for="fecha-venc" class="form-label">Fecha de vencimiento</label>
                                    <input required name="vencTarjeta" type="text" class="form-control" id="fecha-venc" placeholder="MM/AA">
                                </div>
                                <div class="mb-3">
                                    <label for="cvc" class="form-label">CVC</label>
                                    <input required name="cvc" type="text" class="form-control" id="cvc" placeholder="XXX">
                                </div>
                                <div class="mb-3">
                                    <label for="titular-tarjeta" class="form-label">Titular</label>
                                    <input required name="titularTarjeta" type="text" class="form-control" id="titular-tarjeta" placeholder="Albert Einstein">
                                </div>
                            </div>

                            <div id="campos-transferencia" class="campos-pago" style="display: none;">
                                <div class="mb-3">
                                    <label for="nombre-banco" class="form-label">Nombre del banco</label>
                                    <input required name="nombreBanco" type="text" class="form-control" id="nombre-banco" placeholder="Nombre del banco">
                                </div>
                                <div class="mb-3">
                                    <label for="cuenta-banco" class="form-label">Número de cuenta</label>
                                    <input required name="cuentaBanco" type="text" class="form-control" id="cuenta-banco" placeholder="Número de cuenta">
                                </div>
                                <div class="mb-3">
                                    <label for="titular-banco" class="form-label">Titular</label>
                                    <input required name="titularBanco" type="text" class="form-control" id="titular-banco" placeholder="Albert Einstein">
                                </div>
                            </div>

                            <div id="campos-paypal" class="campos-pago" style="display: none;">
                                <div class="mb-3">
                                    <label for="cuenta-paypal" class="form-label">Número de cuenta</label>
                                    <input required name="cuentaPaypal" type="text" class="form-control" id="cuenta-paypal" placeholder="Número de cuenta">
                                </div>
                                <div class="mb-3">
                                    <label for="titular-paypal" class="form-label">Titular</label>
                                    <input required name="titularPaypal" type="text" class="form-control" id="titular-paypal" placeholder="Albert Einstein">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="confirmarPago()">Confirmar Pago</button>
                    </div>
                </div>
            </div>
        </div>
            
        <script src="/resources/js/pagar.js"></script>
    </body>
</html>
