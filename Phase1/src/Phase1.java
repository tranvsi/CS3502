public class Phase1 {
    public static void main(String[] args){

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
