/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function coincidenPasswords() {
    const password = document.getElementById('password');
    const passwordConfirm = document.getElementById('password-confirm');
    if (password.value === passwordConfirm.value && password.value.length >= 8) {
        password.classList.remove('is-invalid');
        password.classList.add('is-valid');
        passwordConfirm.classList.remove('is-invalid');
        passwordConfirm.classList.add('is-valid');
        return true;
    } else {
        password.classList.add('is-invalid');
        password.classList.remove('is-valid');
        passwordConfirm.classList.add('is-invalid');
        passwordConfirm.classList.remove('is-valid');
        return false;
    }
}
document.getElementById('password').addEventListener('input', coincidenPasswords);
document.getElementById('password-confirm').addEventListener('input', coincidenPasswords);


function validarUsuario() {
    const inputUsuario = document.getElementById('nombre-usuario');
    const labelUsuario = document.getElementById('estado-usuario');
    const valor = inputUsuario.value.trim();

    if (valor.length === 0) {
        labelUsuario.textContent = '';
        inputUsuario.classList.remove('is-valid', 'is-invalid');
        return;
    }

    fetch('/verificarUsuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'usuario=' + encodeURIComponent(valor)
    })
        .then(res => res.text())
        .then(texto => {
            if (texto === 'disponible') {
                labelUsuario.textContent = 'Disponible';
                labelUsuario.style.color = 'green';
                inputUsuario.classList.add('is-valid');
                inputUsuario.classList.remove('is-invalid');
            } else {
                labelUsuario.textContent = 'Ocupado';
                labelUsuario.style.color = 'red';
                inputUsuario.classList.add('is-invalid');
                inputUsuario.classList.remove('is-valid');
            }
        })
        .catch(e => console.error("Error al verificar usuario:", e));
}


function validarCorreo() {
    const inputCorreo = document.getElementById('email');
    const labelCorreo = document.getElementById('estado-correo');
    const valor = inputCorreo.value.trim();

    if (valor.length === 0) {
        labelCorreo.textContent = '';
        inputCorreo.classList.remove('is-valid', 'is-invalid');
        return;
    }

    fetch('/verificarCorreo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'correo=' + encodeURIComponent(valor)
    })
        .then(res => res.text())
        .then(texto => {
            if (texto === 'disponible') {
                labelCorreo.textContent = 'Disponible';
                labelCorreo.style.color = 'green';
                inputCorreo.classList.add('is-valid');
                inputCorreo.classList.remove('is-invalid');
            } else {
                labelCorreo.textContent = 'Ocupado';
                labelCorreo.style.color = 'red';
                inputCorreo.classList.add('is-invalid');
                inputCorreo.classList.remove('is-valid');
            }
        })
        .catch(e => console.error("Error al verificar correo:", e));
}

document.getElementById('nombre-usuario').addEventListener('input', validarUsuario);
document.getElementById('email').addEventListener('input', validarCorreo);

/*ocultamos/mostramos los campos del proponente*/
function toggleCamposProponente() {
    const radioColab = document.getElementById('radio-colab');
    const radioProp = document.getElementById('radio-prop');
    const camposProp = document.getElementById('campos-proponente');
    const ciudad = document.getElementById('ciudad');
    const calle = document.getElementById('calle');
    const numPuerta = document.getElementById('num-puerta');
    
    if (radioProp.checked) {
        camposProp.style.display = 'block';
        ciudad.required = true;
        calle.required = true;
        numPuerta.required = true;
    } else if (radioColab.checked) {
        camposProp.style.display = 'none';  
        ciudad.required = false;
        calle.required = false;
        numPuerta.required = false;
    }
}

/*cuando refrescamos, aseguramos que los radio no estén check*/
document.addEventListener('DOMContentLoaded', function() {
    const radiosTipoUsuario = document.getElementsByName('tipoUsuario');
    for (let i = 0; i < radiosTipoUsuario.length; i++) {
        radiosTipoUsuario[i].checked = false;
    }
});