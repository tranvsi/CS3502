import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Phase3 {
    public static void main(String[] args){

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
}
