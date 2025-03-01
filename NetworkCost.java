import java.util.*;
public class NetworkCost {
    
    public static void main(String[] args) {
        int n = 3;
        int[] modules = {1, 2, 2};
        int[][] connections = {{1, 2, 1}, {2, 3, 1}};

        int result = minCostToConnectDevices(n, modules, connections);
        System.out.println("Minimum total cost to connect all devices: " + result);
    }

    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        // Create a graph with virtual edges (module cost connections)
        List<int[]> graph = new ArrayList<>();
        
        // Add virtual edges from the "virtual node" 0 to each device
        for (int i = 0; i < n; i++) {
            graph.add(new int[]{i + 1, 0, modules[i]});
        }
        
        // Add real connections to the graph
        for (int[] connection : connections) {
            graph.add(new int[]{connection[0], connection[1], connection[2]});
        }

        // Use Prim's algorithm to calculate the MST cost
        return prim(n, graph);
    }

    private static int prim(int n, List<int[]> graph) {
        // Min-Heap for storing the edges (cost, device)
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        boolean[] visited = new boolean[n + 1]; // to track visited devices
        int totalCost = 0;
        
        // Start with the virtual node (0) which connects to all devices
        pq.offer(new int[]{0, 1, 0});
        
        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int device1 = edge[0];
            int device2 = edge[1];
            int cost = edge[2];
            // Skip already visited devices
            if (visited[device2]) continue;
            // Add the cost of this edge to the total
            totalCost += cost;
            visited[device2] = true;

            // Add all connected edges to the heap
            for (int[] connection : graph) {
                if (connection[0] == device2 && !visited[connection[1]]) {
                    pq.offer(new int[]{device2, connection[1], connection[2]});
                }
            }
        }
        return totalCost;
    }
}


