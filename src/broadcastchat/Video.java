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
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author Dai
 */
public class Video extends Thread{
    private String dispositivo = "vfw:Microsoft WDM Image Capture (Win32):0";
    private Player player = null;
    public Procesos procesos;
    public Comunicador comunicador;
    public boolean continuar = true;
    
    public Proceso proceso; //Es el que tiene VT y demas CI, 
    
    public Component Componente(){
    Component componente_video;
        try {
            //obtenemos el dispositivo;
            CaptureDeviceInfo device = CaptureDeviceManager.getDevice(dispositivo);
            MediaLocator localizador = new MediaLocator("vfw://0");
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
    
    
    public Image capturarImagen(int c){
        Image img=null;
        FrameGrabbingControl fgc = (FrameGrabbingControl)
        player.getControl("javax.media.control.FrameGrabbingControl");
        Buffer buf = fgc.grabFrame();
        // creamos la imagen awt
        BufferToImage btoi = new BufferToImage((VideoFormat)buf.getFormat());
        img = btoi.createImage(buf);
        File file = new File("C:/Users/Uziel/Desktop/video/theimage"+c+".jpg");
        /* ImageIO.write(img, "jpg", file);*/
         String formato = "JPEG";
                 try{
                   ImageIO.write((RenderedImage) img,formato,file);
                }catch (IOException ioe){System.out.println("Error al guardar la imagen");}
        return img;
        
        
    }
    
    public void iniciarVideo(){
        try{
        CaptureDeviceInfo device = CaptureDeviceManager.getDevice(dispositivo);
        MediaLocator localizador = new MediaLocator("vfw://0");
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
        int cont = 1;
        Mensaje m = proceso.crearMensaje();
        m.img = capturarImagen(cont);
        m.tipo = Mensaje.TIPO_VIDEO;
        proceso.difundirMensaje( m );
    }
    
    public void showVideo(Image i){
        System.out.println("Agregando imagen al panel");
        
        if( proceso.window.pnlAudio != null){
            JLabel lblImg = new JLabel("");
            lblImg.setIcon((Icon) i);
            proceso.window.pnlAudio.pnlRecepcionVideo.add(lblImg);
            lblImg.setVisible(true);
            //Agregar la imagen al pnl ese
           // proceso.window.pnlAudio.pnlRecepcionVideo.set
        }
        
        if( proceso.window.pnlTexto != null){
            JLabel lblImg = new JLabel("");
            lblImg.setIcon((Icon) i);
            proceso.window.pnlTexto.pnlRecepcionVideo.add(lblImg);
            lblImg.setVisible(true);
            //Agregar la imagen al pnl ese
            //proceso.window.pnlAudio.pnlRecepcionVideo.add( i. );
        }
        
        if( proceso.window.pnlCliente != null){
            JLabel lblImg = new JLabel("");
            lblImg.setIcon((Icon) i);
            proceso.window.pnlCliente.pnlRecepcionVideo.add(lblImg);
            lblImg.setVisible(true);
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
