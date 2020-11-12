package lecture13;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by sumit on 14/01/17.
 *
 * Using Future interface to get result from Callable and Handling Exception of the "call" method.
 *
 */
public class Demo2 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();


        Future<Integer> future = executorService.submit(new Callable<Integer>() {

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

        try {
            System.out.println("Slept for  : " + future.get());   // NOTE: "get()" is a synchronous method.  Code will wait until the thread finishes the execution and returns the value.
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {    //Comes down to this, when the internal thread throws exception.
            e.printStackTrace();
        }

        System.out.println("Code Done.");
    }

}
