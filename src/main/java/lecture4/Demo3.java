package lecture4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sumit on 12/01/17.
 * Let's threads synchronization  : Adding "synchronized" keyword infront of both the methods "stageOne" and "stageTwo".
 *
 *  Result : Everything is fine, the list are having the expected number of items.
 *  But Execution time is still around ~4.8 secs  (We expected it to be around 2 secs, since we are using two threads.)
 *
 *  Reason : Because of the intrinsic locks.
 *  The problem is that the lock is available only for the instance of that object and not for each method.
 *  So down here, if one of the thread got lock to work on "stageOne", the other thread won't be able to work on "stageTwo" either.
 *  Although "stageTwo" doesn't changes any states which "stageOne" changes and vice versa.
 *
 */

class Worker3 {
    private Random random = new Random();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();


    public synchronized void stageOne () throws InterruptedException {
        Thread.sleep(1);     //Simulating an API call, or resource intensive task to generate a number.
        this.list1.add(random.nextInt(100));
    }

    public synchronized void stageTwo () throws InterruptedException {
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

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();


        long endTime = System.currentTimeMillis();
        System.out.println("List1 size :" + this.list1.size());
        System.out.println("List2 size :" + this.list2.size());
        System.out.println("Total Time : " + (endTime - startTime));
    }
}

public class Demo3 {

    public static void main(String[] args) throws Exception {
        Worker3 worker3 = new Worker3();
        worker3.main();
    }

}
