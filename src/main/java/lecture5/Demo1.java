package lecture5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by sumit on 13/01/17.
 *
 * Using Executor Service.
 * In the previous tutorials, when we directly instantiated thread and called "start" method on it, it should be noted that
 * thread creation and termination are very time consuming task, as they involve a lot of overhead.
 * Instead a pool of threads is used which is instantiated once and then terminated once all the task are done.
 *
 * ExecutorService takes care of Thread Pool pretty well.
 * You define the number of threads you want in the thread pool, and also define the max waiting time for all the task to finish.
 *
 * You then submit tasks in the ExecutorService which then looks at the thread which is currently free and assign the task to that thread.
 * Once the thread completes its task, it is then again available to be reused for another task. (But ExecutorService takes care of all this).
 *
 */

class Processor1 implements Runnable {
    private int id;

    public Processor1(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting Task : " + this.id + ". On Thread " + Thread.currentThread().getId());

        try {
            Thread.sleep(2000);  //Simulating some time consuming task, like calling an API, Reading a file, etc.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed Task : " + this.id + ". On Thread " + Thread.currentThread().getId());
    }
}

public class Demo1 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Processor1(i));
        }

        executorService.shutdown();   // Doesn't act immediately. It is a request that "When all the submitted tasks are finished, then terminate all the thread created."

        System.out.println("All taks submitted.   (Everything above was async.)");

        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);   //Wait for a day, so all the tasks are finished, after that terminate forcefully.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks completed.");

    }

}
