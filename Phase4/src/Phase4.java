import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Phase4 {
    public static void main(String[] args){

    }

    public static void transfer(BankAccount from, BankAccount to, double amount){
        BankAccount firstAcc = from.compareTo(to) < 0 ? from:to;
        BankAccount secondAcc = from.compareTo(to) < 0 ? to:from;

        try {
            if (firstAcc.getLock().tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " lock " + firstAcc);

                try {
                    if (secondAcc.getLock().tryLock(1, TimeUnit.SECONDS)) {
                        System.out.println(Thread.currentThread().getName() + " locked " + secondAcc);
                        if (from.getBalance() >= amount){
                            from.withdraw(amount);
                            to.deposit(amount);
                            System.out.println("Moved $" + amount " from " + from + " to " + to);
                        }
                        else {
                            System.out.println("Insufficient balance to transfer from " + from + " to " + to);
                        }
                    }
                    else {
                        System.out.println(Thread.currentThread().getName() + " failed to lock " + secondAcc);
                    }
                } finally {
                    if (secondAcc.getLock().isHeldByCurrentThread()) {
                        secondAcc.getLock().unlock();
                    }
                }
            }
            else {
                System.out.println(Thread.currentThread().getName() + " failed to lock " + firstAcc);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            if (firstAcc.getLock().isHeldByCurrentThread()) {
                firstAcc.getLock().unlock();
            }
        }

    }

    static class BankAccount implements Comparable<BankAccount>{
        private double balance;
        private final ReentrantLock lock = new ReentrantLock();

        public BankAccount(double startingBalance){
            this.balance = startingBalance;
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
        public int compareTo(BankAccount other){
            return System.identityHashCode(this) - System.identityHashCode(other);
        }

        @Override
        public String toString(){
            return "Account@" + hashCode();
        }
    }
}
