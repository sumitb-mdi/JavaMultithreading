package lecture12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by sumit on 14/01/17.
 *
 * Let's try to limit the total number of connections to 10.
 *
 * Result : Now after this semaphore, there will be batch of 10 connections at a time.
 *
 */


class Connection2 {
    private static Connection2 connection = new Connection2();
    private int totalConnections = 0;
    private Semaphore semaphore = new Semaphore(10);    //Max 10 acquires without a release.
                                // new Semaphore(10, true);   // the last parameter is "fair", if true, then the first thread which aquired lock will have to release.

    public static Connection2 getConnection2() {
        return connection;
    }

    private Connection2 () {

    }

    public void connect () {
        try {
            semaphore.acquire();
            doConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public void doConnect () {
        synchronized (this) {
            totalConnections  += 1;
            System.out.println("Current Number of Connection2s = " + totalConnections);
        }

        try {
            Thread.sleep(2000); //simulating time consuming work here, after the connections.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            totalConnections -= 1;
        }
    }
}

public class Demo2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();  //Will create new thread if not available, otherwise reuse from the cached pool.

        for (int i = 0; i < 200; i++) {
            executorService.submit(Connection2.getConnection2()::connect);
        }

        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
