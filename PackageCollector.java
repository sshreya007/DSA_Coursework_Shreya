import java.util.*;

public static void main(String[] args) {
    int[] packages1 = {1, 0, 0, 0, 0, 1};
    int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
    System.out.println(minRoadsToCollectPackages(packages1, roads1)); // Output: 2

    int[] packages2 = {0, 0, 0, 1, 1, 0, 0, 1};
    int[][] roads2 = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {5, 6}, {5, 7}};
    System.out.println(minRoadsToCollectPackages(packages2, roads2)); // Output: 2
}

public class PackageCollector {
    public static int minRoadsToCollectPackages(int[] packages, int[][] roads) {
        int n = packages.length;
        List<List<Integer>> graph = new ArrayList<>();

        // Step 1: Build the adjacency list
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }

        // Step 2: Find all nodes that have packages
        Set<Integer> packageLocations = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (packages[i] == 1) {
                packageLocations.add(i);
            }
        }

        // Step 3: BFS to find all packages within distance 2
        Set<Integer> collected = new HashSet<>();
        int minMoves = 0;

        for (int start = 0; start < n; start++) {
            if (collected.containsAll(packageLocations)) break; // Stop if all packages are collected

            Queue<int[]> queue = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();
            queue.offer(new int[]{start, 0}); // {currentNode, depth}
            visited.add(start);

            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int node = curr[0], depth = curr[1];

                // Collect packages within distance 2
                if (depth <= 2 && packageLocations.contains(node)) {
                    collected.add(node);
                }

                // If depth exceeds 2, stop searching further
                if (depth >= 2) continue;

                // Explore neighbors
                for (int neighbor : graph.get(node)) {
                    if (!visited.contains(neighbor)) {
                        queue.offer(new int[]{neighbor, depth + 1});
                        visited.add(neighbor);
                    }
                }
            }
            minMoves++;
        }
        
        return minMoves;
    }
}

