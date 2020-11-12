package lecture4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sumit on 12/01/17.
 *
 * ==>  Adding block synchronization by using custom locks, instead of using the intrinsic lock of the class.
 *
 *  Result : Both the list are having 2000 items as expected.
 *  Execution Time reduced to around 2.4 secs.
 *
 *
 *  Observation: One might think that why can't we use list1 and list2 objects itself as the locks.
 *  Well, in this case we can, but it is always a good practice to declare the locks separately.
 */

class Worker4 {
    private Random random = new Random();

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();


    public void stageOne () throws InterruptedException {
        synchronized (lock1) {    //Synchronizing only this block of code using the lock "lock1"
            Thread.sleep(1);     //Simulating an API call, or resource intensive task to generate a number.
            this.list1.add(random.nextInt(100));
        }
    }

    public void stageTwo () throws InterruptedException {
        synchronized (lock2) {      // Synchronizing only this block of code using the lock "lock2"
            Thread.sleep(1);     //Simulating an API call, or resource intensive task to generate a number.
            this.list2.add(random.nextInt(100));
        }
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

public class Demo4 {

    public static void main(String[] args) throws Exception {
        Worker4 worker4 = new Worker4();
        worker4.main();
    }

}
