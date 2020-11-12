package lecture11;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sumit on 14/01/17.
 * <p>
 *
 * Using ReEntrant Lock to overcome the race condition issues in the last demo.
 *
 * Result:  Because of the order of taking lock on different lock object is different, we get a DeadLock.
 * Remember that this problem is not only related to multiple locks, but also with the nested synchronized blocks.
 *
 * One of the naive solution to this problem is to always acquire locks in the same order.
 */


class Account2 {
    private int balance = 10000;

    public void deposit (int amount) {
        this.balance += amount;
    }

    public void withdraw (int amount) {
        this.balance -= amount;
    }

    public int getBalance () {
        return this.balance;
    }

    public static void transfer (Account2 fromAcc, Account2 toAcc, int amount) {
        fromAcc.withdraw(amount);
        toAcc.deposit(amount);
    }

}


class Runner2 {

    private Account2 acc1 = new Account2();
    private Account2 acc2 = new Account2();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();


    public void firstMethod() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            lock1.lock();
            lock2.lock();
            try {
                Account2.transfer(acc1, acc2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondMethod() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            lock2.lock();    //The order is different here.  (If we lock1 and then lock2, there shouldn't be any problem, since lock1 will be the outer for both threads)
            lock1.lock();
            try {
                Account2.transfer(acc2, acc1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finishedAll() {
        System.out.println("Account2 1 balance : " + acc1.getBalance());
        System.out.println("Account2 2 balance : " + acc2.getBalance());
        System.out.println("Total balance : " + (acc1.getBalance() + acc2.getBalance()));
    }
}


public class Demo2 {

    public static void main(String[] args) throws Exception {

        Runner2 runner = new Runner2();
        Thread t1 = new Thread(runner::firstMethod);
        Thread t2 = new Thread(runner::secondMethod);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finishedAll();
    }


}
