import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Phase4 {
    public static void main(String[] args){
        //Created two bank accounts with starting balance of 5000
        BankAccount bank1 = new BankAccount(5000);
        BankAccount bank2 = new BankAccount(5000);
        //Created two threads (one for transferring from bank1 to bank2 and the other for the inverse)
        Thread thread1 = new Thread(() -> transfer(bank1, bank2, 550), "Transfer1to2");
        Thread thread2 = new Thread(() -> transfer(bank2, bank1, 1500), "Transfer2to1");
        //Start both threads
        thread1.start();
        thread2.start();
        //Try-Catch block to join threads before main thread continues
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        //Print the final balances after transfers
        System.out.println("Bank 1 balance: " + bank1.getBalance());
        System.out.println("Bank 2 balance: " + bank2.getBalance());
    }
    //Transfer method which utilizes lock ordering and timeouts
    public static void transfer(BankAccount from, BankAccount to, double amount){
        //Lock ordering mechanism to determine which account to lock first
        BankAccount firstAcc = from.compareTo(to) < 0 ? from:to;
        BankAccount secondAcc = from.compareTo(to) < 0 ? to:from;

        try {
            //Acquire lock for first account timeout of 5 seconds
            if (firstAcc.getLock().tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " locked " + firstAcc);

                try {
                    //Acquire lock for second account timeout of 5 seconds
                    if (secondAcc.getLock().tryLock(5, TimeUnit.SECONDS)) {
                        System.out.println(Thread.currentThread().getName() + " locked " + secondAcc);
                        //If sufficient balance, preform the necessary transactions
                        if (from.getBalance() >= amount){
                            from.withdraw(amount);
                            to.deposit(amount);
                            System.out.println("Moved $" + amount + " from " + from + " to " + to);
                        }
                        //Else, print an error statement regarding insufficient balance
                        else {
                            System.out.println("Insufficient balance to transfer from " + from + " to " + to);
                        }
                    }
                    //Print error statement if lock on second account is not acquired within 5 seconds
                    else {
                        System.out.println(Thread.currentThread().getName() + " failed to lock " + secondAcc);
                    }
                } finally {
                    //Always unlock second account if lock was acquired
                    if (secondAcc.getLock().isHeldByCurrentThread()) {
                        secondAcc.getLock().unlock();
                    }
                }
            }
            //Print error statement if lock on first account is not acquired within 5 seconds
            else {
                System.out.println(Thread.currentThread().getName() + " failed to lock " + firstAcc);
            }
        } catch (InterruptedException e){
            //Handle interruptions
            e.printStackTrace();
        } finally {
            //Unlock the lock for first account if it was acquired
            if (firstAcc.getLock().isHeldByCurrentThread()) {
                firstAcc.getLock().unlock();
            }
        }
    }
    //Bank Account class
    static class BankAccount implements Comparable<BankAccount>{
        //Double to hold balance
        private double balance;
        //Every object of bank account will have a lock
        private final ReentrantLock lock = new ReentrantLock();
        //Default constructor
        public BankAccount(double startingBalance){
            this.balance = startingBalance;
        }
        //Getter to retrieve lock
        public ReentrantLock getLock(){
            return lock;
        }
        //Getter to retrieve balance
        public double getBalance(){
            return balance;
        }
        //Deposit method
        public void deposit(double amount){
            balance += amount;
        }
        //Withdraw method
        public void withdraw(double amount){
            balance -= amount;
        }
        //Override compareTo method to compare accounts based on their hashcode
        @Override
        public int compareTo(BankAccount other){
            return System.identityHashCode(this) - System.identityHashCode(other);
        }
        //Override toString method to display unique identifier for account
        @Override
        public String toString(){
            return "Account@" + hashCode();
        }
    }
}
