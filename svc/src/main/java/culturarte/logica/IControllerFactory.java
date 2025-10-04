/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

/**
 *
 * @author mark
 */

//SINGLETON
public class IControllerFactory {
    private static IControllerFactory instancia;
    
    public static IControllerFactory getInstance() {
        if (instancia == null) {
            instancia = new IControllerFactory();
        }
        return instancia;
    }
    
    public IController getIController() {
        return Controller.getInstance();
    }
}
