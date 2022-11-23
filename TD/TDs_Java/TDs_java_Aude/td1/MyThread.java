


class MyThread extends Thread{

    MyThread (String name){
        super(name);
    }



    public void run(){
        System.out.println("Hello, I am " + this.getName());

    }

}