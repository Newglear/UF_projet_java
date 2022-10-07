public class TestThreadMany {

    public static void main (String arg[]){
        for (int i = 0; i < Integer.parseInt(arg[0]); i++){
            MyThread t = new MyThread("Thread #"+String.valueOf(i));
            t.start();
        }
    }

}