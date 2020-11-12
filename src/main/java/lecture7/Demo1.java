package lecture7;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * Created by sumit on 13/01/17.
 * <p>
 * Usign Blocking Queues.
 * When in multi-threaded environment, the BlockingQueue implementation saves a lot of time and Code.
 * And also they are thread-safe.
 *
 *
 */

public class Demo1 {
    private BlockingQueue queue = new ArrayBlockingQueue<>(10);         //Max Size of the Queue : 10

    public static void main(String[] args) throws InterruptedException {

        Demo1 demo1 = new Demo1();

        Thread t1 = new Thread(demo1::producer);
        Thread t2 = new Thread(demo1::consumer);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }

    private void producer() {
        Random random = new Random();

        while (true) {
            try {
                this.queue.put(random.nextInt(100));        // This is also a blocking operation.
                                                            // Once the queue max size is reached, it will wait, until some thread removes the item from queue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void consumer() {
        Random random = new Random();

        while (true) {

            try {
                Thread.sleep(100);
                if (random.nextInt(10) < 2) {
                    Integer takenValue = (Integer)this.queue.take();              // This is thread safe blocking operation.
                                                    // Means that if the Queue is empty, then it will wait untill there is an element to consume in queue.
                                                    // It won't throw exception when the queue is empty.

                    System.out.println("Taken value = " + takenValue);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
