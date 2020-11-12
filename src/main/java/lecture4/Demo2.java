package lecture4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sumit on 12/01/17.
 * Let's add threads.
 *
 * Result: Most of the times, both the list wont contain 2000 items at the end.
 * There can also be exceptions.
 */

class Worker2 {
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

public class Demo2 {

    public static void main(String[] args) throws Exception {
        Worker2 worker2 = new Worker2();
        worker2.main();
    }

}
