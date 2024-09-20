public class Phase1 {
    public static void main(String[] args){
        //Created reader thread
        Thread reader = new Thread(new ReaderThread(), "Reader");
        //Created writer thread
        Thread writer = new Thread(new WriterThread(), "Writer");
        //Created calculator thread
        Thread calculator = new Thread(new CalculateThread(), "Calculator");

        //Start reader thread
        reader.start();
        //Start writer thread
        writer.start();
        //Start calculator thread
        calculator.start();

        //Try-Catch block to ensure threads finish their tasks before the final print statement
        try{
            reader.join();
            writer.join();
            calculator.join();
        } catch (InterruptedException e){
            System.err.println("Main has been interrupted");
        }

        //Final print statement notifying user of program termination
        System.out.println("Threads have finished their tasks. Program closing...");
        System.exit(0);
    }

    //Static class for reader thread which implements runnable
    static class ReaderThread implements Runnable{
        @Override
        public void run(){
            //Try-Catch block to handle interruptions
            try{
                //Print statement during execution
                System.out.println("Reader started");
                //Sleep time of 500ms to simulate task
                Thread.sleep(500);
                //Print statement on completion
                System.out.println("Reader finished");
            } catch (InterruptedException e){
                System.out.println("Reader interrupted");
            }
        }
    }

    //Static class for writer thread which implements runnable
    static class WriterThread implements Runnable{
        @Override
        public void run(){
            //Try-Catch block to handle interruptions
            try{
                //Print statement during execution
                System.out.println("Writer started");
                //Sleep time of 1000ms to simulate task
                Thread.sleep(1000);
                //Print statement on completion
                System.out.println("Writer finished");
            } catch (InterruptedException e){
                System.out.println("Writer interrupted");
            }
        }
    }

    //Static class for calculator thread which implements runnable
    static class CalculateThread implements Runnable{
        @Override
        public void run(){
            //Try-Catch block to handle interruptions
            try{
                //Print statement during execution
                System.out.println("Calculator started");
                //Sleep time of 800ms to simulate task
                Thread.sleep(800);
                //Print statement on completion
                System.out.println("Calculator finished");
            } catch (InterruptedException e){
                System.out.println("Calculator interrupted");
            }
        }
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
    }
}
