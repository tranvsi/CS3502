import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Phase4 {
    public static void main(String[] args){

    }

    public static void transfer(){

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


    }
}
