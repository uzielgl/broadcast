/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package broadcastchat;
/**
 *
 * @author uzielgl
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class UDPServer extends Thread{
    private String ip;
    private String port;
    public List<ComunicadorListener> listeners = new ArrayList<ComunicadorListener>();
    
    MulticastSocket aSocket;
    
    UDPServer( String ip, String port ){
        ip = "228.5.6.7";
        port = "6789";
        
        this.ip = ip;
        this.port = port;
        
        try{
            aSocket = new MulticastSocket( Integer.parseInt( port ) );
            aSocket.joinGroup( InetAddress.getByName(ip) );
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }
    }
    UDPServer( String ip, int port){
        ip = "228.5.6.7";
        port = 6789;
        
        this.ip = ip;
        this.port = Integer.toString( port );  
        
        try{
            aSocket = new MulticastSocket(  port  );
            aSocket.joinGroup( InetAddress.getByName(ip) );
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }
    }
    
    private class ServerSocketThread implements Runnable{
        public void run(){
            Mensaje mensaje = new Mensaje();
            try{ 
                System.out.println("Levantando servidor en " + ip + " " + " puerto " + port);
                while(true){
                    byte[] buffer = new byte[80000];
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    aSocket.receive(request);
                   // aSocket.leaveGroup(InetAddress.getByName(ip));
                    //Deserializamos el objeto
                    ByteArrayInputStream objIn = new ByteArrayInputStream( request.getData() );
                    ObjectInputStream ois = new ObjectInputStream( objIn );
                    try{
                        mensaje = (Mensaje) ois.readObject();
                    }catch( Exception e){
                        println "Error en deserializar el mensaje:"
                        print e.getMessage();
                        e.printStackTrace();
                    }

                    for (ComunicadorListener cl : listeners) cl.onReceiveMessage( mensaje );
                    System.out.print("Recibiendo mensaje con UDP: ");
                    System.out.println( mensaje );
                    //System.out.println( new String( request.getData(), "UTF-8" ).trim() );
                } 
            }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
            }catch (IOException e) {System.out.println("IO: " + e.getMessage());}
        }
    }
    
    
    public void run(){
        Thread t = new Thread(new ServerSocketThread());
        t.start();
       
        
        /*Thread t5 = new Thread(new ServerSocketThread());
        t5.start();
        Thread t6 = new Thread(new ServerSocketThread());
        t6.start();
        Thread t7 = new Thread(new ServerSocketThread());
        t7.start();
        Thread t8 = new Thread(new ServerSocketThread());
        t8.start();*/
    }
    
    public void stopServer(){
        aSocket.close();
        aSocket.disconnect();
        aSocket = null;
        interrupt();
    }

} 
