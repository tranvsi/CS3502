import java.util.concurrent.locks.ReentrantLock;
public class Phase2 {
    public static void main(String[] args){
        BankAccount bank = new BankAccount();
        Thread deposit = new Thread(() -> {
            bank.deposit(100.0);
            System.out.println("$100 has been deposited.");
        }, "DepositThread");
        Thread withdraw = new Thread(() -> {
            bank.withdraw(75.0);
            System.out.println("$75 has been withdrawn.");
        }, "WithdrawThread");
        deposit.start();
        withdraw.start();
        try {
            deposit.join();
            withdraw.join();
        } catch (InterruptedException e){
            System.err.println("Main thread error");
        }
        System.out.println("Account balance: $" + bank.returnBalance());
        System.exit(0);
    }

    static class BankAccount{
        private double balance;
        private final ReentrantLock lock = new ReentrantLock();

        public void deposit(double money){
            lock.lock();
            try {
                double newBalance = balance + money;
                Thread.sleep(100);
                balance = newBalance;
                System.out.println("Deposited $" + money + ". Current balance is $" + balance);
            } catch (InterruptedException e){
                System.out.println("Deposit Error!");
            } finally {
                lock.unlock();
            }
        }

        public void withdraw(double money){
            lock.lock();
            try {
                if (balance >= money){
                    double newBalance = balance - money;
                    Thread.sleep(100);
                    balance = newBalance;
                    System.out.println("Withdrew $" + money + ". Current balance is $" + balance);
                }
                else {
                    System.out.println("Insufficient balance!");
                }
            } catch (InterruptedException e){
                System.out.println("Withdrawal Error");
            } finally {
                lock.unlock();
            }
        }

        public double returnBalance(){
            return balance;
        }
    }
}
