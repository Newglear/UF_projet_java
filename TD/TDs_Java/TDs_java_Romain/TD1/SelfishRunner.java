public class SelfishRunner extends Thread {
    private int tick = 1;
    private int num;
    public SelfishRunner(int num) {
        this.num = num;
    }
    public void run() {
        while (tick < 400000) {
            tick++;
            try{
                Thread.sleep(0);
            } catch(InterruptedException e){}
            if ((tick % 50000) == 0){
                System.out.println("Thread #" + num + ", tick = " + tick);
            }
        }
    }
}
