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