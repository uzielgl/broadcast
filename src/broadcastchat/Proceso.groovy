/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package broadcastchat
import com.google.gson.Gson;
/**
 *
 * @author uzielgl
 */
class Proceso implements ComunicadorListener{
    public int id; //Id del proceso
    public String ip; //Ip del proceso
    public int port;
    
    public Comunicador comunicador;
    public MainWindow window;
    
    Procesos procesos = new Procesos();
    
    public Proceso(){
        ip = Util.getLocalIp();
        String[] exp = ip.split("\\.");
        id = Integer.parseInt( exp[3] );
        port = id + 3000;
        
        comunicador = new Comunicador( ip, port ); //El constructor levanta el servidor
        comunicador.proceso = this;
        comunicador.udpServer.listeners.add(this);
    }
    
    public Proceso(String ip, int port){ //Un constructor sencillo para poder tener una lista de procesos
        ip = Util.getLocalIp();
        String[] exp = ip.split("\\.");
        id = Integer.parseInt( exp[3] );
        port = id + 3000;
    }
    
    public String toString(){
        def props = [
            "id" : id,
            "ip" : ip,
            "port" : port
        ]
        return new Gson().toJson( props );
    }
    
    //@override
    public void onReceiveMessage(Mensaje m){
        if( m.tipo == Mensaje.TIPO_DESCUBRIMIENTO ){
            procesos.add( m.from );
            window.addHistory("Peers", procesos.toString() );
        }
        
    }
    
    /** Manejara todos los procesos */
    private class Procesos{
        public ArrayList<Proceso> procesos = new ArrayList<Proceso>();
        
        public void add( BasicProceso proceso){
            //Está el proceso ? 
            boolean esta = false;
            for( Proceso p: procesos ){
                if( p.id == proceso.id ){
                    esta = true;
                    break;
                }
            }
            if( !esta )
                this.procesos.add( proceso );
        }
        
        public Proceso getById( int id ){
            for( Proceso p: procesos ) if( p.id == id ) return p;
            return null;
        }
   
    }
    
}

