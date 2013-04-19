/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package broadcastchat
import com.google.gson.Gson;
import java.awt.Image
import javax.swing.JLabel
import javax.swing.ImageIcon
import javax.swing.Icon
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.Clip

import javax.sound.sampled.*;
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
    public Audio audio = new Audio();
    
    
    
    
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
        VT[ pos ]++;
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
        
        //window.addHistory("Mensaje a difunder", m.toString());
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
    public  procesarMensaje( Mensaje message ){
        if( message == null) return true;
        window.addHistory("Recibiendo mensaje", message.toString() );
        int k = message.estructura[0];
        int tk = message.estructura[1];
        def hm = message.estructura[2];
        
        if( ! ( ( tk == (VT[ k ] + 1 ) ) && isCausal(VT, hm) ) ){ //Aquí duda con el +1 preguntar
            //println "wait... Encolar el mensaje y con cada recepción intentar entregarlo (llamar a esta misma función)";
            window.addHistory("Esperando mensaje de p" + ( k + 1) );
            addColaMensaje( message );
            return false;
        }else{
            VT[ k ] ++;
            ci = deleteKS( k, ci);
            ci.add( [k, tk] );
            ci = deleteHmCi( hm, ci );
            
            entregarMensaje( message );
            eliminaDeCola( message );
            
            for( def x = 0 ; x< cola_mensajes.size(); x++ ){
                def m = cola_mensajes[x];
                procesarMensaje( message );
            }
            
            return true;
        }
    }
    
    /** Pone a null el mensaje que se le pasa de la cola de mensajes*/
    public synchronized eliminaDeCola(message){
        for( def x = 0 ; x< cola_mensajes.size(); x++ ){
            def m = cola_mensajes[x];
            if( m == null ) continue;
            println "antes $m"
            if( m.estructura[0] == message.estructura[0] && m.estructura[1] == message.estructura[1] )
                cola_mensajes[x] = null;
        }
    }
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    public void entregarMensaje( Mensaje m){
        //window.addHistory( "Entregando mensaje", m.toString() );
        def pnl;
        //Dependiendo del tipo mandamos una u otra ventana
        if( window.tipo == MainWindow.TIPO_TEXTO){
            pnl = window.pnlTexto;
        }else if( window.tipo == MainWindow.TIPO_AUDIO){
            pnl = window.pnlAudio;
        }else if( window.tipo == MainWindow.TIPO_VIDEO){
            pnl = window.pnlVideo
        }else if( window.tipo == MainWindow.TIPO_CLIENTE){
            pnl = window.pnlCliente;
        }
        
        if( m.tipo == Mensaje.TIPO_VIDEO){
            println "debo de mostrar video en panel"
            ImageIcon i = new ImageIcon( m.img );
            JLabel lblImg = new JLabel("");
            lblImg.setIcon( i ) ;
            pnl.pnlRecepcionVideo.add(lblImg, 0);
            try{
                pnl.pnlRecepcionVideo.remove(1);
            }catch( Exception e){}
            lblImg.setVisible(true);
        }else if( m.tipo == Mensaje.TIPO_TEXTO){
            texto.showTexto( m, pnl );
        }
        else if(m.tipo == Mensaje.TIPO_AUDIO){
            AudioFormat format = new AudioFormat(16000.0F, 16, 1, true, false);
            try {
                System.out.println("Listening for incoming sound");
                DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo);
                speaker.open(format);
                speaker.start();                
               
                    byte[] data = m.audio;
                    //baos.reset();
                    ByteArrayInputStream bais = new ByteArrayInputStream(data);
                    AudioInputStream ais = new AudioInputStream(bais,format,data.length);
                    int numBytesRead = 0;
                    if ((numBytesRead = ais.read(data)) != -1) speaker.write(data, 0, numBytesRead);
                    ais.close();
                    bais.close();
                
                speaker.drain();
                speaker.close();
                System.out.println("Stopped listening for incoming sound");
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*
            AudioFormat format = new AudioFormat(8000.0F, 16, 1, true, false);
            //reproducir Audio
            ByteArrayInputStream oInstream = new ByteArrayInputStream(m.audio);
            AudioInputStream oAIS = AudioSystem.getAudioInputStream(oInstream, format, m.audio.length );
            try{
            //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("smb_stomp.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(oInstream);
            clip.start();
            }catch(Exception ex){
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
            }*/
        }
    }
    
    /** Ingresa mensajes no repetidos a la cola*/
    public synchronized addColaMensaje( message ){
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

