package lecture3;

/**
 * Created by sumit on 12/01/17.
 *
 * Mitigating the effect of shared variable between multiple thread in Demo1. Here we are using synchronized keyword.
 */
public class Demo2 {

    public int count = 0;

    public synchronized void incrementCount() {
        this.count++;
    }

    /* Theory :
        Every object in Java has internal locking mechanism using Mutex.
        Whenever you(thread) call a Synchronized method of that object, it needs to acquire that lock,
        so that no other thread can enter it, unless its work is done in the critical region.
    */
    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        demo2.doWork();
    }

    public void doWork () {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    incrementCount();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    incrementCount();
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
