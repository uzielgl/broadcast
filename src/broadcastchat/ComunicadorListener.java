/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package broadcastchat;

/**
 *
 * @author uzielgl
 */
public interface ComunicadorListener {
    void onReceiveMessage( Mensaje m );
    
    //Aquí mismo podría agregar cosas como addToHistory que agregaría a un txtde histórico
}
