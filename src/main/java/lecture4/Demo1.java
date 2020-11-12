package lecture4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sumit on 12/01/17.
 * Without Threads.  (Everything running in the single main thread.)
 *
 *  Adding 2000 items in each list.   ==> Execution time : ~ 4800 milli sec (4.8 secs)
 */

class Worker1 {
    private Random random = new Random();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();


    public void stageOne () throws InterruptedException {
        Thread.sleep(1);     //Simulating an API call, or resource intensive task to generate a number.
        this.list1.add(random.nextInt(100));
    }

    public void stageTwo () throws InterruptedException {
        Thread.sleep(1);     //Simulating an API call, or resource intensive task to generate a number.
        this.list2.add(random.nextInt(100));
    }

    public void process () throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void main () throws InterruptedException {
        long startTime = System.currentTimeMillis();

        process();

        process();

        long endTime = System.currentTimeMillis();
        System.out.println("List1 size :" + this.list1.size());
        System.out.println("List2 size :" + this.list2.size());
        System.out.println("Total Time : " + (endTime - startTime));
    }
}

public class Demo1 {

    public static void main(String[] args) throws Exception {
        Worker1 worker1 = new Worker1();
        worker1.main();
    }

}
