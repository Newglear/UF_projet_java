package chavardage.networkManager;

public class InstanceTest extends Thread{
    // classe pour vérifier si la façon dont j'ai implémenté les singletons pour les serveurs fonctionne




    private static int nbInstances=0;

    public InstanceTest() throws ServerAlreadyOpen {
        if (nbInstances!=0){
            ServerAlreadyOpen e = new ServerAlreadyOpen("UDPServeur");
            throw e;
        }
        nbInstances=1;
    }







}
