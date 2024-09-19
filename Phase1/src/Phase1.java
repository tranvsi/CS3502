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
            e.printStackTrace();
        }

        System.out.println("Threads have finished their tasks. Program closing...");
    }

    static class ReaderThread implements Runnable{
        public void run(){
            System.out.println("This thread is reading!");
        }
    }

    static class WriterThread implements Runnable{
        public void run(){
            System.out.println("This thread is writing!");
        }
    }

    static class CalculateThread implements Runnable{
        public void run(){
            System.out.println("This thread is calculating!");
        }
    }
}
