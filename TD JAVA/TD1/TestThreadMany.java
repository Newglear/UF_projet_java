public class TestThreadMany {
    public static void main(String arg[]){
        int nombreT = Integer.valueOf(arg[0]);

        for(Integer i=1;i<=nombreT;i++){
            new MyThread("Thread #" + i.toString());
        }
    }

}