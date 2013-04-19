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
    public PnlTexto pnlTexto;
    public Proceso proceso;
    public int c = 0;   
    
    public boolean continuar = true;
    public void run(){
        while(true){
            
            if( !continuar ){
                continue;
            }
            
            String texto = Integer.toString(c) + " Cadena de texto a enviar";
            pnlTexto.txtEnviarTexto.append(texto + "\n");
            Mensaje m = proceso.crearMensaje();
            m.tipo = Mensaje.TIPO_TEXTO;
            m.texto = texto;
            proceso.difundirMensaje( m );
            
            try{
                Thread.sleep(1000);
            }catch(Exception e){ 
                System.out.println("Error en el thread de texto");
                System.out.println(e.getMessage());
            }
            c++;
        }
    }
    
    public void iniciarEnvio( ){
        this.start();
    }
    
    public void showTexto( Mensaje m, pnl ){
        pnl.txtRecepcionTexto.append( m.texto + "\n" );
        
        /*
        if( proceso.window.tipo == MainWindow.TIPO_CLIENTE  ){
            proceso.window.pnlCliente.txtRecepcionTexto.append( m.texto );
        }else if( proceso.window.tipo == MainWindow.TIPO_AUDIO ){
            proceso.window.pnlAudio.txtRecepcionTexto.append( m.texto );
        } */
    }
    
    public void detenerEnvio(){
        continuar = false;
    }
    
    public void continuarEnvio(){
        continuar = true;
    }
}
        