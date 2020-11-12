package lecture8;

import java.util.Scanner;

/**
 * Created by sumit on 13/01/17.
 * <p>
 * Using wait() and notify() methods.
 */

class Worker {
    public static Scanner scanner = new Scanner(System.in);

    public void produce() {
        try {
            synchronized (this) {
                System.out.println("Producer thread started...");

                wait();     //It will release the lock here and will wait until someone notifies.

                System.out.println("Producer resumed...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void consume() {
        try {
            Thread.sleep(2000);
            synchronized (this) {
                System.out.println("Waiting for return key");
                scanner.nextLine();
                System.out.println("Return key pressed.");
                notify();            // Just notifies other thread, that lock can be acquired. (Can be acquired, doesn't mean it will be acquired right now)

                Thread.sleep(3000);  //To demonstrate the the "wait" method will not move forward unless this lock is released completely.
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();

        Thread t1 = new Thread(worker::produce);
        Thread t2 = new Thread(worker::consume);


        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
