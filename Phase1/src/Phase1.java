public class Phase1 {
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

        try{
            deposit.join();
            withdraw.join();
        } catch (InterruptedException e){
            System.err.println("Main thread error");
        }

        System.out.println("Account balance: $" + bank.returnBalance());
    }


    static class BankAccount{
        private double balance;

        public synchronized void deposit(double money){
            double newBalance = balance + money;

            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
                System.out.println("Deposit Error!");
            }
            balance = newBalance;
        }

        public synchronized void withdraw(double money){
            double newBalance = balance - money;

            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
                System.out.println("Withdraw error");
            }
            balance = newBalance;
        }

        public synchronized double returnBalance(){
            return balance;
        }
    }
}
