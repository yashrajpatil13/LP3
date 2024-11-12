import java.util.Arrays;
import java.util.Scanner;

public class FractionalKnapsack {
    // Class to store item details
    static class Item implements Comparable<Item> {
        int index;      // Original index of item
        double weight;  // Weight of item
        double profit;  // Profit of item
        double ratio;   // Profit/Weight ratio

        public Item(int index, double weight, double profit) {
            this.index = index;
            this.weight = weight;
            this.profit = profit;
            // Calculate ratio with proper precision
            this.ratio = weight != 0 ? profit / weight : 0;
        }

        // Compare items based on profit/weight ratio in descending order
        public int compareTo(Item other) {
            return Double.compare(other.ratio, this.ratio);
        }

        // Create a copy of the item
        public Item copy() {
            Item copy = new Item(this.index, this.weight, this.profit);
            copy.ratio = this.ratio;
            return copy;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of items
        System.out.print("Enter the number of items (n): ");
        int n = scanner.nextInt();

        // Input validation
        if (n <= 0) {
            System.out.println("Number of items must be positive!");
            return;
        }

        Item[] items = new Item[n];
        Item[] sortedItems = new Item[n];  // For sorted items
        double[] fractions = new double[n]; // To store fractions in original order

        // Input weights
        System.out.println("\nEnter the weights of items:");
        for (int i = 0; i < n; i++) {
            double weight = scanner.nextDouble();
            if (weight <= 0) {
                System.out.println("Weight must be positive!");
                return;
            }
            items[i] = new Item(i + 1, weight, 0);
        }

        // Input profits
        System.out.println("\nEnter the profits of items:");
        for (int i = 0; i < n; i++) {
            double profit = scanner.nextDouble();
            if (profit < 0) {
                System.out.println("Profit cannot be negative!");
                return;
            }
            items[i].profit = profit;
            items[i].ratio = items[i].profit / items[i].weight; // Recalculate ratio
            sortedItems[i] = items[i].copy(); // Create a copy for sorting
        }

        // Input knapsack capacity
        System.out.print("\nEnter the knapsack capacity (m): ");
        double capacity = scanner.nextDouble();
        if (capacity <= 0) {
            System.out.println("Knapsack capacity must be positive!");
            return;
        }

        // Sort items by profit/weight ratio
        Arrays.sort(sortedItems);

        double totalProfit = 0;
        double remainingCapacity = capacity;

        // Process items in sorted order (highest ratio first)
        for (Item item : sortedItems) {
            int originalIndex = item.index - 1;
            
            if (remainingCapacity >= item.weight) {
                // Take whole item
                fractions[originalIndex] = 1.0;
                totalProfit += item.profit;
                remainingCapacity -= item.weight;
            } else if (remainingCapacity > 0) {
                // Take fraction of item
                double fraction = Math.round(remainingCapacity / item.weight * 100.0) / 100.0 ;
                fractions[originalIndex] = fraction;
                totalProfit += item.profit * fraction;
                remainingCapacity = 0;
            } else {
                fractions[originalIndex] = 0.0;
            }
        }

        // Print results in tabular format
        System.out.println("\nResults:");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s%n", 
            "Object", "Profit", "Weight", "Ratio", "Fraction");
        System.out.println("--------------------------------------------------");
        
        // Print in original order
        for (int i = 0; i < n; i++) {
            System.out.printf("%-10d %-10.2f %-10.2f %-10.2f %-10.2f%n", 
                items[i].index,
                items[i].profit,
                items[i].weight,
                items[i].ratio,
                fractions[i]);
        }

        System.out.println("\nMaximized Profit: " + String.format("%.2f", Math.round(totalProfit * 100.0) / 100.0));
        
        scanner.close();
    }
}


7
15
10 5 15 7 6 18 3
2 3 5 7 1 4 1
55.35