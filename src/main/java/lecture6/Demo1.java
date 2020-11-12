package lecture6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sumit on 13/01/17.
 *
 * Using CountDownLatch.
 *
 * CountDownLatch is a thread safe counter available in Java Concurrent Package.
 * It can be used when a thread or main thread needs to wait for all the other threads to complete their tasks
 * and then proceed further.
 *
 * Eg. Usage: In a backend service, when multiple threads a processing a document and the main thread is waiting for all the threads to finish
 * their tasks and finally when all finishes, it marks the task as done in the DB.
 * (Not a good design, but you get the idea)
 *
 */

class Processor2 implements Runnable {
    private CountDownLatch latch;

    public Processor2(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Started : ");

        try {
            Thread.sleep(2000);   //Simulating some long task.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch.countDown();
    }
}

public class Demo1 {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);               // This object is thread safe, so no need to define explicit Lock or so.
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executorService.submit(new Processor2(latch));
        }
        try {
            latch.await();                      //Wait till the latch reaches Zero.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All tasks completed.");

    }
}
