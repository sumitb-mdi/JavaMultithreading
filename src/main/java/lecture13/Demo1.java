package lecture13;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sumit on 14/01/17.
 *
 * Using Callable interface instead of runnable.
 *
 */
public class Demo1 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();


        executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(4000);

                if (duration > 2000) {
                    throw new IOException("Sleeping for too long.");
                }
                System.out.println("Starting ... ");

                Thread.sleep(duration);

                System.out.println("Completed ... ");

                return duration;
            }
        });



        executorService.shutdown();


    }

}
