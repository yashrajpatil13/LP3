import java.util.Scanner;

public class FibonacciCalculator {
    // Non-recursive implementation
    public static long fibonacciNonRecursive(int n) {
        if (n <= 1) return n;
        
        long prev = 0, current = 1;
        for (int i = 2; i <= n; i++) {
            long next = prev + current;
            prev = current;
            current = next;
        }
        return current;
    }
    
    // Recursive implementation
    public static long fibonacciRecursive(int n) {
        if (n <= 1) return n;
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number to calculate its Fibonacci value: ");
        int n = scanner.nextInt();
        
        if (n < 0) {
            System.out.println("Please enter a non-negative number");
            return;
        }
        
        // Calculate using both methods
        System.out.println("\nCalculating Fibonacci for n = " + n);
        
        // Non-recursive calculation with time measurement
        long startTime = System.nanoTime();
        long nonRecursiveResult = fibonacciNonRecursive(n);
        long nonRecursiveTime = System.nanoTime() - startTime;
        
        System.out.println("\nNon-recursive result: " + nonRecursiveResult);
        System.out.println("Non-recursive execution time: " + nonRecursiveTime/1000000.0 + " ms");
        
        // Recursive calculation with time measurement
        startTime = System.nanoTime();
        long recursiveResult = fibonacciRecursive(n);
        long recursiveTime = System.nanoTime() - startTime;
        
        System.out.println("\nRecursive result: " + recursiveResult);
        System.out.println("Recursive execution time: " + recursiveTime/1000000.0 + " ms");
        
        scanner.close();
    }
}