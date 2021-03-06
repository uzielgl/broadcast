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
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
    public Comunicador comunicador;
    public boolean continuar = true;
    
    public Proceso proceso; //Es el que tiene VT y demas CI, 
    
    public int i = 0;
    
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
    
    
    public byte[] capturarImagen(int c){
        i++;
        Image img=null;
        FrameGrabbingControl fgc = (FrameGrabbingControl)
        player.getControl("javax.media.control.FrameGrabbingControl");
        Buffer buf = fgc.grabFrame();
        
        // creamos la imagen awt
        BufferToImage btoi = new BufferToImage((VideoFormat)buf.getFormat());
        img = btoi.createImage(buf);
        
        
        File file = new File("C:/Users/Uziel/Desktop/video/theimage"+i+".jpg");
        try {
            ImageIO.write((RenderedImage)img, "jpg", file);
        } catch (IOException ex) {
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
         String formato = "JPEG";
         /*// byte[] imageInByte;
          * try{
          * 
          * ImageIO.write((RenderedImage) img,formato,file);
          * 
          * }catch (IOException ioe){System.out.println("Error al guardar la imagen");}*/
                // File fnew=new File("/tmp/rose.jpg");
                BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            ImageIO.write(originalImage, "jpg", baos );
        } catch (IOException ex) {
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
                byte[] imageInByte=baos.toByteArray();


    //return Toolkit.getDefaultToolkit().createImage(img.getSource());
        return imageInByte;
        
       
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
                
                System.out.println("entra al run");
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
        //File fnew = new File(image);
        m.img = capturarImagen(cont);
        m.tipo = Mensaje.TIPO_VIDEO;
        System.out.println("mensaje video: "+m);
        proceso.difundirMensaje( m );
    }
    
   
    
    public void showVideo(byte[] bytesImage){
        System.out.println("Agregando imagen al panel");
        Image i = new ImageIcon(bytesImage).getImage();
       
        System.out.println("entra a ShowVideo, img: "+i);
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
