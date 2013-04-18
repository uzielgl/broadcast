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
    
    public Procesos procesos = new Procesos();
    
    public Video video = new Video();
    
    public Proceso(){
        ip = Util.getLocalIp();
        String[] exp = ip.split("\\.");
        id = Integer.parseInt( exp[3] );
        port = id + 3000;
        
        comunicador = new Comunicador( ip, port ); //El constructor levanta el servidor
        comunicador.proceso = this;
        comunicador.udpServer.listeners.add(this);
        
        video.comunicador = comunicador;
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
    
    //Crea un mensaje pegandole el historial causal y otras cosas
    public Mensaje crearMensaje(){
        Mensaje m = new Mensaje();
        
        //Aqui le agrego el ci y lo necesario m.ci = tal cosa
        return m;
    }
    
    //@override
    public void onReceiveMessage(Mensaje m){
        if( m.tipo == Mensaje.TIPO_DESCUBRIMIENTO ){
            procesos.add( m.from );
            window.addHistory("Peers", procesos.toString() );
        }else{
            procesarMensaje( m );
        }
    }
    
    //Lo puede enviar a la cola o lo puede entregar
    public procesarMensaje( Mensaje m ){
        
    }
    
    public void entregarMensaje( Mensaje m){
        if( m.tipo == Mensaje.TIPO_VIDEO){
            video.showVideo( m )
        }
        
    }
    
    /** Manejara todos los procesos */
    private class Procesos{
        public ArrayList<BasicProceso> procesos = new ArrayList<BasicProceso>();
        
        public void add( BasicProceso proceso){
            //Está el proceso ? 
            boolean esta = false;
            for( BasicProceso p: procesos ){
                if( p.id == proceso.id ){
                    esta = true;
                    break;
                }
            }
            if( !esta )
                this.procesos.add( proceso );
        }
        
        public Proceso getById( int id ){
            for( BasicProceso p: procesos ) if( p.id == id ) return p;
            return null;
        }
    }
    
}

