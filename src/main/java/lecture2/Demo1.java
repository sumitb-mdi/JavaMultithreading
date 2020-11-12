package lecture2;

import java.util.Scanner;

/**
 * Created by sumit on 12/01/17.
 * Basic Thread Synchronization.   ( Using "volatile" Keyword )
 */

class Processor extends Thread {
    private volatile boolean running = true;
    // In some platforms this is highly possible that this thread when starts, caches the value of "running"
    // Because it expects that other threads (Like main thread) won't be altering this thread state.
    // By supplying the Volatile keyword infront of our variable, we make sure that this code works on all the platforms.
    // Because now since the variable is volatile, thread don't cache the values.

    @Override
    public void run() {
        while (this.running) {
            System.out.println("Hello");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown () {
        System.out.println("Shuting down");

        this.running = false;
    }
}

public class Demo1 {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Processor processor1 = new Processor();
        processor1.start();

        System.out.println("Hit Enter to stop : ");
        scanner.nextLine();     //Waiting here for input.

        processor1.shutDown();
    }


}
