class MyThread extends Thread {
    public MyThread(String name){
        super(name);
        start();
    }

    public void run(){
        System.out.println("Hello, I am " + getName());
    }
}