package lecture10;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sumit on 13/01/17.
 *
 * Using ReEntrant Lock.
 * They are different from the synchronized blocks, becuase they can hold the lock across multiple methods.
 * You take a lock in one method and unlock it in a different method. This is not possible with synchronized blocks.
 *
 */

class Runner {

    Lock lock = new ReentrantLock();

    private int count = 0;
    private void increment () {
        count++;
    }

    public void firstThread () {
        lock.lock();
        for (int i = 0; i < 10000; i++) {
            increment();
        }
        lock.unlock();
    }

    public void secondThread () {
        lock.lock();

        try {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        } finally {
            lock.unlock();                  // It is always recommended to place the unlock in the finally block, since
                                            // if your code raises exception, then also it should be able to release the lock.
        }

    }

    public void allFinished () {
        System.out.println("All Finished.");
    }
}

public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();

        Thread t1 = new Thread(runner::firstThread);
        Thread t2 = new Thread(runner::secondThread);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
