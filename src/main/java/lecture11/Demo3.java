package lecture11;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sumit on 14/01/17.
 * <p>
 *
 * Using tryLock feature of ReEntrant Lock and making custom method to acquire locks.
 *
 * 
 */


class Account3 {
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

    public static void transfer (Account3 fromAcc, Account3 toAcc, int amount) {
        fromAcc.withdraw(amount);
        toAcc.deposit(amount);
    }

}


class Runner3 {

    private Account3 acc1 = new Account3();
    private Account3 acc2 = new Account3();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();


    private void acquireLocks (Lock firstLock, Lock secondLock) {
        boolean gotFirstLock = false;
        boolean gotSecondLock = false;

        while (true) {
            try {
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            } finally {
                if (gotFirstLock && gotSecondLock) {
                    return;
                }

                if (gotFirstLock) firstLock.unlock();
                if (gotSecondLock) secondLock.unlock();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void firstMethod() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            acquireLocks(lock1, lock2);
            try {
                Account3.transfer(acc1, acc2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondMethod() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            acquireLocks(lock2, lock1);
            try {
                Account3.transfer(acc2, acc1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finishedAll() {
        System.out.println("Account3 1 balance : " + acc1.getBalance());
        System.out.println("Account3 2 balance : " + acc2.getBalance());
        System.out.println("Total balance : " + (acc1.getBalance() + acc2.getBalance()));
    }
}


public class Demo3 {

    public static void main(String[] args) throws Exception {

        Runner3 runner = new Runner3();
        Thread t1 = new Thread(runner::firstMethod);
        Thread t2 = new Thread(runner::secondMethod);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finishedAll();
    }


}
