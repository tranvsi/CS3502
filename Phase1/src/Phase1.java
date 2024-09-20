public class Phase1 {
    public static void main(String[] args){

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
