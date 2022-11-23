public class TestThread {

    public static void main (String arg[]){
            MyThread t1, t2;
            t1= new MyThread("Gwen");
            t2= new MyThread("Romain");
            t1.start();
            t2.start();
    }

}