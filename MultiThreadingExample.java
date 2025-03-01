public class MultiThreadingExample {
    public static void main(String[] args) {
        int n = 5; // Example value
        NumberPrinter printer = new NumberPrinter();
        ThreadController controller = new ThreadController(n);

        Thread zeroThread = new Thread(() -> controller.printZero(printer));
        Thread evenThread = new Thread(() -> controller.printEven(printer));
        Thread oddThread = new Thread(() -> controller.printOdd(printer));

        zeroThread.start();
        evenThread.start();
        oddThread.start();
    }
}
class NumberPrinter {
    public void printZero() {
        System.out.print("0");
    }

    public void printEven(int num) {
        System.out.print(num);
    }

    public void printOdd(int num) {
        System.out.print(num);
    }
}

class ThreadController {
    private int n;
    private int currentNumber = 1; // Start from 1
    private final Object lock = new Object();
    private boolean zeroTurn = true;

    public ThreadController(int n) {
        this.n = n;
    }

    public void printZero(NumberPrinter printer) {
        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                while (!zeroTurn) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printer.printZero();
                zeroTurn = false;
                lock.notifyAll();
            }
        }
    }

    public void printEven(NumberPrinter printer) {
        for (int i = 2; i <= n; i += 2) {
            synchronized (lock) {
                while (zeroTurn || currentNumber % 2 != 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printer.printEven(currentNumber);
                currentNumber++;
                zeroTurn = true;
                lock.notifyAll();
            }
        }
    }

    public void printOdd(NumberPrinter printer) {
        for (int i = 1; i <= n; i += 2) {
            synchronized (lock) {
                while (zeroTurn || currentNumber % 2 == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printer.printOdd(currentNumber);
                currentNumber++;
                zeroTurn = true;
                lock.notifyAll();
            }
        }
    }
}


