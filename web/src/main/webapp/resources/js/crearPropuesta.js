/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function validarCheckboxes() {
    const checkboxes = document.querySelectorAll('input[name="tipoRetorno"');
    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            return true; // Al menos uno seleccionado
        }
    }
    const mensajeError = document.getElementById("mensaje-error");
    mensajeError.style.display = 'block';

    return false; // No se envía el formulario
}