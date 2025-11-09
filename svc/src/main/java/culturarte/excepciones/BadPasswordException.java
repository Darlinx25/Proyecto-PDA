/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.excepciones;

/**
 *
 * @author mark
 */
public class BadPasswordException extends Exception {

    public BadPasswordException() {
    }
    
    public BadPasswordException(String message) {
        super(message);
    }
}
