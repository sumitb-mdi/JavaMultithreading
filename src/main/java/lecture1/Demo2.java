package lecture1;

/**
 * Created by sumit on 12/01/17.
 * In this example we will use the Runnable Interface.
 */

class Runner2 implements Runnable {
    public void run() {
        for (int i = 0 ; i < 10; i++) {
            System.out.println("Implemented Hello " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class Demo2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner2());   //Ofcourse you can use Anonymous class if you don't intend to reuse the code inside it.
        Thread t2 = new Thread(new Runner2());

        t1.start();
        t2.start();
    }
}
