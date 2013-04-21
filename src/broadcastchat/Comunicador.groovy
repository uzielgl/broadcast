/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package broadcastchat

/**
 *
 * @author uzielgl
 */
class Comunicador {
    public UDPServer udpServer;
    public UDPClient udpClient;
    String ip;
    int port;
    
    Proceso proceso;
    
    String subnet;
    
    Comunicador(String ip, int port){
        this.ip = ip;
        this.port = port;
        
        String[] ex = ip.split("\\.");
        subnet = ex[0] + "." + ex[1] + "." + ex[2] + ".";
        
        udpClient = new UDPClient();
        start();
    }
    
    /**
     * Sólo se enviarán objectos "Mensaje".
     */ ///
    public sendMessage(Proceso p, Mensaje m){
        println "en proceso";
        udpClient.sendMenssage( p.ip, p.port, m );
    }
    
    /**
     * Sólo se enviarán objectos "Mensaje".
     */ ///
    public sendMessage(BasicProceso p, Mensaje m){
        int i = 2;
        println "en basci proceso"
        println "proceso "  + p
        println "ip " + p.ip + " " + p.ip.getClass();
        println "port " + p.port + " " + p.port.getClass();
        println i.getClass();
        println m.getClass();
        udpClient.sendMenssage( p.ip, p.port, m );
    }
    
     /** Inicia el servidor del proceso*/
    public void start(){
        udpServer = new UDPServer(ip, port);
        udpServer.start();
    }
    
    /** Detiene el servidor del proceso*/
    public void stop(){
        udpServer.stopServer();
    }
    
    
    /** Envía un mensaje de "Aquí estoy " a todos los demás procesos */
    public void sendDiscoveryMessage(){
        Mensaje m = new Mensaje( Mensaje.TIPO_DESCUBRIMIENTO );
        m.from = new BasicProceso( proceso );
        this.udpClient.sendMessage( m);
    }
    
    /** Envía un mensaje de "Aquí estoy " a todos los demás procesos */
    public void sendDiscoveryMessage( String ip ){
        Mensaje m = new Mensaje( Mensaje.TIPO_DESCUBRIMIENTO );
        m.from = new BasicProceso( proceso );
        this.udpClient.sendMessage( m);
    }
	
}

