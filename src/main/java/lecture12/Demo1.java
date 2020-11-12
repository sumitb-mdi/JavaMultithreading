package lecture12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by sumit on 14/01/17.
 *
 * Simple Connection class demonstration without any new concept.
 *
 */


class Connection {
    private static Connection connection = new Connection();
    private int totalConnections = 0;

    public static Connection getConnection() {
        return connection;
    }

    private Connection () {

    }

    public void connect () {
        synchronized (this) {
            totalConnections += 1;
            System.out.println("Current Number of Connections = " + totalConnections);
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

public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();  //Will create new thread if not available, otherwise reuse from the cached pool.

        for (int i = 0; i < 200; i++) {
            executorService.submit(Connection.getConnection()::connect);
        }

        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
