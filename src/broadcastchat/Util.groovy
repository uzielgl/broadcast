/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package broadcastchat

/**
 *
 * @author uzielgl
 */
class Util {
    
    public static String getLocalIp(){
        String myIP = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            myIP = addr.getHostAddress();       
            // Bonus. Get your hostname.
            String myHost = addr.getHostName();
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host: "+e);
        }
        return myIP;
    }
    
    /** Le hace ping a una ip y regresa true si responde*/
    public static boolean checkIp( String ip){
        int timeout=1500;
        try {
            if (InetAddress.getByName(ip).isReachable(timeout)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Unknown Host: "+e);
        }
        return false;
    }
    
    /** Chequea que ips están vivas con el reachable*/
    public static void checkHosts(String subnet){
        int from = 100;
        int to = 110;
        int timeout=1500;
        for (int i=from;i<to;i++){
            String host=subnet + "." + i;
            try {
                if (InetAddress.getByName(host).isReachable(timeout)){
                    System.out.println(host + " is reachable");
                }else{
                    System.out.println( host + " no recheable"); 
                }
            } catch (Exception e) {
                System.out.println("Unknown Host: "+e);
            }
        }
    }
    
}

