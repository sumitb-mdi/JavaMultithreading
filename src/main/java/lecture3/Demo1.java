package lecture3;

/**
 * Created by sumit on 12/01/17.
 *
 * Demonstrating the mess of shared variable between multiple threads.
 */
public class Demo1 {

    public int count = 0;

    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        demo1.doWork();
    }

    public void doWork () {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    count++;
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    count++;                        //This is not an atomic operation.   There are three steps (count = count + 1) ==>  get, add, store.
                }
            }
        });

        t1.start();
        t2.start();

        System.out.println("Current Count = " + this.count);        //This will output some random number. Since we didn't waited for threads to finish.

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Now the count = " + this.count);        // Here also the output will be unexpected. Since threads are writing to a shared space.

    }
}
