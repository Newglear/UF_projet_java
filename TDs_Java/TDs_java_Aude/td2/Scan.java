import java.net.*; 

class Scan{

    static int portMin = 1; 
    static int portMax = 1026; 
    
    static Boolean PortIsFree(int i){
        // si on arrive à créer un ServerSocket, c'est que le port est libre 
        try {
            ServerSocket socket = new ServerSocket(i); 
            socket.close(); 
            return true; 
        } catch (Exception e) {
            return false; // sinon il est occupé 
        }   
    }

    public static void main(String args[]){
        for (int i = portMin; i <=portMax; i++){
            if (PortIsFree(i)) {
                System.out.println("le port " + i + " est libre");
            }
            else {
                System.out.println("le port "+ i + " est occupé" );
            }
        }

    }
    
}