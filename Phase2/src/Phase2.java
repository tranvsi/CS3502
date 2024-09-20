import java.util.concurrent.locks.ReentrantLock;
public class Phase2 {
    public static void main(String[] args){
        BankAccount bank = new BankAccount();

        Thread deposit = new Thread(() -> {
            for (int i = 0; i < 20; i++){
                bank.deposit(10);
            }
        }, "DepositThread");

        Thread withdraw = new Thread(() -> {
            for (int i = 0; i < 15; i++){
                bank.withdraw(5);
            }
        }, "WithdrawThread");

        deposit.start();
        withdraw.start();

        try {
            deposit.join();
            withdraw.join();
        } catch (InterruptedException e){
            System.err.println("Main thread error");
        }

        System.out.println("Final balance: $" + bank.returnBalance());
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
            lock.lock();
            try {
                return balance;
            } finally {
                lock.unlock();
            }
        }
    }
}
