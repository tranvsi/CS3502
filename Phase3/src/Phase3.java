//Import package for locks
import java.util.concurrent.locks.ReentrantLock;
//Main class
public class Phase3 {
    //Main method
    public static void main(String[] args){
        //Created two bank accounts
        BankAccount account1 = new BankAccount(5000);
        BankAccount account2 = new BankAccount(5000);
        //Created two threads (one to transfer from account1 to account2 and the other doing the inverse)
        Thread thread1 = new Thread(() -> transfer(account1,account2,500), "Transfer1-to-2");
        Thread thread2 = new Thread(() -> transfer(account2,account1,500), "Transfer2-to-1");
        //Start both threads
        thread1.start();
        thread2.start();
    }
    //Bank Account class
    static class BankAccount {
        //Double to hold balance
        private double balance;
        //Lock object
        private final ReentrantLock lock = new ReentrantLock();
        //Constructor
        public BankAccount(double initialBalance){
            this.balance = initialBalance;
        }
        //Method to obtain the lock
        public ReentrantLock getLock(){
            return lock;
        }
        //Method to return balance
        public double getBalance(){
            return balance;
        }
        //Method to deposit money
        public void deposit(double amount){
            balance += amount;
        }
        //Method to withdraw money
        public void withdraw(double amount){
            balance -= amount;
        }
        //Override toString method to return the account hashcode
        @Override
        public String toString(){
            return "Account@" + hashCode();
        }
    }
    //Transfer class
    public static void transfer(BankAccount from, BankAccount to, double amount){
        //Obtain the locks from the "from" account and "to" account
        ReentrantLock fromLock = from.getLock();
        ReentrantLock toLock = to.getLock();

        //Attempt to lock the lock from the "from" account
        System.out.println(Thread.currentThread().getName() + " attempting to lock " + to);
        fromLock.lock();
        System.out.println(Thread.currentThread().getName() + " locked " + from);

        try {
            //Incorporate a delay to increase likelihood of deadlock
            Thread.sleep(500);

            //Attempt to lock the lock from the "to" account
            System.out.println(Thread.currentThread().getName() + " attempting to lock " + from);
            toLock.lock();
            System.out.println(Thread.currentThread().getName() + " locked " + to);

            //If the "to" account lock has been locked, perform the corresponding transaction
            try {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    System.out.println("Transferred " + amount + " from " + from + " to " + to);
                } else {
                    System.out.println("Not enough balance to transfer " + amount + " from " + from + " to " + to);
                }
            } finally {
                toLock.unlock();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            fromLock.unlock();
        }
    }
}
