/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package broadcastchat;

import java.util.logging.Logger;
import javax.media.*;
import javax.media.cdm.CaptureDeviceManager;
import java.io.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author Dai
 */
public class Video extends Thread{
    private String dispositivo = "vfw:Microsoft WDM Image Capture (Win32):0";
    private Player player = null;
    public ArrayList<BasicProceso> procesos;
    public Comunicador comunicador;
    public boolean continuar = true;
    
    Proceso proceso; //Es el que tiene VT y demas CI, 
    
    public Component Componente(){
    Component componente_video;
        try {
            //obtenemos el dispositivo;
            CaptureDeviceInfo device = CaptureDeviceManager.getDevice(dispositivo);
            MediaLocator localizador = device.getLocator();
            player = Manager.createRealizedPlayer(localizador);
            player.start();
        }
        catch(IOException ex){
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(NoPlayerException ex){
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(CannotRealizeException ex){
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
        //si se puede crear el player
        if((componente_video = player.getVisualComponent()) != null){
            componente_video.setSize(320, 240);
            return componente_video;
        }
        else{
            JOptionPane.showMessageDialog(null, "No se pudo crear el video");
            return null;
        }
    }
    
    
    public Image capturarImagen(){
        Image img=null;
        FrameGrabbingControl fgc = (FrameGrabbingControl)
        player.getControl("javax.media.control.FrameGrabbingControl");
        Buffer buf = fgc.grabFrame();
        // creamos la imagen awt
        BufferToImage btoi = new BufferToImage((VideoFormat)buf.getFormat());
        img = btoi.createImage(buf);
        return img;
    }
    
    public void run(){
        while( true ){
            while( continuar ){
                enviarImagenAProcesos();
                
                try{
                    Thread.sleep(41);
                }catch( Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    
    public void enviarImagenAProcesos(){
        for(BasicProceso p: this.procesos){
            Mensaje m = proceso.crearMensaje();
            m.img = capturarImagen();
            comunicador.sendMessage( p, m);
        }
    }
    
    public void showVideo(Image i){
        System.out.println("Agregando imagen al panel");
        
        if( proceso.window.pnlAudio != null){
            //Agregar la imagen al pnl ese
            //proceso.window.pnlAudio.pnlRecepcionVideo.add( i. );
        }
        
        if( proceso.window.pnlTexto != null){
            //Agregar la imagen al pnl ese
            //proceso.window.pnlAudio.pnlRecepcionVideo.add( i. );
        }
        
        if( proceso.window.pnlCliente != null){
            //Agregar la imagen al pnl ese
            //proceso.window.pnlAudio.pnlRecepcionVideo.add( i. );
        }
    }
    
    
    public void stopSendVideo(){
        continuar = false;
    }
    
    public void startSendVideo(){
        continuar = true;
    }
    
    
    
}
