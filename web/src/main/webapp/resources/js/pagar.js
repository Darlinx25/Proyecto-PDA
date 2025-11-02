document.querySelectorAll('button[data-monto]').forEach(function (boton) {
    boton.addEventListener('click', function () {
        let monto = this.getAttribute('data-monto');
        let idColaboracion = this.getAttribute('data-id-colab');

        document.getElementById('monto-colaboracion').value = monto;
        document.getElementById('id-colaboracion').value = idColaboracion;
        
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
    const metodoPago = document.getElementById("metodo-pago").value;

    let grupoVisible = null;
    if (metodoPago === "tarjeta") grupoVisible = document.getElementById("campos-tarjeta");
    if (metodoPago === "transferencia") grupoVisible = document.getElementById("campos-transferencia");
    if (metodoPago === "paypal") grupoVisible = document.getElementById("campos-paypal");

    if (metodoPago === "elegir") {
        document.getElementById("metodo-pago").classList.add("is-invalid");
        return;
    } else {
        document.getElementById("metodo-pago").classList.remove("is-invalid");
    }

    if (grupoVisible) {
        const inputs = grupoVisible.querySelectorAll("input[required], select[required]");
        let valido = true;

        inputs.forEach(input => {
            if (!input.value.trim() || input.value === "elegir") {
                input.classList.add("is-invalid");
                valido = false;
            } else {
                input.classList.remove("is-invalid");
            }
        });

        if (!valido) {
            return;
        }
    }

    document.getElementById("formulario-pago").submit();
}