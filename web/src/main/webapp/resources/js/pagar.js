document.querySelectorAll('button[data-monto]').forEach(function (boton) {
    boton.addEventListener('click', function () {
        let monto = this.getAttribute('data-monto');

        document.getElementById('monto-colaboracion').value = monto;
        document.getElementById('metodo-pago').value = 'elegir';
        let camposPago = document.querySelectorAll('.campos-pago');
        camposPago.forEach(function (campo) {
            campo.style.display = 'none';
        });
        document.getElementById('tipo-tarjeta').value = 'elegir';
    });
});

document.getElementById('metodo-pago').addEventListener('change', function () {
    let camposPago = document.querySelectorAll('.campos-pago');
    camposPago.forEach(function (campo) {
        campo.style.display = 'none';
    });

    let metodoSelec = this.value;
    if (metodoSelec === 'tarjeta') {
        document.getElementById('campos-tarjeta').style.display = 'block';
    } else if (metodoSelec === 'transferencia') {
        document.getElementById('campos-transferencia').style.display = 'block';
    } else if (metodoSelec === 'paypal') {
        document.getElementById('campos-paypal').style.display = 'block';
    }
});

function confirmarPago() {
    document.getElementById("formulario-pago").submit();
}