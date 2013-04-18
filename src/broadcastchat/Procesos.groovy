/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package broadcastchat

/**
 *
 * @author uzielgl
 */
 /** Manejara todos los procesos */
class Procesos{
    public ArrayList<BasicProceso> procesos = new ArrayList<BasicProceso>();

    public void add( BasicProceso proceso){
        //Está el proceso ? 
        boolean esta = false;
        for( BasicProceso p: procesos ){
            if( p.id == proceso.id ){
                esta = true;
                break;
            }
        }
        if( !esta )
            this.procesos.add( proceso );
    }

    public Proceso getById( int id ){
        for( BasicProceso p: procesos ) if( p.id == id ) return p;
        return null;
    }
}