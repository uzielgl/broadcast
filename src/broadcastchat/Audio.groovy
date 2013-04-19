/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package broadcastchat

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

class Audio extends Thread{
    
    private MediaLocator ml = new MediaLocator("javasound://44100");
private DatagramSocket socket;
private boolean transmitting;
private Player player;
TargetDataLine mic;
public byte[] buffer;
private AudioFormat format;
public String ip;// ip usadada en el datagramPacket
public Proceso proceso;


private DatagramSocket datagramSocket(){
    try {
        return new DatagramSocket();
    } catch (SocketException ex) {
        return null;
    }
}

private void startMic() {
    try {
        format = new AudioFormat(8000.0F, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        mic = (TargetDataLine) AudioSystem.getLine(info);
        mic.open(format);
        mic.start();
        buffer = new byte[1024];
    } catch (LineUnavailableException ex) {
       //s Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private Player createPlayer() {
    try {
        return Manager.createRealizedPlayer(ml);
    } catch (IOException ex) {
        return null;
    } catch (NoPlayerException ex) {
        return null;
    } catch (CannotRealizeException ex) {
        return null;
    }
}

private void send() {
    try {
        mic.read(buffer, 0, 1024);
        DatagramPacket packet = 
            new DatagramPacket(
               buffer, buffer.length, InetAddress.getByName(ip), 91);
        socket.send(packet);
    } catch (IOException ex) {
        //Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
    }
}

@Override
public void run() {
    player = createPlayer();
    player.start();
    socket = datagramSocket();
    transmitting = true;
    startMic();
    while (transmitting) {
       enviarAudioAProcesos();
    }  
}

private byte[] obtenerBuffer() {
    //starMic();
    try {
        mic.read(buffer, 0, 1024);
        /*DatagramPacket packet = 
            new DatagramPacket(
               buffer, buffer.length, InetAddress.getByName(ip), 91);
        socket.send(packet);*/
    } catch (IOException ex) {
        println "error obtenerBuffer"
        println ex.getMessage()
        //Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
    }
    return buffer;
}

public void enviarAudioAProcesos(){
        Mensaje m = proceso.crearMensaje();
        m.audio = obtenerBuffer();
        m.tipo = Mensaje.TIPO_AUDIO;
        proceso.difundirMensaje(m);
    }
	
}

