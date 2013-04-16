/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package broadcastchat
import com.google.gson.Gson;
/**
 *
 * @author uzielgl
 * Este objeto se hace para poder serializarlo y enviarlo
 */
class BasicProceso implements Serializable{
    public int id; //Id del proceso
    public String ip; //Ip del proceso
    public int port;
    
    public BasicProceso(){}
    
    public BasicProceso(Proceso proceso){
        id= proceso.id;
        ip = proceso.ip;
        port = proceso.port;
    }
    
    public String toString(){
        def props = [
            "id" : id,
            "ip" : ip,
            "port" : port
        ]
        return new Gson().toJson( props );
    }
    
    
}

