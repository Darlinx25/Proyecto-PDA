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