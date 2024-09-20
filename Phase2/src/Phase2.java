//Import Reentrant lock to implement mutual exclusion
import java.util.concurrent.locks.ReentrantLock;
public class Phase2 {
    //Main method
    public static void main(String[] args){
        //Create new bank account object
        BankAccount bank = new BankAccount();
        //Create a thread for deposits
        Thread deposit = new Thread(() -> {
            //Uses for loop that continuously deposits 20 times
            for (int i = 0; i < 20; i++){
                bank.deposit(10);
            }
        }, "DepositThread");
        //Create a thread for withdrawls
        Thread withdraw = new Thread(() -> {
            //Uses a for loop that continuously withdraws 15 times
            for (int i = 0; i < 15; i++){
                bank.withdraw(5);
            }
        }, "WithdrawThread");
        //Start deposit thread
        deposit.start();
        //Start withdraw thread
        withdraw.start();
        //Try-catch block to wait for the threads to finish before main thread terminates
        try {
            deposit.join();
            withdraw.join();
        } catch (InterruptedException e){
            System.err.println("Main thread error");
        }
        //Return final balance within the bank account
        System.out.println("Final balance: $" + bank.returnBalance());
        System.exit(0);
    }
    //Bank Account class
    static class BankAccount{
        //private double variable to hold balance
        private double balance;
        //Create new lock object within the bank account class
        private final ReentrantLock lock = new ReentrantLock();
        //Deposit method
        public void deposit(double money){
            //Obtains lock when first run
            lock.lock();
            //Deposits money and sleeps for 100ms
            try {
                double newBalance = balance + money;
                Thread.sleep(100);
                balance = newBalance;
                System.out.println("Deposited $" + money + ". Current balance is $" + balance);
            } catch (InterruptedException e){
                System.out.println("Deposit Error!");
            } finally {
                //The finally block releases the lock
                lock.unlock();
            }
        }
        //Withdraw method
        public void withdraw(double money){
            //Obtains lock when first run
            lock.lock();
            //Withdraws money and then sleeps for 100ms
            try {
                //Checks if the amount to be withdrawn is less than the balance
                if (balance >= money){
                    double newBalance = balance - money;
                    Thread.sleep(100);
                    balance = newBalance;
                    System.out.println("Withdrew $" + money + ". Current balance is $" + balance);
                }
                //If amount to be withdrawn is greater than balance, display error
                else {
                    System.out.println("Insufficient balance!");
                }
            } catch (InterruptedException e){
                System.out.println("Withdrawal Error");
            } finally {
                //The finally block releases the lock
                lock.unlock();
            }
        }
        //Getter to return balance of the account
        public double returnBalance(){
            //Also utilizes lock to prevent race conditions
            lock.lock();
            try {
                return balance;
            } finally {
                //Releases lock after balance is returned
                lock.unlock();
            }
        }
    }
}
