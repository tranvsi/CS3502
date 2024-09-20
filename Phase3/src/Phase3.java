import java.util.concurrent.locks.ReentrantLock;

public class Phase3 {
    public static void main(String[] args){
        BankAccount account1 = new BankAccount(5000);
        BankAccount account2 = new BankAccount(5000);

        Thread thread1 = new Thread(() -> transfer(account1,account2,500), "Transfer1to2");
        Thread thread2 = new Thread(() -> transfer(account2,account1,500), "Transfer2to1");
    }

    static class BankAccount {
        private double balance;
        private final ReentrantLock lock = new ReentrantLock();

        public BankAccount(double initialBalance){
            this.balance = initialBalance;
        }

        public ReentrantLock getLock(){
            return lock;
        }

        public double getBalance(){
            return balance;
        }

        public void deposit(double amount){
            balance += amount;
        }

        public void withdraw(double amount){
            balance -= amount;
        }

        @Override
        public String toString(){
            return "Account@" + hashCode();
        }
    }

    public static void transfer(BankAccount from, BankAccount to, double amount){
        ReentrantLock fromLock = from.getLock();
        ReentrantLock toLock = to.getLock();

        System.out.println(Thread.currentThread().getName() + " attempting to lock " + to);
        fromLock.lock();
        System.out.println(Thread.currentThread().getName() + " locked " + from);

        try {
            Thread.sleep(500);

            System.out.println(Thread.currentThread().getName() + " attempting to lock " + from);
            toLock.lock();
            System.out.println(Thread.currentThread().getName() + " locked " + to);

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
