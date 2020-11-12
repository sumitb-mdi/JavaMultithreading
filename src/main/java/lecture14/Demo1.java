package lecture14;

/**
 * Created by sumit on 14/01/17.
 *
 * Interrupted Exception.
 *
 */
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Starting ...");
                for (int i = 0; i < 1E8; i++) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("Thread Interrupted...");
//                        e.printStackTrace();
                        break;
                    }
                }
                System.out.println("Finished ...");
            }
        });


        thread.start();

        Thread.sleep(500);

        thread.interrupt();             //forcefully interrupting the thread.

        thread.join();
    }
}
