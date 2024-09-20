public class Phase1 {
    public static void main(String[] args){
        //Create new bank account object
        BankAccount bank = new BankAccount();
        //Create the deposit thread which deposits $100 into the account
        Thread deposit = new Thread(() -> {
            bank.deposit(100.0);
            System.out.println("$100 has been deposited.");
        }, "DepositThread");
        //Create the withdrawal thread which withdraws $75 from the account
        Thread withdraw = new Thread(() -> {
            bank.withdraw(75.0);
            System.out.println("$75 has been withdrawn.");
        }, "WithdrawThread");
        //Start both threads
        deposit.start();
        withdraw.start();
        //Use try-catch block to ensure both deposit and withdraw are able to finish before the main thread continues
        try{
            deposit.join();
            withdraw.join();
        } catch (InterruptedException e){
            System.err.println("Main thread error");
        }
        //Display final account balance
        System.out.println("Account balance: $" + bank.returnBalance());
        //Close program
        System.exit(0);
    }
    //Bank account class
    static class BankAccount{
        //New double variable which holds the balance of the account
        private double balance;
        //Create method for depositing money (includes 0.5s sleep delay to simulate real transaction)
        public synchronized void deposit(double money){
            double newBalance = balance + money;

            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                System.out.println("Deposit Error!");
            }
            balance = newBalance;
        }
        //Create method for withdrawing money (includes 0.5s sleep delay to simulate real transaction)
        public synchronized void withdraw(double money){
            double newBalance = balance - money;

            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                System.out.println("Withdraw error");
            }
            balance = newBalance;
        }
        //Getter to return the balance of the account
        public synchronized double returnBalance(){
            return balance;
        }
    }
}
