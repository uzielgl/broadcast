/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package broadcastchat;

/**
 *
 * @author uzielgl
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.*;
import java.io.ByteArrayOutputStream;
/**
 *
 * @author uzielgl
 */
public class UDPClient {
      
    public void sendMessage(String ip, int port, Mensaje m){
        //Lo convertimos a bytes
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream os;
        try {
            os = new ObjectOutputStream(bytes);
            os.writeObject( m );  
            os.close();
        } catch (IOException ex) {
            System.out.println("Error de ioexception en UDPClient:sendMessage");
            ex.printStackTrace();
        }
        byte[] messageBytes = bytes.toByteArray();
        
        //Y se enviamos ese arreglo en el datagrama
        try{
            DatagramSocket aSocket = new DatagramSocket();    
            DatagramPacket messageOut = new DatagramPacket(messageBytes, messageBytes.length, 
               InetAddress.getByName(ip), port);
            aSocket.send( messageOut );
            
            aSocket.close();
            System.out.print("Enviando mensaje con UDP: ");
            System.out.println( m );
        }catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        }
    }
      
    public Mensaje sendMessageReply(String ip, int port, Mensaje m){
        //Lo convertimos a bytes
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream os;
        Mensaje response_message = new Mensaje();
        try {
            os = new ObjectOutputStream(bytes);
            os.writeObject( m );  
            os.close();
        } catch (IOException ex) {
            System.out.println("Error de ioexception en UDPClient:sendMessage");
            ex.printStackTrace();
        }
        byte[] messageBytes = bytes.toByteArray();
        
        //Y se enviamos ese arreglo en el datagrama
        try{
            DatagramSocket aSocket = new DatagramSocket();   
            aSocket.setSoTimeout( 500 );
            DatagramPacket messageOut = new DatagramPacket(messageBytes, messageBytes.length, 
               InetAddress.getByName(ip), port);
            aSocket.send( messageOut );
            System.out.print("Enviando mensaje con UDP: ");
            
            //Esperamos la réplica
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
            aSocket.receive(reply);
            System.out.println("Recibiendo la replica en UDP");
            
            //La deserializamos
            ByteArrayInputStream objIn = new ByteArrayInputStream( reply.getData() );
            ObjectInputStream ois = new ObjectInputStream( objIn );
            try{
                Mensaje mensaje = (Mensaje) ois.readObject();
                response_message = mensaje;
            }catch( Exception e){
            }
            aSocket.close();
            
            System.out.println( m );
        }catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){
            System.out.println( );
            System.out.println("IO: " + e.getMessage());
        }
        return response_message;
    }

    
}