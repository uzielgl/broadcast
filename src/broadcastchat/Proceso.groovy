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
    public Texto texto = new Texto();
    
    public int pos = -1; //Define la posicíón que tiene este vector en el vt
    public VT = [];
    public hm = [];
    public ci = [];
    
    public cola_mensajes = []; // Arraylist que contiene instancias de Mensaje
    
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
    
    public Proceso(int id, String ip, int port){ //Un constructor sencillo para poder tener una lista de procesos
        this.id = id;
        this.ip = ip;
        this.port = port;
        comunicador = new Comunicador( ip, port ); //El constructor levanta el servidor
        comunicador.proceso = this;
        comunicador.udpServer.listeners.add(this);
        
        video.comunicador = comunicador;
    }
    
    public String toString(){
        def props = [
            "id" : id,
            "ip" : ip,
            "port" : port,
            "pos" : pos,
            "VT" : VT,
            "hm" : hm,
            "ci" : ci
        ]
        return new Gson().toJson( props );
    }
    
    //Crea un mensaje pegandole el historial causal y otras cosas
    public Mensaje crearMensaje(){
        Mensaje m = new Mensaje();
        hm = ci;
        m.estructura = [ pos, VT[pos], hm ];
        ci = [];
              
        //Aqui le agrego el ci y lo necesario m.ci = tal cosa
        return m;
    }
    
    //Envia el mensaje a todos los demás procesos. EL mensajhe creado en crearMensaje
    public difundirMensaje( Mensaje m ){
        m.from = new BasicProceso( this );
        
        window.addHistory("Mensaje a difunder", m.toString());
        for( BasicProceso p: procesos.procesos ){
            if( p.id != id){
                comunicador.udpClient.sendMessage(p.ip, p.port, m );
            }
        }
    }
    
    //@override
    public void onReceiveMessage(Mensaje m){
        if( m.tipo == Mensaje.TIPO_DESCUBRIMIENTO ){
            if ( procesos.add( m.from ) )
                VT.add(0);
            println "this.id " + this.id + this.id.getClass();
            println "form.id" + m.from.id + m.from.id.getClass();
            if( this.id == m.from.id && pos == -1){
                println "Entra aquí "
                pos = procesos.size() - 1;
            }
            window.addHistory("Peers", procesos.procesos.toString() );
            window.addHistory("YO", this.toString());
        }else{ //Este mensaje es de audio, video, texto, y se debe de procesar
            procesarMensaje( m );
        }
    }
    
    //Lo puede enviar a la cola o lo puede entregar
    public procesarMensaje( Mensaje message ){
        if( message == null) return true;
        window.addHistory("Recibiendo mensaje", message.toString() );
        int k = message.estructura[0];
        int tk = message.estructura[1];
        def hm = message.estructura[2];
        
        if( ! ( ( tk == (VT[ k ] + 1 ) ) && isCausal(VT, hm) ) ){ //Aquí duda con el +1 preguntar
            println "wait... Encolar el mensaje y con cada recepción intentar entregarlo (llamar a esta misma función)";
            addColaMensaje( message );
            window.addHistory("Esperando mensaje de p" + ( k + 1) );
            return false;
        }else{
            VT[ k ] ++;
            ci = deleteKS( k, ci);
            ci.add( [k, tk] );
            ci = deleteHmCi( hm, ci );
            
            entregarMensaje( m );
            eliminaDeCola( message );
            
            for( def x = 0 ; x< cola_mensajes.size(); x++ ){
                def m = cola_mensajes[x];
                procesarMensaje( m );
            }
            
            return true;
        }
    }
    
    /** Pone a null el mensaje que se le pasa de la cola de mensajes*/
    public eliminaDeCola(message){
        for( def x = 0 ; x< cola_mensajes.size(); x++ ){
            def m = cola_mensajes[x];
            if( m == null ) continue;
            if( m[0] == message[0] && m[1] == message[1] )
                cola_mensajes[x] = null;
        }
    }
    
    public void entregarMensaje( Mensaje m){
        window.addHistory( "Recibiendo mensaje", m.toString() );
        if( m.tipo == Mensaje.TIPO_VIDEO){
            video.showVideo( m )
        }
    }
    
    /** Ingresa mensajes no repetidos a la cola*/
    public addColaMensaje( message ){
        Iterator it = cola_mensajes.iterator();
        def add = true;
        while( it.hasNext() ){
            def msg = it.next();
            if( msg == null ) continue;
            if( msg.estructura[0] == message.estructura[0] && msg.estructura[1] == message.estructura[1] ){
                add = false;
                break;
            }
        }
        if( add == true) 
            cola_mensajes.add( message );
    }
    
     /** Elimina todas las tuplas de Hm que están en Ci
    * Nota: Regresa tuplas repetidas
    **/
    def deleteHmCi(Hm, Ci){
        Iterator it_ci = Ci.iterator();
        def new_ci = [];
        while( it_ci.hasNext() ) {
            def t_ci = it_ci.next();
            Iterator it_hm = Hm.iterator();
            def add = true;
            while( it_hm.hasNext() ) {
                def t_hm = it_hm.next();
                if(  t_ci[0] == t_hm[0] && t_ci[1] == t_hm[1]  ){
                    add = false;
                    break;
                }
            }
            if (add == true){
               new_ci.add( t_ci );
            }
        }
        return new_ci;
    }
    
     /** Elimina todas las tuplas que k=s del ci
     **/
    public deleteKS( int k, ArrayList ci ){
        Iterator it = ci.iterator();
        def new_ci = [];
        while( it.hasNext() ){
            def t = it.next();
            if( !( t[0] == k ) )
                new_ci.add( t );
        }
        return new_ci;
    }
    
     /** Define si pasa la condición causal
     * @param vt [1,0,1,2]
     * @param hm [ [2, 3], [2,4] ]
     * @return boolean  - Define si pasa o no la condición
     **/
    public isCausal( ArrayList vt, ArrayList hm ){
        println "vt en causal : " + vt;
        println "hm en causal : " + hm;
        Iterator it =  hm.iterator();
        while( it.hasNext() ){
            def t = it.next();
            int l = t[0];
            int tl = t[1];
            print tl + " <= " + vt[l];
            println tl <= vt[l]
            
            if( ! ( tl <= vt[l] ) )
                return false;
        }
        return true;
    }
    
    
    
   
    
}

