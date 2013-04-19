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
    
    DatagramSocket aSocket;
    
    UDPServer( String ip, String port ){
        this.ip = ip;
        this.port = port;
        
        try{
            aSocket = new DatagramSocket( Integer.parseInt( port ) );
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }
    }
    UDPServer( String ip, int port){
        this.ip = ip;
        this.port = Integer.toString( port );  
        
        try{
            aSocket = new DatagramSocket(  port  );
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }
    }
    
    private class ServerSocketThread implements Runnable{
        public void run(){
            Mensaje mensaje = new Mensaje();
            try{ 
                System.out.println("Levantando servidor en " + ip + " " + " puerto " + port);
                while(true){
                    byte[] buffer = new byte[1000];
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    aSocket.receive(request);

                    //Deserializamos el objeto
                    ByteArrayInputStream objIn = new ByteArrayInputStream( request.getData() );
                    ObjectInputStream ois = new ObjectInputStream( objIn );
                    try{
                        mensaje = (Mensaje) ois.readObject();
                    }catch( Exception e){
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
        Thread t2 = new Thread(new ServerSocketThread());
        t2.start();
        Thread t3 = new Thread(new ServerSocketThread());
        t3.start();
        Thread t4 = new Thread(new ServerSocketThread());
        t4.start();
        Thread t5 = new Thread(new ServerSocketThread());
        t5.start();
        Thread t6 = new Thread(new ServerSocketThread());
        t6.start();
        Thread t7 = new Thread(new ServerSocketThread());
        t7.start();
    }
    
    public void stopServer(){
        aSocket.close();
        aSocket.disconnect();
        aSocket = null;
        interrupt();
    }

} 
