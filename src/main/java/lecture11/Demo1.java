package lecture11;

import java.util.Random;

/**
 * Created by sumit on 14/01/17.
 * <p>
 *  Simple class - demonstrating again the mess of multithreading.
 *  The total amount should be 20000 but it won't be, since we have got race conditions and all.
 */


class Account {
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

    public static void transfer (Account fromAcc, Account toAcc, int amount) {
        fromAcc.withdraw(amount);
        toAcc.deposit(amount);
    }

}


class Runner {

    private Account acc1 = new Account();
    private Account acc2 = new Account();



    public void firstMethod() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            Account.transfer(acc1, acc2, random.nextInt(100));
        }
    }

    public void secondMethod() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            Account.transfer(acc2, acc1, random.nextInt(100));
        }
    }

    public void finishedAll() {
        System.out.println("Account 1 balance : " + acc1.getBalance());
        System.out.println("Account 2 balance : " + acc2.getBalance());
        System.out.println("Total balance : " + (acc1.getBalance() + acc2.getBalance()));
    }
}


public class Demo1 {

    public static void main(String[] args) throws Exception {

        Runner runner = new Runner();
        Thread t1 = new Thread(runner::firstMethod);
        Thread t2 = new Thread(runner::secondMethod);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finishedAll();
    }


}
