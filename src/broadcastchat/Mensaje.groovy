/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package broadcastchat
import com.google.gson.Gson;
import java.io.*;
import java.awt.*;
/**
 *
 * @author uzielgl
 */
class Mensaje implements Serializable{
    public static final int TIPO_AUDIO = 1;
    public static final int TIPO_VIDEO = 2;
    public static final int TIPO_TEXTO = 3;
    public static final int TIPO_DESCUBRIMIENTO = 4;
    public int tipo;
    
    public BasicProceso from;
    public BasicProceso to;
    
    //Para videos
    public Image img;
    
    public Mensaje( int tipo){
        this.tipo = tipo;
    }
    
    public Mensaje(){
        
    }
    
    public String toString(){
        def mensaje = [
            "from" : from.toString(),
            "to" : to.toString(),
            "tipo" : tipo
        ];
        return new Gson().toJson( mensaje );
    }
	
}

