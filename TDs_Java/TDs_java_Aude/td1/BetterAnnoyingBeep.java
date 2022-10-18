import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;
/**
 * Schedule a task that executes once every second.
 */
public class BetterAnnoyingBeep {
    Toolkit toolkit;
    Timer timer;
    public BetterAnnoyingBeep() {
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, 5*1000);
    }
    class RemindTask extends TimerTask {
        int numWarningBeeps = 3;
        public void run() {
            long Debut = System.currentTimeMillis();
            if((scheduledExecutionTime() - Debut) < 5000){
                System.out.println(timer.toString());
                if (numWarningBeeps > 0) {
                    toolkit.beep();
                    System.out.println("Beep!");
                    numWarningBeeps--;
                } else {
                    toolkit.beep();
                    System.out.println("Time's up!");
                    //timer.cancel(); //Not necessary because we call System.exit
                    System.exit(0); //Stops the AWT thread (and everything else)
                }
            }else{
                System.out.println("Too late!");
                System.exit(0);
            }

        }
    }
    public static void main(String args[]) {
        System.out.println("About to schedule task.");
        new AnnoyingBeep();
        System.out.println("Task scheduled.");
    }
}