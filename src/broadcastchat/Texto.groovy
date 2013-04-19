/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package broadcastchat

/**
 *
 * @author uzielgl
 */
class Texto extends Thread{
    public pnlTexto;
    public int c = 0;
    
    public void run(){
        while(true){
            String texto = Integer.toString(c) + " Cadena de texto a enviar";
            pnlTexto.txtEnviarTexto.append(texto);
            Mensaje m = pnlTexto.parent.proceso.crearMensaje();
            m.tipo = Mensaje.TIPO_TEXTO;
            m.texto = texto;
            pnlTexto.parent.proceso.difundirMensaje();
            
            try{
                Thread.sleep(1000);
            }catch(Exception e){ 
                System.out.println("Error en el thread de texto");
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void iniciarEnvio( PnlTexto pnlTexto ){
        this.pnlTexto = pnlTexto;
        this.start();
    }
    
}
        