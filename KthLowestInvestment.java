import java.util.PriorityQueue;

public class KthLowestInvestment {
    public static void main(String[] args) {
        // Test cases
        int[] returns1 = {2, 5};
        int[] returns2 = {3, 4};
        System.out.println(kthSmallestProduct(returns1, returns2, 2)); // Output: 8

        int[] returns1b = {-4, -2, 0, 3};
        int[] returns2b = {2, 4};
        System.out.println(kthSmallestProduct(returns1b, returns2b, 6)); // Output: 0
    }
    static class Pair {
        int i, j, value;
        
        Pair(int i, int j, int value) {
            this.i = i;
            this.j = j;
            this.value = value;
        }
    }

    public static int kthSmallestProduct(int[] returns1, int[] returns2, int k) {
        PriorityQueue<Pair> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.value, b.value));

        // Initialize heap with first elements from returns2 multiplied by each element in returns1
        for (int i = 0; i < returns1.length; i++) {
            minHeap.offer(new Pair(i, 0, returns1[i] * returns2[0]));
        }

        // Extract k-1 elements to reach the kth element
        while (k-- > 1) {
            Pair current = minHeap.poll();
            int i = current.i, j = current.j;

            // If there's another element in returns2, push the next pair
            if (j + 1 < returns2.length) {
                minHeap.offer(new Pair(i, j + 1, returns1[i] * returns2[j + 1]));
            }
        }



        return minHeap.poll().value; // The kth smallest product
    }

}


