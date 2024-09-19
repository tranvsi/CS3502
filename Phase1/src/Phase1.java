public class Phase1 {
    public static void main(String[] args){
        Thread reader = new Thread(new ReaderThread(), "Reader");
        Thread writer = new Thread(new WriterThread(), "Writer");
        Thread calculator = new Thread(new CalculateThread(), "Calculator");

        reader.start();
        writer.start();
        calculator.start();

        try{
            reader.join();
            writer.join();
            calculator.join();
        } catch (InterruptedException e){
            System.err.println("Main has been interrupted");
        }

        System.out.println("Threads have finished their tasks. Program closing...");
    }

    static class ReaderThread implements Runnable{
        @Override
        public void run(){
            try{
                System.out.println("Reader started");
                Thread.sleep(500);
                System.out.println("Reader finished");
            } catch (InterruptedException e){
                System.out.println("Reader interrupted");
            }
        }
    }

    static class WriterThread implements Runnable{
        @Override
        public void run(){
            try{
                System.out.println("Writer started");
                Thread.sleep(1000);
                System.out.println("Writer finished");
            } catch (InterruptedException e){
                System.out.println("Writer interrupted");
            }
        }
    }

    static class CalculateThread implements Runnable{
        @Override
        public void run(){
            try{
                System.out.println("Calculator started");
                Thread.sleep(800);
                System.out.println("Calculator finished");
            } catch (InterruptedException e){
                System.out.println("Calculator interrupted");
            }
        }
    }
}
