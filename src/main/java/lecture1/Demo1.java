package lecture1;

/**
 * Created by sumit on 12/01/17.
 * In This example we will learn how to start thread by Extending "Thread" class.
 *
 */

class Runner extends Thread {
    @Override
    public void run() {
        for (int i = 0 ; i < 10; i++) {
            System.out.println("Extended Hello " + i);

            try {
                Thread.sleep(1000);   // simulating  computationally intensive.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Demo1 {
    public static void main(String[] args) {
        Runner runner1 = new Runner();
        runner1.start();   //Note: Here we are not calling "run" method, because calling it directly will start it in the main thread.
                        // "start" method actually runs the "run" method in separate thread.

        Runner runner2 = new Runner();
        runner2.start();
    }
}
